package lzw.app.com.myutils.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuZhaowei on 2019/1/21 0021.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mView;

    public CustomViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mViews = new SparseArray<>();
    }

    public static CustomViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
