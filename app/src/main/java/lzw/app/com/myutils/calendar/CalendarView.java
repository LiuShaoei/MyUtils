package lzw.app.com.myutils.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


import java.util.Calendar;

import lzw.app.com.myutils.R;

public class CalendarView extends LinearLayout {
    private int mDays;//当月一个月有多少天
    private int mLastMonthDays;//上个月有多少天
    private int mWeek;//获取某月的1号是星期几
    private int mYear;//当前年份
    private int mMonth;//当前月份
    private Context mContext;
    private MyGridView mMyGridView;
    private CalendarAdapter mCalendarAdapter;
    private CalendarCallBackAdapter mCallBackAdapter;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate(context, R.layout.calendar_view, this);
        initData();

    }

    private void initData() {
        mMyGridView = findViewById(R.id.calendar_view);
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);//月份是以0开头,要+1
        mWeek = getWeekDayOfMonth(mYear, mMonth);
        mDays = getDaysOfMonth(mYear, mMonth);
        if (mMonth - 1 < 0) {
            mLastMonthDays = getDaysOfMonth(mYear - 1, 11);
        } else {
            mLastMonthDays = getDaysOfMonth(mYear, mMonth - 1);
        }
        //这里+1是为了第一次回调出去的不一致的问题
        mMonth += 1;
    }

    /**
     * 设置指定年月的日历
     *
     * @param year  年
     * @param month 月0-13,
     */
    public void setYearAndMonth(int year, int month) {
        if (String.valueOf(year).length() == 4 && month >= 0 && month <= 13) {
            if (month == 13) {
                month = 1;
                year += 1;
            } else if (month == 0) {
                month = 12;
                year -= 1;
            }
            //回调出去的月份可以直接使用,不用+1;
            mCallBackAdapter.upDate(year, month);
            mCalendarAdapter.getEveryDayUpDate(year, month);
        } else {

        }
    }

    /**
     * 设置自定义的adapter
     *
     * @param adapter
     */
    public void setAdapter(CalendarCallBackAdapter adapter) {
        mCalendarAdapter = new CalendarAdapter();
        mCallBackAdapter = adapter;
        mMyGridView.setAdapter(mCalendarAdapter);
    }

    /**
     * 当请求到数据后通知数据更新
     */
    public void notifyDataSetChanged() {
        if (mCalendarAdapter == null) {
            throw new NullPointerException("adapter is null");
        } else {
            mCalendarAdapter.notifyDataSetChanged();
        }
    }


    class CalendarAdapter extends BaseAdapter {

        private int[] dayNumber;

        private CalendarAdapter() {
            dayNumber = new int[42];
            getEveryDay();
        }

        @Override
        public int getCount() {
            if (dayNumber[35] < 0) {
                return 35;
            } else {
                if (mWeek == 5 || mWeek == 6) {
                    return 42;
                } else {
                    return 35;
                }
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return dayNumber[i];
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //这里返回出去的月份可以直接操作 1-12;
            view = mCallBackAdapter.getView(dayNumber, view, position, mYear, mMonth);
            return view;
        }

        private void getEveryDay() {
            for (int i = 0; i < 42; i++) {
                //遍历每一个格子,里面放入数值,位置是从0开始
                //1.需要显示上月下月日期
                if (i < mWeek) {
                    dayNumber[i] = -mLastMonthDays + mWeek - 1 - i;
                } else if (i < mDays + mWeek) {
                    dayNumber[i] = i - mWeek + 1;
                } else {
                    dayNumber[i] = mDays + mWeek - i - 1;
                }
                //2.不需要显示上月下月日期
//                if (i < mDays + mWeek && i >= mWeek) {
//                    //当月总天数 + 当月第一天日期星期
//                    dayNumber[i] = i - mWeek + 1;
//                } else {
//                    dayNumber[i] = 0;
//                }
            }
        }

        /**
         * 更新获取每个月的日历
         *
         * @param year
         * @param month
         */
        private void getEveryDayUpDate(int year, int month) {
            mWeek = getWeekDayOfMonth(year, month - 1);
            mDays = getDaysOfMonth(year, month - 1);
            mYear = year;
            mMonth = month;
            if (month - 1 == 0) {
                mLastMonthDays = getDaysOfMonth(year - 1, 11);
            } else {
                mLastMonthDays = getDaysOfMonth(year, month - 2);
            }
//            for (int i = 0; i < 42; i++) {
//                if (i < mDays + mWeek && i >= mWeek) {
//                    //当月总天数 + 当月第一天日期星期
//                    dayNumber[i] = i - mWeek + 1;
//                } else {
//                    dayNumber[i] = 0;
//                }
//            }
            for (int i = 0; i < 42; i++) {
                //遍历每一个格子,里面放入数值,位置是从0开始
                //1.需要显示上月下月日期
                if (i < mWeek) {
                    dayNumber[i] = -mLastMonthDays + mWeek - 1 - i;
                } else if (i < mDays + mWeek) {
                    dayNumber[i] = i - mWeek + 1;
                } else {
                    dayNumber[i] = mDays + mWeek - i - 1;
                }
            }
            notifyDataSetChanged();
        }
    }


    /**
     * 获取某年某月一共有多少天
     *
     * @param year
     * @param month 月份是以0开头
     * @return
     */
    private int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到某年某月的1号是星期几
     *
     * @param year
     * @param month
     * @return
     */
    private int getWeekDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

}
