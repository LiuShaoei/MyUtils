package lzw.app.com.myutils.utils;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lzw.app.com.myutils.R;


/**
 * 动态设置沉浸式状态栏，通过添加占位状态栏
 */

public class StatusBarUtil {
    /**
     * 设置默认颜色的状态栏
     *
     * @param activity
     * @param bar
     */
    public static void init(Activity activity, LinearLayout bar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取手机状态栏高度
            int height = getBarHight(activity);
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
            bar.setBackgroundColor(activity.getResources().getColor(R.color.app_color));
            params.height = height;
            bar.setLayoutParams(params);
        }
    }

    public static void initHight(Activity activity, RelativeLayout bar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取手机状态栏高度
            int height = getBarHight(activity);
            //动态的设置隐藏布局的高度
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
            bar.setBackgroundColor(activity.getResources().getColor(R.color.app_color));
            params.height = height;
            bar.setLayoutParams(params);
        }
    }

    /**
     * 设置指定颜色的状态栏
     *
     * @param activity
     * @param bar
     * @param color
     */
    public static void setBarColor(Activity activity, LinearLayout bar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取手机状态栏高度
            int height = getBarHight(activity);
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
            bar.setBackgroundColor(color);
            params.height = height;
            bar.setLayoutParams(params);
        }
    }
    public static void setBarColorR(Activity activity, RelativeLayout bar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取手机状态栏高度
            int height = getBarHight(activity);
            //动态的设置隐藏布局的高度
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
            bar.setBackgroundColor(color);
            params.height = height;
            bar.setLayoutParams(params);
        }
    }


    public static int getBarHight(Activity activity) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
