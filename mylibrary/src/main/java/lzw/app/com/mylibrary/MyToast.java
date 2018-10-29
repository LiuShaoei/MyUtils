package lzw.app.com.mylibrary;

import android.content.Context;

/**
 * Created by LiuZhaowei on 2018/10/9 0009.
 */

public class MyToast {
    private static  android.widget.Toast toast;
    public static void TaostShow(Context context,String message){
        if(toast == null){
            toast = android.widget.Toast.makeText(context,message, android.widget.Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

}
