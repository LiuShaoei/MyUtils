package lzw.app.com.myutils.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * desc：发送短信倒计时
 */
public class MessageTimerUtil extends CountDownTimer {

    private final static long millisInFuture = 60 * 1000;//总时间
    private final static long countDownInterval = 1000;//间隔时间

    private TextView mTextView;

    public MessageTimerUtil(TextView textView) {
        super(millisInFuture, countDownInterval);
        mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setTextColor(Color.parseColor("#666666"));
        mTextView.setClickable(false);
        mTextView.setText("重新发送" + (millisUntilFinished / 1000) + "秒");  //设置倒计时时间
    }

    @Override
    public void onFinish() {
        mTextView.setTextColor(Color.parseColor("#666666"));
        mTextView.setText("重新获取");
        mTextView.setClickable(true);
    }
}
