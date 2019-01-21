package lzw.app.com.myutils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lzw.app.com.myutils.header.Header;
import lzw.app.com.myutils.utils.CodeUtil;
import lzw.app.com.myutils.utils.DialogUtil;
import lzw.app.com.myutils.utils.LoadingUtil;
import lzw.app.com.myutils.utils.LogUtil;
import lzw.app.com.myutils.utils.NumUtils;
import lzw.app.com.myutils.utils.PwdStatusJudgeUtil;
import lzw.app.com.myutils.utils.StatusBarUtil;
import lzw.app.com.myutils.utils.ToastUtil;
import lzw.app.com.myutils.views.ClearEditText;
import xst.app.com.essayjoke.UserAidl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mOne;
    private TextView mTwo;
    private DialogUtil mDialogUtil;
    private LinearLayout mAppTop;
    private ClearEditText mEditText;
    private ImageView mImageView;
    private Header mheader;
    private ImageView mCode;
    private TextView mGetCode;

    private UserAidl mUserAidl;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接好了
            mUserAidl = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接
         //  mUserAidl = UserAidl.Stub
        }
    };

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
        findViewById(R.id.popup).setOnClickListener(this);
        StatusBarUtil.init(this, mAppTop);
        findViewById(R.id.three).setOnClickListener(this);
        mEditText = findViewById(R.id.edit_text);
        mImageView = findViewById(R.id.manner_img);
        mImageView.setOnClickListener(this);
        mheader.setOnHeaderClickListener(this);
        mCode = findViewById(R.id.code);
        mCode.setOnClickListener(this);
        mGetCode  = findViewById(R.id.m_code);
        testNum();

        Bitmap code = CodeUtil.getInstance().createBitmap();
        mCode.setImageBitmap(code);
        mGetCode.setText(CodeUtil.getInstance().getCode());

    }


    /**
     * 测试数字转换工具类
     */
    private void testNum() {




        Intent mIntent = new Intent();
        mIntent.setAction("com.study.aidl.user");//你定义的service的action
        mIntent.setPackage("xst.app.com.essayjoke");//这里你需要设置你应用的包名
        bindService(mIntent,mServiceConn,Context.BIND_AUTO_CREATE);


        double a = 35.235435;
        double b = 3.3;
        double result = NumUtils.formatRound(a, b, 2);
        LogUtil.i(result + "");

        LogUtil.i("formatComma:" + (NumUtils.formatComma("345345345.3453425")));
        LogUtil.i("format1Point:" + (NumUtils.format1Point("345345345.3453425")));
        LogUtil.i("format2Point:" + (NumUtils.format2Point("345345345.3453425")));
        LogUtil.i("formatDou00:" + (NumUtils.formatDou00("345345345.3453425")));
        LogUtil.i("formatDou000:" + (NumUtils.formatDou000("345345345.3453425")));
        LogUtil.i("formatDou0000:" + (NumUtils.formatDou0000("345345345")));
        LogUtil.i("formatDou00002:" + (NumUtils.formatDou00002("345300")));
        LogUtil.i("formatNoPoint:" + (NumUtils.formatNoPoint("345345300.6453425")));
        LogUtil.i(NumUtils.getAppName());
        LogUtil.i(NumUtils.getAppCode() + "");
        String time = NumUtils.transferStringToDate("yyyy-MM-dd", "5115112251555");
        LogUtil.i(time);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.three:
                startActivity(new Intent(this, GestureActivity.class));
                break;
            case R.id.title_left_img:
                ToastUtil.showShort("返回");
                break;
            case R.id.title_right_text:
               // ToastUtil.showShort("下一页");

                try {
                    ToastUtil.showShort(mUserAidl.getUserName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

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
                                        Intent intent = new Intent(MainActivity.this, WaveViewActivity.class);
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
                PwdStatusJudgeUtil.setPwdShowOrHidden(mEditText, mImageView);
                break;
            case R.id.popup:
                PopupWindowActivity.toPopupWindowActivity(this);
                break;
            case R.id.code:
                Bitmap code = CodeUtil.getInstance().createBitmap();
                mCode.setImageBitmap(code);
                mGetCode.setText(CodeUtil.getInstance().getCode());
                break;
        }
    }

}
