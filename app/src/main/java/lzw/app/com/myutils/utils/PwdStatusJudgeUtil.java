package lzw.app.com.myutils.utils;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.widget.EditText;
import android.widget.ImageView;

import lzw.app.com.myutils.R;


public class PwdStatusJudgeUtil {
    /**
     * 控制密码的显示或者隐藏
     *
     * @param text      文本输入框
     * @param imageView 显示或者隐藏的按钮
     */
    public static void setPwdShowOrHidden(EditText text, ImageView imageView) {
        if (text.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setBackgroundResource(R.mipmap.look_pwd);
        } else {
            text.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setBackgroundResource(R.mipmap.hint_pwd);
        }
        showEnd(text);
    }

    /**
     * Editext光标现在在最后面
     */
    public static void showEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.length());
    }
}
