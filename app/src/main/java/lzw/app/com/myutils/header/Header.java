package lzw.app.com.myutils.header;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lzw.app.com.myutils.R;

/**
 * Created by LiuZhaowei on 2018/10/9 0009.
 */

public class Header extends LinearLayout {

    private RelativeLayout mBackgroundColor;
    private ImageView mLeftImageView;
    private TextView mCenterTextView;
    private TextView mRightTextView;
    private ImageView mRightImageView;
    private int backGroundColor;
    private String textCenterView;
    private Drawable imageView;
    private int textCenterColor;
    private float textCenterSize;
    private String textRightView;
    private Drawable imageRightView;
    private int textRightColor;
    private float textRightSize;
    private Activity activity;

    public Header(Context context) {
        super(context);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity) context;
        //加载视图布局
        LayoutInflater.from(context).inflate(R.layout.header, this, true);
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Header);
        textCenterView = a.getString(R.styleable.Header_title_center_text);
        textRightView = a.getString(R.styleable.Header_title_right_text);
        imageView = a.getDrawable(R.styleable.Header_title_left_img);
        imageRightView = a.getDrawable(R.styleable.Header_title_right_img);

        backGroundColor = a.getColor(R.styleable.Header_title_background_color, Color.WHITE);
        textCenterColor = a.getColor(R.styleable.Header_title_center_text_color, Color.WHITE);
        textRightColor = a.getColor(R.styleable.Header_title_right_text_color, Color.WHITE);
        textCenterSize = a.getDimension(R.styleable.Header_title_center_text_size, 14);
        textRightSize = a.getDimension(R.styleable.Header_title_right_text_size, 11);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundColor = (RelativeLayout) findViewById(R.id.title_background_color);
        mLeftImageView = (ImageView) findViewById(R.id.title_left_img);
        mCenterTextView = (TextView) findViewById(R.id.title_center_text);
        mRightImageView = (ImageView) findViewById(R.id.title_right_img);
        mRightTextView = (TextView) findViewById(R.id.title_right_text);
        //首先加载带有默认的控件部分.
        setBackgroundColor(backGroundColor);
        setTextColor(textCenterColor, textRightColor);
        setTextSize(textCenterSize, textRightSize);

        if (textCenterView != null) setCenterText(textCenterView);

        if (imageView == null) {
            mLeftImageView.setVisibility(GONE);
        } else {
            setLeftImage(imageView);
            mLeftImageView.setOnClickListener((view) -> activity.finish());
        }
        if (textRightView != null) {
            mRightTextView.setVisibility(VISIBLE);
            setRightText(textRightView);
        }
        if (mRightImageView != null) {
            mRightImageView.setVisibility(VISIBLE);
            setRightImage(imageRightView);
        }
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor.setBackgroundColor(color);

    }

    public void setLeftImage(Drawable drawable) {
        mLeftImageView.setImageDrawable(drawable);
    }

    public void setRightImage(Drawable drawable) {
        mRightImageView.setImageDrawable(drawable);
    }

    public void setCenterText(String text) {
        mCenterTextView.setText(text);
    }

    public void setRightText(String text) {
        mRightTextView.setText(text);
    }

    public void setTextColor(int color, int rightColor) {
        mCenterTextView.setTextColor(color);
        mRightTextView.setTextColor(rightColor);
    }

    //重写其监听返回的方法,做其他操作
    public void setOnHeaderClickListener(OnClickListener listener) {
        if (imageView != null) mLeftImageView.setOnClickListener(listener);
        if (textRightView != null) mRightTextView.setOnClickListener(listener);
        if (mRightImageView != null) mRightImageView.setOnClickListener(listener);

    }

    public void setTextSize(float textSize, float rightSize) {
        mCenterTextView.setTextSize(textSize);
        mRightTextView.setTextSize(rightSize);
    }
}
