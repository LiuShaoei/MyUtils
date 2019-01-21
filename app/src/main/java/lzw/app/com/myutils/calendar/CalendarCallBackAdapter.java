package lzw.app.com.myutils.calendar;

import android.view.View;

public abstract class CalendarCallBackAdapter {
    public abstract View getView(int[] days, View view, int position, int year, int month);

    public abstract void upDate(int year, int month);
}
