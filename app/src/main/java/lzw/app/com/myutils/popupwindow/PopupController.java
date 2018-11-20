package lzw.app.com.myutils.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by LiuZhaowei on 2018/11/13 0013.
 */
public class PopupController {
    private int layoutResId;//布局的Id
    private Context context;
    private PopupWindow popupWindow;
    View mPopupView;//弹窗布局View
    private View mView;
    private Window mWindow;
    
    PopupController(Context context,PopupWindow popupWindow){
        this.context = context;
        this.popupWindow = popupWindow;
    }
    
    public void setView(int layoutResId){
        mView = null;
        this.layoutResId = layoutResId;
        installContent();
    }

    public void setView(View view){
        mView = view;
        this.layoutResId = 0;
        installContent();
    }

    private void installContent() {
        if(layoutResId != 0){
            mPopupView  = LayoutInflater.from(context).inflate(layoutResId,null);
        }else if(mView != null){
            mPopupView = mView;
        }
        popupWindow.setContentView(mPopupView);
    }

    /**
     * 设置宽高
     * @param width
     * @param height
     */
    private void setWidthAndHeight(int width ,int height){
        if(width == 0 || height ==0){
            //如果没有设置宽高,默认是WRAP_CONTENT
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }else{
            popupWindow.setWidth(width);
            popupWindow.setHeight(height);
        }
    }

    /**
     * 设置背景的灰色程度
     * @param level
     */
    public void setBackGroundLevel(float level){
        mWindow = ((Activity)context).getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = level;
        mWindow.setAttributes(params);
    }

    /**
     * 设置动画效果
     * @param animationStyle
     */
    private void setAnimationStyle(int animationStyle){
        popupWindow.setAnimationStyle(animationStyle);
    }

    /**
     *设置outside是否可点击
     * @param touchable
     */
    private void setOutsideTouchable(boolean touchable){
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置透明背景
        popupWindow.setOutsideTouchable(touchable);//设置outside可点击
        popupWindow.setFocusable(touchable);
    }

    static class PopupParams{
        public int layoutResId;//布局Id
        public Context mContext;
        public int mWidth,mHeight;
        public boolean isShowBg,isShowAnim;
        public float bgLevel;
        public int animationStyle;//动画Id
        public View mView;
        public boolean isTouchable = true;

        public PopupParams (Context context){
            this.mContext = context;
        }

        public void apply(PopupController controller){
            if(mView != null){
                controller.setView(mView);
            }else if(layoutResId != 0){
                controller.setView(layoutResId);
            }else{
                throw new IllegalArgumentException("PopupView's contentView is null");
            }
            controller.setWidthAndHeight(mWidth,mHeight);
            controller.setOutsideTouchable(isTouchable);
            if(isShowBg){
                //设置背景
                controller.setBackGroundLevel(bgLevel);
            }
            if(isShowAnim){
                controller.setAnimationStyle(animationStyle);
            }
        }
    }
}