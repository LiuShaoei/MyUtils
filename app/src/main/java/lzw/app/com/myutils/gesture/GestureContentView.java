package lzw.app.com.myutils.gesture;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LiuZhaowei on 2018/10/25 0025.
 */

public class GestureContentView extends ViewGroup {
    private int baseNum;//该值影响圆点的大小,1-6变大
    private int[] screenDisplay;
    private int blockWidth;//每个点的区域宽度
    private List<GesturePoint> list;// 声明一个集合用来封装坐标集合
    private Context context;
    int interval = 0;//圆点之间的间隔
    private GestureDrawLinear gestureDrawLinear;
    private Drawable pointSelected;//选中了的点的样子
    private Drawable pointNormal;//正常的点的样子
    private Drawable pointWrong;//错误的样子
    private float marginSize;//边距

    /**
     * 包含9个ImageView的容器，初始化
     */


    public GestureContentView(Context context, int baseNum, int normalColor, int wrongColor, float marginSize,
                              Drawable pointNormal, Drawable pointSelected, Drawable pointWrong) {
        super(context);
        this.marginSize = marginSize;
        screenDisplay = getScreenDisplay(context);
        blockWidth = screenDisplay[0] / 3;
        this.list = new ArrayList<>();
        this.context = context;
        this.baseNum = baseNum;
        this.pointNormal = pointNormal;
        this.pointSelected = pointSelected;
        this.pointWrong = pointWrong;

        //添加9个图标
        addChild();
        //初始化一个可以画线的view
        gestureDrawLinear = new GestureDrawLinear(context, list, screenDisplay, normalColor, wrongColor);
    }

    private void addChild() {
        interval = blockWidth / baseNum;
        for (int i = 0; i < 9; i++) {
            ImageView image = new ImageView(context);
            image.setImageDrawable(pointNormal);
            this.addView(image);
            invalidate();
            //第几行
            int row = i / 3;
            //第几列
            int col = i % 3;
            //定义点的每个属性
            int leftX = 0;
            int rightX = 0;
            int topY = 0;
            int bottomY = 0;
            if (i == 0) {
                leftX = col * blockWidth + interval + interval / 2;
                rightX = col * blockWidth + blockWidth - interval / 2;
                topY = row * blockWidth + interval;
                bottomY = row * blockWidth + blockWidth - interval;
            }
            if (i == 1) {
                leftX = col * blockWidth + interval;
                rightX = col * blockWidth + blockWidth - interval;
                topY = row * blockWidth + interval;
                bottomY = row * blockWidth + blockWidth - interval;
            }
            if (i == 2) {
                leftX = col * blockWidth + interval - interval / 2;
                rightX = col * blockWidth + blockWidth - interval - interval / 2;
                topY = row * blockWidth + interval;
                bottomY = row * blockWidth + blockWidth - interval;
            }

            if (i == 3) {
                leftX = col * blockWidth + interval + interval / 2;
                rightX = col * blockWidth + blockWidth - interval / 2;
                topY = row * blockWidth + interval - interval / 2;
                bottomY = row * blockWidth + blockWidth - interval - interval / 2;
            }
            if (i == 4) {
                leftX = col * blockWidth + interval;
                rightX = col * blockWidth + blockWidth - interval;
                topY = row * blockWidth + interval - interval / 2;
                bottomY = row * blockWidth + blockWidth - interval - interval / 2;
            }
            if (i == 5) {
                leftX = col * blockWidth + interval - interval / 2;
                rightX = col * blockWidth + blockWidth - interval - interval / 2;
                topY = row * blockWidth + interval - interval / 2;
                bottomY = row * blockWidth + blockWidth - interval - interval / 2;
            }
            if (i == 6) {
                leftX = col * blockWidth + interval + interval / 2;
                rightX = col * blockWidth + blockWidth - interval / 2;
                topY = row * blockWidth;
                bottomY = row * blockWidth + blockWidth - 2 * interval;
            }
            if (i == 7) {
                leftX = col * blockWidth + interval;
                rightX = col * blockWidth + blockWidth - interval;
                topY = row * blockWidth;
                bottomY = row * blockWidth + blockWidth - 2 * interval;
            }
            if (i == 8) {
                leftX = col * blockWidth + interval - interval / 2;
                rightX = col * blockWidth + blockWidth - interval - interval / 2;
                topY = row * blockWidth;
                bottomY = row * blockWidth + blockWidth - 2 * interval;
            }
            GesturePoint p = new GesturePoint(leftX, rightX, topY, bottomY, i + 1, image, pointNormal, pointSelected, pointWrong);
            this.list.add(p);
        }
    }

