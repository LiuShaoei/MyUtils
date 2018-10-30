package lzw.app.com.myutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import lzw.app.com.myutils.gesture.GestureDrawLinear;
import lzw.app.com.myutils.gesture.GestureView;
import lzw.app.com.myutils.utils.ToastUtil;

/**
 * Created by LiuZhaowei on 2018/10/25 0025.
 */

public class GestureActivity extends AppCompatActivity {
   private GestureView gesture;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        gesture = findViewById(R.id.gesture);
        init();
    }

    private void init() {
        gesture.startGesture(false,"123456", new GestureDrawLinear.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                ToastUtil.showLong(inputCode);
                gesture.clear(1000L);
            }

            @Override
            public void checkedSuccess() {

                gesture.clear(1000L);
            }

            @Override
            public void checkFail() {
                gesture.clear(1000L);
            }
        });
    }

}
