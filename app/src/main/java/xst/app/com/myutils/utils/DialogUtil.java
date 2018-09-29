package xst.app.com.myutils.utils;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import xst.app.com.myutils.R;

/**
 * Created by LiuZhaowei on 2018/9/27 0027.
 */

public class DialogUtil extends DialogFragment implements View.OnClickListener {

    private TextView mTitle;
    private TextView mMessage;
    private TextView mNegative;
    private TextView mPositive;
    private TextView mOneButton;
    private LinearLayout mTwoButton;
    private Dialog mDialog;
    private Builder mBuilder;
    private static DialogUtil mDialogUtil;

    public static DialogUtil getInstance(Builder builder) {
        mDialogUtil = new DialogUtil();
        mDialogUtil.mBuilder = builder;
        return mDialogUtil;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDialog = getDialog();
        if (mDialog == null) {
            return null;
        }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //设置dialog动画
        mDialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog_animation;
        View view = inflater.inflate(R.layout.utils_custom_dialog, container);
        mTitle = view.findViewById(R.id.dialog_title);
        mMessage = view.findViewById(R.id.dialog_message);

        mNegative = view.findViewById(R.id.dialog_negative);
        mNegative.setOnClickListener(this);
        mPositive = view.findViewById(R.id.dialog_positive);
        mPositive.setOnClickListener(this);
        mOneButton = view.findViewById(R.id.dialog_one_button_layout);
        mOneButton.setOnClickListener(this);
        mTwoButton = view.findViewById(R.id.dialog_two_button_layout);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mDialog != null){
            //更改弹窗大小
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            mDialog.getWindow().setLayout((int)(dm.widthPixels*0.8),ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(TextUtils.isEmpty(mBuilder.title)){
            mTitle.setVisibility(View.GONE);
        }else{
            mTitle.setText(mBuilder.title);
        }

        if(mBuilder.isCancel){
            mDialogUtil.setCancelable(true);
        }else{
            mDialogUtil.setCancelable(false);
        }

        if(TextUtils.isEmpty(mBuilder.message)){
            mMessage.setVisibility(View.INVISIBLE);
        }else{
            mMessage.setText(mBuilder.message);
        }

        if(TextUtils.isEmpty(mBuilder.oneButton)){
            mOneButton.setVisibility(View.GONE);
        }else{
            mOneButton.setText(mBuilder.oneButton);
            mTwoButton.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(mBuilder.negative)){
            mNegative.setText("取消");
        }else{
            mNegative.setText(mBuilder.negative);
        }

        if(TextUtils.isEmpty(mBuilder.positive)){
            mPositive.setText("确定");
        }else{
            mPositive.setText(mBuilder.positive);
        }




    }
    public void showDialog(FragmentManager fragmentManager){
        mDialogUtil.show(fragmentManager,DialogUtil.class.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_negative:
                mBuilder.twoButtonListener.clickNegative();
                dismissAllowingStateLoss();
                break;
            case R.id.dialog_positive:
                mBuilder.twoButtonListener.clickPositive();
                dismissAllowingStateLoss();
                break;
            case R.id.dialog_one_button_layout:
                //如果dialog下方只有一个确定按钮需要再调用地方销毁dialog实例
                mBuilder.oneButtonListener.clickOneButton();
              //  dismissAllowingStateLoss();
                break;
        }
    }

    public interface OnClickTwoButtonListener {
        void clickNegative();

        void clickPositive();
    }

    public interface OnClickOneButtonListener {
        void clickOneButton();
    }

    public static class Builder {
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNegative(String negative) {
            this.negative = negative;
            return this;
        }

        public Builder setPositive(String positive) {
            this.positive = positive;
            return this;
        }

        public Builder setCancel(boolean cancel) {
            isCancel = cancel;
            return this;
        }

        public Builder setTwoButtonListener(OnClickTwoButtonListener twoButtonListener) {
            this.twoButtonListener = twoButtonListener;
            return this;
        }

        public Builder setOneButtonListener(OnClickOneButtonListener oneButtonListener) {
            this.oneButtonListener = oneButtonListener;
            return this;
        }

        private String title;
        private String message;
        private String negative;
        private String positive;

        public Builder setOneButton(String oneButton) {
            this.oneButton = oneButton;
            return this;
        }

        private String oneButton;
        private boolean isCancel = true;
        private OnClickTwoButtonListener twoButtonListener;
        private OnClickOneButtonListener oneButtonListener;

    }
}
