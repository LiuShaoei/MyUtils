package xst.app.com.myutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xst.app.com.myutils.utils.DialogUtil;
import xst.app.com.myutils.utils.LoadingUtil;
import xst.app.com.myutils.utils.LogUtil;
import xst.app.com.myutils.utils.NumUtils;
import xst.app.com.myutils.utils.PwdStatusJudgeUtil;
import xst.app.com.myutils.utils.StatusBarUtil;
import xst.app.com.myutils.utils.ToastUtil;
import xst.app.com.myutils.views.ClearEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mOne;
    private TextView mTwo;
    private DialogUtil mDialogUtil;
    private LinearLayout mAppTop;
    private ClearEditText mEditText;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOne = findViewById(R.id.one);
        mOne.setOnClickListener(this);
        mTwo = findViewById(R.id.two);
        mTwo.setOnClickListener(this);
        mAppTop = findViewById(R.id.app_top);
        StatusBarUtil.init(this, mAppTop);

        mEditText = findViewById(R.id.edit_text);
        mImageView = findViewById(R.id.manner_img);
        mImageView.setOnClickListener(this);
        testNum();
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
