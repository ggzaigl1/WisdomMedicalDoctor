package com.hjy.wisdommedicaldoctor.gratuitous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.aop.resultfilter.ResultCallBack;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.rv.divider.GridItemDecoration;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarUtil;
import com.haibin.calendarview.CalendarViewDelegate;
import com.hjy.wisdommedicaldoctor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 义诊排班
 * Created by fangs on 2018/8/29 11:50.
 */
public class GratuitousSchedulingActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {


    @BindView(R.id.tvHaveScheduling)
    TextView tvHaveScheduling;
    @BindView(R.id.tvButScheduling)
    TextView tvButScheduling;

    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.rvScheduling)
    RecyclerView rvScheduling;
    GratuitousSchedulingAdapter adapter;


    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.gratuitous_scheduling_activity;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        initLab();
        initRv();
    }

    @OnClick({R.id.tvTime, R.id.tvConfirm})
    @ClickFilter
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvTime:
                Bundle bundle = new Bundle();
                bundle.putInt("year", Integer.parseInt(TimeUtils.Long2DataString(System.currentTimeMillis(), "yyyy")));
                JumpUtils.jump(this, MonthSelectActivity.class, bundle, R.id.tvTime, new ResultCallBack() {
                    @Override
                    public void onActResult(int requestCode, int resultCode, @Nullable Intent data) {
                        if (resultCode == RESULT_OK && null != data){
                            Bundle bundle = data.getExtras();
                            Calendar calendar = (Calendar) bundle.getSerializable("Calendar");

                            List<GratuitousSchedulingBean> listData = getData(calendar);
                            adapter.setmDatas(listData);
                            adapter.notifyDataSetChanged();

                            tvTime.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
                        }
                    }
                });
                break;
            case R.id.tvConfirm:

                break;
        }
    }

    private void initLab(){
        tvTime.setText(TimeUtils.Long2DataString(System.currentTimeMillis(), getString(R.string.timeFormat1)));
        TintUtils.setTxtIconLocal(tvTime, TintUtils.getDrawable(R.drawable.svg_arrow_right, 1), 2);

        TintUtils.setTxtIconLocal(tvHaveScheduling, TintUtils.getTintDrawable(R.drawable.svg_rectangle, 1, R.color.haveColor), 0);
        TintUtils.setTxtIconLocal(tvButScheduling, TintUtils.getDrawable(R.drawable.svg_rectangle, 1), 0);
    }

    private void initRv() {
        rvScheduling.addItemDecoration(new GridItemDecoration.Builder()
                .setColumn(7)
                .create(this));
        rvScheduling.setLayoutManager(new GridLayoutManager(this, 7));

        String yearStr = TimeUtils.Long2DataString(System.currentTimeMillis(), "yyyy");
        String monthStr = TimeUtils.Long2DataString(System.currentTimeMillis(), "MM");
        Calendar calendar = new Calendar(Integer.parseInt(yearStr), Integer.parseInt(monthStr));
        adapter = new GratuitousSchedulingAdapter(this, getData(calendar));
        rvScheduling.setAdapter(adapter);
    }

    private List<GratuitousSchedulingBean> getData(Calendar calendar){
        List<GratuitousSchedulingBean> data = new ArrayList<>();

        int StartDiff = CalendarUtil.getMonthViewStartDiff(calendar, CalendarViewDelegate.WEEK_START_WITH_SUN);
        int endDiff = CalendarUtil.getMonthEndDiff(calendar, CalendarViewDelegate.WEEK_START_WITH_SUN);

        String week = "日一二三四五六";
        for (int i = 0; i < 7 + StartDiff; i++){
            data.add(new GratuitousSchedulingBean(i < 7 ? week.charAt(i) + "" : ""));
        }

        int monthDayNum = CalendarUtil.getMonthDaysCount(calendar.getYear(), calendar.getMonth());
        for (int i = 1; i <= monthDayNum; i++){
            data.add(new GratuitousSchedulingBean(i + "", i % 3 == 0 ? 1 : (i % 7 == 0 ? 2 : 0)));
        }

        for (int i = 0; i < endDiff; i++){
            data.add(new GratuitousSchedulingBean());
        }

        return data;
    }
}
