package lzw.app.com.myutils.calendar;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lzw.app.com.myutils.R;
import lzw.app.com.myutils.utils.CustomViewHolder;

public class CalendarActivity extends AppCompatActivity {
    CalendarView mCalendar;
    RecyclerView mRecycleView;
    TextView mTimeTitle;
    private ViewHolder mViewHolder;
    private RecyclerView.Adapter<CustomViewHolder> mAdapter;
    private List<CalendarDataBean> mList;
    private int mCurrentYear;
    private int mCurrentMonth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_calendar_activity);
        mCalendar = findViewById(R.id.calendar);
        mRecycleView = findViewById(R.id.recycle_view);
        mTimeTitle = findViewById(R.id.time_title);
        initData();
    }

//    @OnClick({R.id.next, R.id.last})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.next:
//                mCurrentMonth++;
//                mCalendar.setYearAndMonth(mCurrentYear, mCurrentMonth);
//                break;
//            case R.id.last:
//                mCurrentMonth--;
//                mCalendar.setYearAndMonth(mCurrentYear, mCurrentMonth);
//                break;
//        }
//    }

    protected void initData() {
        mCalendar.setAdapter(new CalendarCallBackAdapter() {
            @Override
            public View getView(int[] days, View view, int position, int year, int month) {
                mTimeTitle.setText(year + "年" + month + "月");
                mCurrentYear = year;
                mCurrentMonth = month;
                if (view == null) {
                    view = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.m_calendar_item_view, null);
                    mViewHolder = new ViewHolder(view);
                    view.setTag(mViewHolder);
                } else {
                    mViewHolder = (ViewHolder) view.getTag();
                }

                mViewHolder.day.setText("");
                mViewHolder.content.setText("");
                mViewHolder.day.setTextColor(getResources().getColor(R.color.tv_black));

                mViewHolder.day.setText(days[position] < 0 ? Math.abs(days[position]) + "" : days[position] + "");
                if (days[position] < 0) {
                    mViewHolder.day.setTextColor(getResources().getColor(R.color.tv_black));
                } else {
                    mViewHolder.day.setTextColor(getResources().getColor(R.color.tv_black));
                }
                if (days[position] == 4) {
                    mViewHolder.content.setText("1200");
                    mViewHolder.content.setTextColor(getResources().getColor(R.color.red));
                }
                if (days[position] == 5) {
                    mViewHolder.content.setText("800");
                    mViewHolder.content.setTextColor(getResources().getColor(R.color.red));
                }
                if (days[position] == 19) {
                    mViewHolder.content.setText("880");
                    mViewHolder.content.setTextColor(getResources().getColor(R.color.tv_black));
                }
                if (days[position] == 13) {
                    mViewHolder.content.setText("今天");
                    mViewHolder.day.setTextColor(getResources().getColor(R.color.app_color));
                    mViewHolder.content.setTextColor(getResources().getColor(R.color.app_color));
                }
                return view;
            }

            @Override
            public void upDate(int year, int month) {
                mTimeTitle.setText(year + "年" + month + "月");
            }
        });

        mList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            CalendarDataBean bean = new CalendarDataBean();
            bean.setPlatform("支付宝");
            bean.setAmount(1523 + i);
            if (i % 2 == 0) {
                bean.setState(true);
            } else {
                bean.setState(false);
            }
            mList.add(bean);

        }
        LinearLayoutManager manager = new LinearLayoutManager(CalendarActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mAdapter = new RecyclerView.Adapter<CustomViewHolder>() {
            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return CustomViewHolder.get(CalendarActivity.this, parent, R.layout.m_calendar_list_holder);
            }

            @Override
            public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
                CalendarDataBean bean = mList.get(position);
                TextView tPlatform = holder.getView(R.id.lend_platform);
                TextView tAmount = holder.getView(R.id.lend_amount);
                TextView tState = holder.getView(R.id.lend_state);
                TextView tDate = holder.getView(R.id.lend_date);
                TextView tPeriods = holder.getView(R.id.lend_periods);
                TextView tDateState = holder.getView(R.id.lend_date_state);
                if (bean.isState()) {
                    tDateState.setVisibility(View.GONE);
                    tPlatform.setText(mList.get(position).getPlatform());
                    tAmount.setText(mList.get(position).getAmount() + "");
                    tState.setText("已还");
                    tState.setBackground(null);
                    tDate.setTextColor(getResources().getColor(R.color.tv_grayer));
                    tPlatform.setTextColor(getResources().getColor(R.color.tv_grayer));
                    tAmount.setTextColor(getResources().getColor(R.color.tv_grayer));
                    tPeriods.setTextColor(getResources().getColor(R.color.tv_grayer));
                    tState.setTextColor(getResources().getColor(R.color.tv_grayer));
                } else {
                    tDateState.setVisibility(View.VISIBLE);
                    tState.setText("待还");
                    tDate.setTextColor(getResources().getColor(R.color.tv_black));
                    tPlatform.setTextColor(getResources().getColor(R.color.tv_black));
                    tAmount.setTextColor(getResources().getColor(R.color.tv_black));
                    tPeriods.setTextColor(getResources().getColor(R.color.tv_black));
                    tState.setTextColor(getResources().getColor(R.color.app_color));
                }

            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        });
    }


    public class ViewHolder {
        private TextView day;
        private TextView content;

        public ViewHolder(View view) {
            this.day = view.findViewById(R.id.day);
            this.content = view.findViewById(R.id.content);
        }
    }
}
