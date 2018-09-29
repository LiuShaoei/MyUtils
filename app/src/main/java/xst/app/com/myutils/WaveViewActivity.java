package xst.app.com.myutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import xst.app.com.myutils.views.MyWaveView;
import xst.app.com.myutils.views.ProgressView;
import xst.app.com.myutils.views.SwitcherView;

/**
 * Created by LiuZhaowei on 2018/9/29 0029.
 */

public class WaveViewActivity extends AppCompatActivity {

    private MyWaveView myWaveView;
    private ProgressView mProgressView;
    private TextView mTextView;
    private SwitcherView mNotice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        myWaveView = findViewById(R.id.home_wave_view);
        mProgressView = findViewById(R.id.home_progressView);
        mTextView = findViewById(R.id.adapter_percent_tv);
        mNotice = findViewById(R.id.notice_text);

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


    }
}
