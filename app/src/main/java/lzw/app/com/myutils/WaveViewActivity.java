package lzw.app.com.myutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lzw.app.com.myutils.keyboard.KeyboardUtil;
import lzw.app.com.myutils.utils.ToastUtil;
import lzw.app.com.myutils.views.MyWaveView;
import lzw.app.com.myutils.views.ProgressView;
import lzw.app.com.myutils.views.SwitcherView;

/**
 * Created by LiuZhaowei on 2018/9/29 0029.
 */

public class WaveViewActivity extends AppCompatActivity {

    private MyWaveView myWaveView;
    private ProgressView mProgressView;
    private TextView mTextView;
    private SwitcherView mNotice;
    private EditText mEditText;
    private RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        myWaveView = findViewById(R.id.home_wave_view);
        mProgressView = findViewById(R.id.home_progressView);
        mTextView = findViewById(R.id.adapter_percent_tv);
        mNotice = findViewById(R.id.notice_text);
        mEditText = findViewById(R.id.et_rondom);
        mRelativeLayout = findViewById(R.id.background_rel);
        final KeyboardUtil keyboardUtil = new KeyboardUtil(WaveViewActivity.this,true);
        mTextView.setText("20%");
        myWaveView.setSpeed(3);
        myWaveView.setAmplitude(20);//振幅
        myWaveView.setWaterProgress(80);
        mProgressView.setProgress(100);
        ArrayList<String> list = new ArrayList<>();
        list.add("范冰冰是我大老婆");
        list.add("杨幂是我二老婆");
        list.add("赵丽颖是我三老婆");
        list.add("周冬雨是我四老婆");
        mNotice.setResource(list);
        mNotice.startRolling();
        //点击确定的回调
        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            @Override
            public void onOkClick() {
                ToastUtil.showLong(mEditText.getText().toString());
            }
        });
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboardUtil.hideKeyboard();
            }
        });
        //注册点击事件.
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboardUtil.attachTo(mEditText);
            }
        });
        //打开页面,主动点击控件,触发控件的onClickListener方法,实现弹出键盘.
        mEditText.performClick();
    }
}
