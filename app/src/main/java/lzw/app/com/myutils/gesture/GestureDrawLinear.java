package lzw.app.com.myutils.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by LiuZhaowei on 2018/10/25 0025.
 */

public class GestureDrawLinear extends View {

    private int mov_x;//声明起点x坐标
    private int mov_y;//声明起点y坐标
    private Paint paint;//声明一个画笔
    private Canvas canvas;//声明一个画布
    private Bitmap bitmap;//声明一个位图
    private GesturePoint currentPoint;//手指当前在哪个point内
    private GestureCallBack callBack;//用户绘图的回调
    private StringBuilder currentPassWord;//用户当前绘制的图形密码
    private boolean isVerify = false;//是否为效验
    private String passWord = "";//用户传入的password
    private List<GesturePoint> list;//声明一个封装view坐标的集合
    private List<Pair<GesturePoint, GesturePoint>> linearList;//记录画过的线
    private Map<String, GesturePoint> autoCheckPointMap;//自动选中的情况点
    private boolean isDrawEnable = true;//是否允许绘制
    private int normalColor;//默认手势连接线的颜色
    private int wrongColor;//手势线错误的颜色

    public GestureDrawLinear(Context context, List<GesturePoint> list, int[] screenDisplay,
                             int normalColor, int wrongColor) {
        super(context);
        paint = new Paint(Paint.DITHER_FLAG);//创建一个画笔
        bitmap = Bitmap.createBitmap(screenDisplay[0], screenDisplay[0], Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        paint.setStyle(Paint.Style.STROKE);//设置非填充
        paint.setStrokeWidth(10);//笔宽10像素
        paint.setAntiAlias(true);//不显示锯齿

        this.list = list;
        this.linearList = new ArrayList<>();
        initAutoCheckPointMap();
        this.normalColor = normalColor;
        this.wrongColor = wrongColor;
        //初始化密码缓存
        this.currentPassWord = new StringBuilder();
    }

    private void initAutoCheckPointMap() {
        autoCheckPointMap = new HashMap<>();
        autoCheckPointMap.put("1,3", getGesturePointByNum(2));
        autoCheckPointMap.put("1,7", getGesturePointByNum(4));
        autoCheckPointMap.put("1,9", getGesturePointByNum(5));
        autoCheckPointMap.put("2,8", getGesturePointByNum(5));
        autoCheckPointMap.put("3,7", getGesturePointByNum(5));
        autoCheckPointMap.put("3,9", getGesturePointByNum(6));
        autoCheckPointMap.put("4,6", getGesturePointByNum(5));
        autoCheckPointMap.put("7,9", getGesturePointByNum(8));
    }

    private GesturePoint getGesturePointByNum(int num) {
        for (GesturePoint point : list) {
            if (point.getNum() == num) {
                return point;
            }
        }
        return null;
    }

    /**
     * 画位图
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDrawEnable == false) {
            //当期不允许绘制
            return true;
        }
        paint.setColor(normalColor);//设置默认链接线的颜色
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mov_x = (int) event.getX();
                mov_y = (int) event.getY();
                //判断当前点击的位置是处于哪个点之内
                currentPoint = getPointAt(mov_x, mov_y);
                if (currentPoint != null) {
                    currentPoint.setPointState(GesturePoint.POINT_STATE_SELECTED);
                    currentPassWord.append(currentPoint.getNum() - 1);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                clearScreenAndDrawList();
                // 得到当前移动位置是处于哪个点内
                GesturePoint pointAt = getPointAt((int) event.getX(), (int) event.getY());
                // 代表当前用户手指处于点与点之前
                if (currentPoint == null && pointAt == null) {
                    return true;
                } else {// 代表用户的手指移动到了点上
                    if (currentPoint == null) {// 先判断当前的point是不是为null
                        // 如果为空，那么把手指移动到的点赋值给currentPoint
                        currentPoint = pointAt;
                        // 把currentPoint这个点设置选中为true;
                        currentPoint.setPointState(GesturePoint.POINT_STATE_SELECTED);
                        currentPassWord.append(currentPoint.getNum() - 1);
                    }
                }
                if (pointAt == null || currentPoint.equals(pointAt) || GesturePoint.POINT_STATE_SELECTED == pointAt.getPointState()) {
                    // 点击移动区域不在圆的区域，或者当前点击的点与当前移动到的点的位置相同，或者当前点击的点处于选中状态
                    // 那么以当前的点中心为起点，以手指移动位置为终点画线
                    canvas.drawLine(currentPoint.getCenterX(), currentPoint.getCenterY(), event.getX(), event.getY(), paint);// 画线
                } else {
                    // 如果当前点击的点与当前移动到的点的位置不同
                    // 那么以前前点的中心为起点，以手移动到的点的位置画线
                    canvas.drawLine(currentPoint.getCenterX(), currentPoint.getCenterY(), pointAt.getCenterX(), pointAt.getCenterY(), paint);// 画线
                    pointAt.setPointState(GesturePoint.POINT_STATE_SELECTED);

                    // 判断是否中间点需要选中
                    GesturePoint betweenPoint = getBetweenCheckPoint(currentPoint, pointAt);
                    if (betweenPoint != null && GesturePoint.POINT_STATE_SELECTED != betweenPoint.getPointState()) {
                        // 存在中间点并且没有被选中
                        Pair<GesturePoint, GesturePoint> pair1 = new Pair<>(currentPoint, betweenPoint);
                        linearList.add(pair1);
                        currentPassWord.append(betweenPoint.getNum() - 1);
                        Pair<GesturePoint, GesturePoint> pair2 = new Pair<>(betweenPoint, pointAt);
                        linearList.add(pair2);
                        currentPassWord.append(pointAt.getNum() - 1);
                        // 设置中间点选中
                        betweenPoint.setPointState(GesturePoint.POINT_STATE_SELECTED);
                        // 赋值当前的point;
                        currentPoint = pointAt;
                    } else {
                        Pair<GesturePoint, GesturePoint> pair = new Pair<>(currentPoint, pointAt);
                        linearList.add(pair);
                        currentPassWord.append(pointAt.getNum() - 1);
                        // 赋值当前的point;
                        currentPoint = pointAt;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:// 当手指抬起的时候
                if (callBack == null) {
                    break;
                }
                // 手势密码校验
                // 清掉屏幕上所有的线，只画上集合里面保存的线
                if (isVerify) {
                    if (passWord.equals(currentPassWord.toString())) {
                        callBack.checkedSuccess(); // 代表用户绘制的密码手势与传入的密码相同
                    } else {
                        callBack.checkFail();// 用户绘制的密码与传入的密码不同。
                    }
                } else {
                    callBack.onGestureCodeInput(currentPassWord.toString());
                }

                break;
            default:
                break;
        }
        return true;
    }

    public void clearDrawLinearState(long delayTime) {
        if (delayTime > 0) {
            //绘制红色提示路线
            isDrawEnable = false;
            drawErrorPathTip();
        }
        new Handler().postDelayed(new clearStateRunnable(), delayTime);
    }

    /**
     * 校验错误,再次绘制不一致提示
     */
    private void drawErrorPathTip() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setColor(wrongColor);// 设置默认线路颜色
        for (Pair<GesturePoint, GesturePoint> pair : linearList) {
            pair.first.setPointState(GesturePoint.POINT_STATE_WRONG);
            pair.second.setPointState(GesturePoint.POINT_STATE_WRONG);
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);// 画线
        }
        invalidate();
    }

    /**
     * 清除绘制状态的线程
     */
    final class clearStateRunnable implements Runnable {

        @Override
        public void run() {
            //重置current
            currentPassWord = new StringBuilder();
            //清空保存点的集合
            linearList.clear();
            //重新绘制界面
            clearScreenAndDrawList();
            for (GesturePoint point : list) {
                point.setPointState(GesturePoint.POINT_STATE_NORMAL);
            }
            invalidate();
            isDrawEnable = true;
        }
    }


    /**
     * 判断是否中间点需要选中
     */
    private GesturePoint getBetweenCheckPoint(GesturePoint pointStart, GesturePoint pointEnd) {
        int startNum = pointStart.getNum();
        int endNum = pointEnd.getNum();
        String key = null;
        if (startNum < endNum) {
            key = startNum + "," + endNum;
        } else {
            key = endNum + "," + startNum;
        }
        return autoCheckPointMap.get(key);
    }

    /**
     * 清除屏幕上的所有的线,然后画出集合里面的线
     */
    private void clearScreenAndDrawList() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<GesturePoint, GesturePoint> pair : linearList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);
        }
    }

    /**
     * 通过点的位置去集合里面查找这个点是包含在哪个Point里面的.
     * 如果没有找到,则返回null,代表用户当前移动的地方属于点与点之间
     */
    private GesturePoint getPointAt(int x, int y) {
        for (GesturePoint point : list) {
            //先判断x
            int leftX = point.getLeftX();
            int rightX = point.getRightX();
            if (!(x >= leftX && x < rightX)) {
                //如果为假,则跳到下一个对比
                continue;
            }
            int topY = point.getTopY();
            int bottomY = point.getBottomY();
            if (!(y >= topY && y < bottomY)) {
                //若果为假,则跳到下一个对比
                continue;
            }
            //如果执行到这里,那说明当前点击的位置在遍历到的点的位置这个地方
            return point;
        }
        return null;
    }

    public void setAss(boolean isVerify, String password, GestureDrawLinear.GestureCallBack callBack) {
        this.isVerify = isVerify;
        this.passWord = password;
        this.callBack = callBack;
    }

    /**
     * 效验,设置回调
     */
    public interface GestureCallBack {
        void onGestureCodeInput(String inputCode);

        void checkedSuccess();

        void checkFail();
    }
}

