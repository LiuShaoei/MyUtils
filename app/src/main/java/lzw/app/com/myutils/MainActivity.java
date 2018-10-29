package lzw.app.com.myutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lzw.app.com.myutils.gesture.GestureDrawLinear;
import lzw.app.com.myutils.gesture.GestureView;
import lzw.app.com.myutils.test.Header;
import lzw.app.com.myutils.utils.DialogUtil;
import lzw.app.com.myutils.utils.LoadingUtil;
import lzw.app.com.myutils.utils.LogUtil;
import lzw.app.com.myutils.utils.NumUtils;
import lzw.app.com.myutils.utils.PwdStatusJudgeUtil;
import lzw.app.com.myutils.utils.StatusBarUtil;
import lzw.app.com.myutils.utils.ToastUtil;
import lzw.app.com.myutils.views.ClearEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mOne;
    private TextView mTwo;
    private DialogUtil mDialogUtil;
    private LinearLayout mAppTop;
    private ClearEditText mEditText;
    private ImageView mImageView;
    private Header mheader;
    private TextView textView;
    private GestureView gestureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOne = findViewById(R.id.one);
        mOne.setOnClickListener(this);
        mTwo = findViewById(R.id.two);
        mTwo.setOnClickListener(this);
        mAppTop = findViewById(R.id.app_top);
        mheader = findViewById(R.id.header);
        textView = findViewById(R.id.title_text);
        gestureView = findViewById(R.id.gesture);

        StatusBarUtil.init(this, mAppTop);
        findViewById(R.id.three).setOnClickListener(this);
        mEditText = findViewById(R.id.edit_text);
        mImageView = findViewById(R.id.manner_img);
        mImageView.setOnClickListener(this);
        mheader.setOnheaderClickListener(this);
        testNum();
        textView.setText("我是title");

        gestureView.startGesture(true,"12345", new GestureDrawLinear.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                ToastUtil.showShort(inputCode);
                gestureView.clear(1000L);
            }

            @Override
            public void checkedSuccess() {
                ToastUtil.showShort("成功");
                gestureView.clear(0L);
            }

            @Override
            public void checkFail() {
                ToastUtil.showShort("错误");
                gestureView.clear(1000L);
            }
        });
    }



    /**
     * 测试数字转换工具类
     */
    private void testNum() {
        double a = 35.235435;
        double b = 3.3;
        double  result = NumUtils.formatRound(a,b,2);
        LogUtil.i(result+"");

        LogUtil.i("formatComma:"+(NumUtils.formatComma("345345345.3453425")));
        LogUtil.i("format1Point:"+(NumUtils.format1Point("345345345.3453425")));
        LogUtil.i("format2Point:"+(NumUtils.format2Point("345345345.3453425")));
        LogUtil.i("formatDou00:"+(NumUtils.formatDou00("345345345.3453425")));
        LogUtil.i("formatDou000:"+(NumUtils.formatDou000("345345345.3453425")));
        LogUtil.i("formatDou0000:"+(NumUtils.formatDou0000("345345345")));
        LogUtil.i("formatDou00002:"+(NumUtils.formatDou00002("345300")));
        LogUtil.i("formatNoPoint:"+(NumUtils.formatNoPoint("345345300.6453425")));
        LogUtil.i(NumUtils.getAppName());
        LogUtil.i(NumUtils.getAppCode()+"");
        String time = NumUtils.transferStringToDate("yyyy-MM-dd","5115112251555");
        LogUtil.i(time);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.three:
                startActivity(new Intent(this,GestureEditActivity.class));
                break;
            case R.id.title_left_img:
                ToastUtil.showShort("返回");
                break;
            case R.id.one:
                mDialogUtil = DialogUtil.getInstance(
                        new DialogUtil.Builder()
                                .setCancel(false)
                                .setTitle("提示")
                                .setMessage("这是一个提示")
                                .setOneButton("确定")
                                .setOneButtonListener(new DialogUtil.OnClickOneButtonListener() {
                                    @Override
                                    public void clickOneButton() {
                                        ToastUtil.showShort("OneButton");
                                        //在这里点击销毁实例,或其他地方
                                        Intent intent = new Intent(MainActivity.this,WaveViewActivity.class);
                                        startActivity(intent);
                                        mDialogUtil.dismissAllowingStateLoss();
                                    }
                                }));
                mDialogUtil.showDialog(getSupportFragmentManager());
                break;
            case R.id.two:
                DialogUtil.getInstance(new DialogUtil.Builder()
                        .setCancel(true)
                        .setTitle("这是提示")
                        .setMessage("这是两个button的提示")
                        .setTwoButtonListener(new DialogUtil.OnClickTwoButtonListener() {
                            @Override
                            public void clickNegative() {
                                ToastUtil.showShort("clickNegative");
                            }

                            @Override
                            public void clickPositive() {
                                //ToastUtil.showShort("clickPositive");
                                LoadingUtil loadingUtil = new LoadingUtil(MainActivity.this);
                                loadingUtil.show();

                            }
                        })).showDialog(getSupportFragmentManager());
                break;
            case R.id.manner_img:
                PwdStatusJudgeUtil.setPwdShowOrHidden(mEditText,mImageView);
                break;
        }
    }

}
