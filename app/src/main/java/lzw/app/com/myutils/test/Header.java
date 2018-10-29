package lzw.app.com.myutils.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
    private ImageView mImageView;
    private TextView mTextView;

    private int backGroundColor;
    private String textView;
    private Drawable imageView;
    private int textColor;
    private float textSize;

    public Header(Context context) {
        super(context);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载视图布局
        LayoutInflater.from(context).inflate(R.layout.header, this, true);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Header);
        textView = a.getString(R.styleable.Header_titleText);
        imageView = a.getDrawable(R.styleable.Header_titleLeftImg);
        backGroundColor = a.getColor(R.styleable.Header_titleBackgroundColor, Color.WHITE);
        textColor = a.getColor(R.styleable.Header_titleTextColor, Color.WHITE);
        textSize = a.getDimension(R.styleable.Header_titleTextSize, 30);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundColor = (RelativeLayout) findViewById(R.id.background_color);
        mImageView = (ImageView) findViewById(R.id.title_left_img);
        mTextView = (TextView) findViewById(R.id.title_text);


        if (!TextUtils.isEmpty(textView)) {
            setmBackgroundColor(backGroundColor);
            setmImageView(imageView);
            setmTextView(textView);
            setTextColor(textColor);
            setTextSize(textSize);
        }
    }

    public void setmBackgroundColor(int color) {
        mBackgroundColor.setBackgroundColor(color);

    }

    public void setmImageView(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }

    public void setmTextView(String text) {
        mTextView.setText(text);
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

    public void setOnheaderClickListener(OnClickListener listener) {
        mImageView.setOnClickListener(listener);
    }

    public void setTextSize(float textSize) {
        mTextView.setPadding(0, (int) textSize, 0, 0);
    }
}
