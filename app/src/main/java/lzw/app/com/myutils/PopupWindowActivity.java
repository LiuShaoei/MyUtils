package lzw.app.com.myutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import lzw.app.com.myutils.popupwindow.CustomPopupWindow;
import lzw.app.com.myutils.popupwindow.CustomUtil;
import lzw.app.com.myutils.utils.DensityUtils;
import lzw.app.com.myutils.utils.ToastUtil;

/**
 * Created by LiuZhaowei on 2018/11/13 0013.
 */
public class PopupWindowActivity extends AppCompatActivity  implements CustomPopupWindow.ViewInterface  {

    private CustomPopupWindow popupWindow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
    }

    //向下弹出
    public void showDownPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.popup_down)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(true)
                .create();
        popupWindow.showAsDropDown(view);
        //得到button的左上角坐标
//        int[] positions = new int[2];
//        view.getLocationOnScreen(positions);
//        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, positions[1] + view.getHeight());
    }
    //向右弹出
    public void showRightPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimHorizontal)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, view.getWidth(), -view.getHeight());
        //得到button的左上角坐标
//        int[] positions = new int[2];
//        view.getLocationOnScreen(positions);
//        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, positions[0] + view.getWidth(), positions[1]);
    }
    //向左弹出
    public void showLeftPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, -popupWindow.getWidth(), -view.getHeight());
        //得到button的左上角坐标
//        int[] positions = new int[2];
//        view.getLocationOnScreen(positions);
//        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, positions[0] - popupWindow.getWidth(), positions[1]);
    }
    //向上弹出
    public void showUpPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, 0, -(popupWindow.getHeight() + view.getMeasuredHeight()));

        //得到button的左上角坐标
//        int[] positions = new int[2];
//        view.getLocationOnScreen(positions);
//        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, positions[0], positions[1] - popupWindow.getHeight());
    }

    //全屏弹出
    public void showAll(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_up, null);
        //测量View的宽高
        CustomUtil.measureWidthAndHeight(upView);
        popupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.popup_up)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    public void showReminder(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.query_info)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        popupWindow.showAsDropDown(view, (int) (-popupWindow.getWidth() + DensityUtils.dip2px(this, 20)),
                -(popupWindow.getHeight() + view.getMeasuredHeight()));
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        switch (layoutResId) {
            case R.layout.popup_down:
                TextView recycle_view = (TextView) view.findViewById(R.id.test);
                break;
            case R.layout.popup_up:
                Button btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
                Button btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
                Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                btn_take_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                btn_select_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        return true;
                    }
                });
                break;
            case R.layout.popup_left_or_right:
                TextView tv_like = (TextView) view.findViewById(R.id.tv_like);
                TextView tv_hate = (TextView) view.findViewById(R.id.tv_hate);
                tv_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShort("赞一个");
                        popupWindow.dismiss();
                    }
                });
                tv_hate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShort("踩一下");
                        popupWindow.dismiss();
                    }
                });
                break;
        }
    }



    public static void toPopupWindowActivity(Activity activity){
        Intent intent = new Intent(activity,PopupWindowActivity.class);
        activity.startActivity(intent);
    }
}
