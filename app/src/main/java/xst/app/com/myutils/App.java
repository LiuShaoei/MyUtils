package xst.app.com.myutils;

import android.app.Application;

/**
 * Created by Administrator on 2018/9/27 0027.
 */

public class App extends Application {
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
