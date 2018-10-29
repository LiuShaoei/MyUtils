package lzw.app.com.myutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import lzw.app.com.myutils.gesture.GestureContentView;

/**
 * Created by LiuZhaowei on 2018/10/25 0025.
 */

public class GestureEditActivity extends AppCompatActivity {
    private FrameLayout gestureContainer;
    private TextView textReset;
    private GestureContentView GestureContentView;
    private String mFirstPassword = null;
    private boolean mIsFirstInput = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        gestureContainer = findViewById(R.id.gesture_container);
      //  textReset = findViewById(R.id.text_reset);
        init();
    }

    private void init() {
       /* GestureContentView = new GestureContentView(this, false, "", new GestureDrawLinear.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                if (!isInputPassValidate(inputCode)) {
                    textReset.setText(Html.fromHtml("<font color='#F98F12'>最少链接4个点, 请重新输入</font>"));
                    GestureContentView.clearDrawLinearState(0L);
                    return;
                }
                if (mIsFirstInput) {
                    mFirstPassword = inputCode;
                    GestureContentView.clearDrawLinearState(0L);
                    textReset.setText(Html.fromHtml("<font color='#F98F12'>请再次绘制相同图案</font>"));
                } else {
                    if (inputCode.equals(mFirstPassword)) {
                        //保存手势密码
                        ToastUtil.showShort(inputCode);
                        GestureContentView.clearDrawLinearState(0L);
                    } else {
                        textReset.setText(Html.fromHtml("<font color='#ff0000'>与上一次绘制不一致，请重新绘制</font>"));
                        //左右晃动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureEditActivity.this, R.anim.shake);
                        textReset.startAnimation(shakeAnimation);
                        //保持绘制的线,1秒后清除
                        GestureContentView.clearDrawLinearState(1000L);
                    }
                }
                mIsFirstInput = false;
            }

            @Override
            public void checkedSuccess() {

            }

            @Override
            public void checkFail() {

            }
        });
        //设置手势解锁显示到哪个布局的里面
        GestureContentView.setParentView(gestureContainer);*/

    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }
}
