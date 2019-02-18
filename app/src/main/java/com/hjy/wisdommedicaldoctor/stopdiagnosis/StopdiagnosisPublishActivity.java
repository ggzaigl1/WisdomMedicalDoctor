package com.hjy.wisdommedicaldoctor.stopdiagnosis;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.rv.divider.GridItemDecoration;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.SchedulingBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.scheduling.SchedulingNewSelectDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 停诊发布
 * Created by fangs on 2018/8/15 16:48.
 */
public class StopdiagnosisPublishActivity extends AppCompatActivity implements IBaseActivity{

    @BindView(R.id.tagStartDate)
    TextView tagStartDate;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;

    @BindView(R.id.tagEndDate)
    TextView tagEndDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;

    @BindView(R.id.rvScheduling)
    RecyclerView rvScheduling;
    StopDiagnosisPublishAdapter adapter;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.stop_diagnosis_activity_publish;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
        initView();

        initRv();
    }

    @ClickFilter
    @OnClick({R.id.tvStartDate, R.id.tvEndDate, R.id.tvSubmit})
    public void onClick(View v) {
        SchedulingNewSelectDialog dialog = new SchedulingNewSelectDialog();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tvStartDate:
                bundle.putString("SetTimeTag", "设置开始时间");
                dialog.setArguments(bundle);
                dialog.setDataListener(selectTimeMillis -> {
                    if (selectTimeMillis >= TimeUtils.initDateByDay(System.currentTimeMillis())){
                        tvStartDate.setText(TimeUtils.Long2DataString(selectTimeMillis, "yyyy-MM-dd HH:mm"));
                        tvEndDate.setText(TimeUtils.Long2DataString(selectTimeMillis + 7 * 24 * 3600000 - 1, "yyyy-MM-dd HH:mm"));
                        selectTime(selectTimeMillis, 0);
                    } else {
                        T.showShort("请选择今天零点以后的时间");
                    }
                });
                dialog.show(getSupportFragmentManager());
                break;
            case R.id.tvEndDate:
                bundle.putString("SetTimeTag", "设置结束时间");
                dialog.setArguments(bundle);
                dialog.setDataListener(selectTimeMillis -> {
                    long startTime = selectTimeMillis - 6 * 24 * 3600000;
                    long endTime = selectTimeMillis + 24 * 3600000 - 1;

                    if (endTime == TimeUtils.initDateByDay(System.currentTimeMillis())
                            + 7 * 24 * 3600000 - 1){
                        tvEndDate.setText(TimeUtils.Long2DataString(endTime, "yyyy-MM-dd HH:mm"));
                        tvStartDate.setText(TimeUtils.Long2DataString(startTime, "yyyy-MM-dd HH:mm"));
                        selectTime(selectTimeMillis, 1);
                    } else {
                        T.showShort("请选择今天零点以后7天的时间");
                    }
                });
                dialog.show(getSupportFragmentManager());
                break;
            case R.id.tvSubmit://todo 提示对话框

                break;
        }
    }

    private void initView() {
        Drawable calendarDra = TintUtils.getTintDrawable(R.drawable.svg_schedule, 1, R.color.calendar);
        TintUtils.setTxtIconLocal(tagStartDate, calendarDra, 0);
        TintUtils.setTxtIconLocal(tagEndDate, calendarDra, 0);

        Drawable rightArrow = TintUtils.getDrawable(R.drawable.svg_arrow_right, 1);
        TintUtils.setTxtIconLocal(tvStartDate, rightArrow, 2);
        TintUtils.setTxtIconLocal(tvEndDate, rightArrow, 2);
    }

    private void initRv() {
        rvScheduling.addItemDecoration(new GridItemDecoration.Builder()
                .setColumn(8)
                .create(this));
        GridLayoutManager manager = new GridLayoutManager(this, 10);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 8 == 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rvScheduling.setLayoutManager(manager);
        adapter = new StopDiagnosisPublishAdapter(this, new ArrayList<>());
        rvScheduling.setAdapter(adapter);
    }

    private void selectTime(long timeMillis, int moudel) {
        List<Date> days = TimeUtils.dateToWeek(timeMillis, moudel);
        adapter.setmDatas(getData(days));
        adapter.notifyDataSetChanged();
    }

    //生成假数据填充 列表
    private List<SchedulingBean> getData(List<Date> days) {
        List<SchedulingBean> data = new ArrayList<>();
        for (int i = 0; i < DocConstant.time.length; i++) {
            for (int l = 0; l < 8; l++) {
                if (i == 0) {
                    if (l == 0) {
                        data.add(new SchedulingBean(DocConstant.time[i]));
                    } else {
                        data.add(new SchedulingBean(days.get(l - 1)));
                    }
                } else {
                    if (l == 0) {
                        data.add(new SchedulingBean(DocConstant.time[i]));
                    } else {
                        if (l % 3 == 0) data.add(new SchedulingBean(i, DocConstant.time[i], days.get(l - 1), 1));
                        else if (l % 2 == 0)
                            data.add(new SchedulingBean(i, DocConstant.time[i], days.get(l - 1), 1));
                        else data.add(new SchedulingBean(i, DocConstant.time[i], days.get(l - 1), 0));
                    }
                }
            }
        }

        return data;
    }
}
