package xst.app.com.myutils.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import xst.app.com.myutils.R;

/**
 * Created by LiuZhaowei on 2017/12/25.
 */

public class LoadingUtil extends Dialog {
    private Context mContext;
    private TextView messageTv;

    public LoadingUtil(@NonNull Context context) {
        this(context, R.style.progress_dialog);
    }

    private LoadingUtil(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.loading_view, null);
        setContentView(view);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setCanceledOnTouchOutside(false);
        messageTv = view.findViewById(R.id.load_tv);
    }

    public void show(String message) {
        super.show();
        if (TextUtils.isEmpty(message)) {
            messageTv.setText("正在努力加载...");
        } else {
            messageTv.setText(message);
        }
    }
}
