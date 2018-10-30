package lzw.app.com.myutils.gesture;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lzw.app.com.myutils.R;

/**
 * Created by LiuZhaowei on 2018/10/26 0026.
 */

public class GestureView extends LinearLayout {

    private float marginSize;
    private Drawable pointNormal;
    private Drawable pointSelected;
    private Drawable pointWrong;
    private int normalColor;
    private int wrongColor;
    private int pointSize;
    private FrameLayout gestureContainer;
    private GestureContentView gestureContentView;

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载布局视图
        LayoutInflater.from(context).inflate(R.layout.activity_gesture_container, this, true);
        //加载自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Gesture);
        //手势点的大小,不会影响到手势区域的大小,暂时不支持小数
        pointSize = a.getInteger(R.styleable.Gesture_gesture_dot_size, 4);
        //手势居屏幕正中间,这个参数影响到手势整体的区域的大小
        marginSize = a.getDimension(R.styleable.Gesture_gesture_margin, 20);
        //正常点的图片
        pointNormal = a.getDrawable(R.styleable.Gesture_gesture_point_normal);
        //按下点的图片
        pointSelected = a.getDrawable(R.styleable.Gesture_gesture_point_selected);
        //错误点的图片
        pointWrong = a.getDrawable(R.styleable.Gesture_gesture_point_wrong);
        //默认手势连接线的颜色
        normalColor = a.getColor(R.styleable.Gesture_gesture_normal_color, Color.BLUE);
        //错误手势连接线的颜色
        wrongColor = a.getColor(R.styleable.Gesture_gesture_wrong_color, Color.RED);
        gestureContentView = new GestureContentView(getContext(), pointSize,
                normalColor, wrongColor, marginSize, pointNormal, pointSelected, pointWrong);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        gestureContainer = findViewById(R.id.gesture_container);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(gestureContainer.getLayoutParams());
        lp.setMargins((int) marginSize, 0, (int) marginSize, 0);
        gestureContainer.setLayoutParams(lp);
        //设置手势解锁显示到哪个布局的里面
        gestureContentView.setParentView(gestureContainer);

    }

    public void startGesture(boolean isVerify, String password, GestureDrawLinear.GestureCallBack callBack) {
        gestureContentView.setAss(isVerify, password, callBack);
    }

    public void clear(long time) {
        gestureContentView.clearDrawLinearState(time);
    }

}
