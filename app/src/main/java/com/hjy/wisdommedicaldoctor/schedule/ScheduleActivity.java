package com.hjy.wisdommedicaldoctor.schedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.ioc.ConfigUtils;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.TodayBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 日程
 * Created by fangs on 2018/7/25 16:40.
 */
public class ScheduleActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener,
        CalendarView.OnDateSelectedListener, CalendarView.OnYearChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    private int mYear;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String mCurrentDate;
    private TextView mTvDateTitle;
    private TextView mTvMsg;
    private TextView mTvVideo;
    private TextView mTvSoundRecording;
    private int text_image_number = 0;
    private int video_number = 0;
    private int voice_number = 0;
    private ScheduleAdapter mAdapter;


    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_schedule;
    }

    @StatusBar(statusColor = R.color.statusBarColor, navColor = R.color.navigationBarColor)
    @SuppressLint("SetTextI18n")
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {

        Drawable backImg = TintUtils.getTintDrawable(ConfigUtils.getBackImg(), 1, R.color.white);
        toolbar.setNavigationIcon(backImg);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setBackgroundColor(ResUtils.getColor(R.color.titleBar));
        toolbarTitle.setTextColor(ResUtils.getColor(R.color.white));

        initRv();
        initCalendar();

        mYear = calendarView.getCurYear();
        toolbarTitle.setText(calendarView.getCurYear() + "年");
        mTvDateTitle.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        mCurrentDate = transformDate(calendarView.getCurYear(), calendarView.getCurMonth(), calendarView.getCurDay());
        allTODO(mCurrentDate);


        calendarView.setOnDateSelectedListener(this);
        calendarView.setOnYearChangeListener(this);


    }

    /**
     * 转化日期格式
     *
     * @param curYear
     * @param curMonth
     * @param curDay
     */
    private String transformDate(int curYear, int curMonth, int curDay) {
        String curMonthStr;
        String curDayStr;
        curMonthStr = curMonth < 10 ? "0" + curMonth : curMonth + "";
        curDayStr = curDay < 10 ? "0" + curDay : curDay + "";
        return curYear + "-" + curMonthStr + "-" + curDayStr;
    }

    private void allTODO(String date) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        objectMap.put("date", date);

        RequestUtils.create(ApiService.class)
                .allTODO(objectMap)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<TodayBean>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onSuccess(TodayBean t) {
                        mAdapter.setNewData(t.getListConsultRecord());
                        if (t.getListTempConsultRecord() != null && t.getListTempConsultRecord().size() != 0) {
                            List<TodayBean.ListTempConsultRecordBean> listTempConsultRecord = t.getListTempConsultRecord();
                            for (TodayBean.ListTempConsultRecordBean listTempConsultRecordBean : listTempConsultRecord) {
                                int serviceId = listTempConsultRecordBean.getServiceId();
                                switch (serviceId) {
                                    case 1:
                                        text_image_number++;
                                        break;
                                    case 2:
                                        video_number++;
                                        break;
                                    case 3:
                                        voice_number++;
                                        break;
                                }

                            }
                            mTvMsg.setText(text_image_number + "");
                            mTvVideo.setText(video_number + "");
                            mTvSoundRecording.setText(voice_number + "");
                        } else {
                            mTvMsg.setText("0");
                            mTvVideo.setText("0");
                            mTvSoundRecording.setText("0");
                        }

                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        View menuLayout = menu.findItem(R.id.menuSchedule).getActionView();
        menuLayout.setOnClickListener(this);

        AppCompatImageView imgMenu = menuLayout.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.svg_schedule);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onYearChange(int year) {
        toolbarTitle.setText(year + "年");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mYear = calendar.getYear();
        toolbarTitle.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
        mTvDateTitle.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        String date = transformDate(calendar.getYear(), calendar.getMonth(), calendar.getDay());
        text_image_number = 0;
        video_number = 0;
        voice_number = 0;
        allTODO(date);
    }

    @OnClick({R.id.toolbarTitle})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbarTitle:
                if (!calendarView.isYearSelectLayoutVisible()) {
                    calendarView.showYearSelectLayout(mYear);
                } else {
                    calendarView.closeYearSelectLayout();
                }
                break;
            case R.id.menuLayout://菜单按钮
                calendarView.scrollToCurrent();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!calendarView.isYearSelectLayoutVisible()) {
            super.onBackPressed();
        } else {
            calendarView.closeYearSelectLayout();
        }
    }

    private void initCalendar() {
        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    private void initRv() {
        View view = LayoutInflater.from(ScheduleActivity.this).inflate(R.layout.item_schedule_title, (ViewGroup) recyclerView.getParent(), false);
        mTvDateTitle = view.findViewById(R.id.tvDateTitle);
        mTvMsg = view.findViewById(R.id.tvMsg);
        mTvVideo = view.findViewById(R.id.tvVideo);
        mTvSoundRecording = view.findViewById(R.id.tvSoundRecording);
        TintUtils.setTxtIconLocal(mTvMsg, TintUtils.getDrawable(R.drawable.svg_msg, 1), 0);
        TintUtils.setTxtIconLocal(mTvVideo, TintUtils.getDrawable(R.drawable.svg_video, 1), 0);
        TintUtils.setTxtIconLocal(mTvSoundRecording, TintUtils.getDrawable(R.drawable.svg_sound_recording, 1), 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ScheduleAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(view);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("main", true);
                bundle.putInt("serviceId",mAdapter.getData().get(position).getServiceId());
                Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
