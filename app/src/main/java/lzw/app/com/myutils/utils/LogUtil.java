package lzw.app.com.myutils.utils;

import android.util.Log;

/**
 * 日志操作工具类
 */

public class LogUtil {
    //日志打印控制开关
    private static final boolean isPrint = true;
    private static final String TAG = "code";
    private static final String TAG_LOVE = "love";

    /**
     * 单一信息打印
     *
     * @param message
     */
    public static void i(String message) {
        if (isPrint) {
            Log.i(TAG, message);
        }
    }

    public static void iMsg(String message) {
        if (isPrint) {
            Log.i(TAG_LOVE, message);
        }
    }

    /**
     * 多个信息打印
     *
     * @param message
     */
    public static void printAll(String... message) {
        if (isPrint) {
            StringBuilder builder = new StringBuilder();
            for (String str : message) {
                builder.append(str + "\n");
            }
            Log.i(TAG, builder.toString());
        }
    }


}
