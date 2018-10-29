package lzw.app.com.myutils.utils;

import android.widget.Toast;

import lzw.app.com.myutils.App;

/**
 * Created by LiuZhaowei on 2018/9/27 0027.
 */

public class ToastUtil {
    private static Toast mToast;

    public static void showShort(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.instance, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void showLong(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.instance, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.show();

    }
}