    public void setParentView(ViewGroup parent) {
        // 得到屏幕的宽度
        int width = screenDisplay[0];
        LayoutParams layoutParams = new LayoutParams(width, width - interval);
        this.setLayoutParams(layoutParams);
        gestureDrawLinear.setLayoutParams(layoutParams);
        parent.addView(gestureDrawLinear);
        parent.addView(this);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        interval = blockWidth / baseNum;
        for (int j = 0; j < getChildCount(); j++) {
            //第几行
            int row = j / 3;
            //第几列
            int col = j % 3;
            View v = getChildAt(j);

            int leftX = 0;
            int rightX = 0;
            int topY = 0;
            int bottomY = 0;
            if (j == 0) {
                leftX = col * blockWidth + interval + interval / 2;
                rightX = col * blockWidth + blockWidth - interval / 2;
                topY = row * blockWidth + interval;
                bottomY = row * blockWidth + blockWidth - interval;
            }
            if (j == 1) {
                leftX = col * blockWidth + interval;
                rightX = col * blockWidth + blockWidth - interval;
                topY = row * blockWidth + interval;
                bottomY = row * blockWidth + blockWidth - interval;
            }
            if (j == 2) {
                leftX = col * blockWidth + interval - interval / 2;
                rightX = col * blockWidth + blockWidth - interval - interval / 2;
                topY = row * blockWidth + interval;
                bottomY = row * blockWidth + blockWidth - interval;
            }

            if (j == 3) {
                leftX = col * blockWidth + interval + interval / 2;
                rightX = col * blockWidth + blockWidth - interval / 2;
                topY = row * blockWidth + interval - interval / 2;
                bottomY = row * blockWidth + blockWidth - interval - interval / 2;
            }
            if (j == 4) {
                leftX = col * blockWidth + interval;
                rightX = col * blockWidth + blockWidth - interval;
                topY = row * blockWidth + interval - interval / 2;
                bottomY = row * blockWidth + blockWidth - interval - interval / 2;
            }
            if (j == 5) {
                leftX = col * blockWidth + interval - interval / 2;
                rightX = col * blockWidth + blockWidth - interval - interval / 2;
                topY = row * blockWidth + interval - interval / 2;
                bottomY = row * blockWidth + blockWidth - interval - interval / 2;
            }
            if (j == 6) {
                leftX = col * blockWidth + interval + interval / 2;
                rightX = col * blockWidth + blockWidth - interval / 2;
                topY = row * blockWidth;
                bottomY = row * blockWidth + blockWidth - 2 * interval;
            }
            if (j == 7) {
                leftX = col * blockWidth + interval;
                rightX = col * blockWidth + blockWidth - interval;
                topY = row * blockWidth;
                bottomY = row * blockWidth + blockWidth - 2 * interval;
            }
            if (j == 8) {
                leftX = col * blockWidth + interval - interval / 2;
                rightX = col * blockWidth + blockWidth - interval - interval / 2;
                topY = row * blockWidth;
                bottomY = row * blockWidth + blockWidth - 2 * interval;
            }
            v.layout(leftX, topY, rightX, bottomY);

        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //遍历设置每个子view的大小
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setAss(boolean isVerify, String password, GestureDrawLinear.GestureCallBack callBack) {
        gestureDrawLinear.setAss(isVerify, password, callBack);
    }

    /**
     * 保留路径delayTime时间长
     */
    public void clearDrawLinearState(long delayTime) {
        gestureDrawLinear.clearDrawLinearState(delayTime);
    }

    //根据手机大小得到手机的屏幕宽度
    public int[] getScreenDisplay(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels - 2 * (int) marginSize;
        int height = displayMetrics.heightPixels;
        int[] result = {width, height};
        return result;
    }
}
