package com.hjy.wisdommedicaldoctor.scheduling;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.rv.divider.GridItemDecoration;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.GsonUtils;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.MakeBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新建排班
 * Created by fangs on 2018/8/3 15:33.
 */
public class SchedulingNewActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

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
    SchedulingNewAdapter adapter;

    @BindView(R.id.tvMakeSuccess)
    TextView tvMakeSuccess;
    @BindView(R.id.tvCanMakeAppointment)
    TextView tvCanMakeAppointment;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.scheduling_activity_new;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
        initView();

        initRv();
    }

    @OnClick({R.id.tvStartDate, R.id.tvEndDate, R.id.tvConfirm})
    @Override
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
            case R.id.tvConfirm://提交
                newScheduling();
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

        TintUtils.setTxtIconLocal(tvCanMakeAppointment, TintUtils.getDrawable(R.drawable.svg_rectangle, 1), 0);
        TintUtils.setTxtIconLocal(tvMakeSuccess, TintUtils.getDrawable(R.drawable.svg_make, 1), 0);
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
//        rvScheduling.addItemDecoration(new StickyItemDecoration());
        adapter = new SchedulingNewAdapter(this, new ArrayList<>());
        rvScheduling.setAdapter(adapter);
    }

    private void selectTime(long timeMillis, int moudel) {
        List<Date> days = TimeUtils.dateToWeek(timeMillis, moudel);
        adapter.setmDatas(getData(days));
        adapter.notifyDataSetChanged();
    }

    //生成数据填充列表
    private List<MakeBean> getData(List<Date> days) {
        List<MakeBean> data = new ArrayList<>();
        for (int i = 0; i < DocConstant.time.length; i++) {
            for (int l = 0; l < 8; l++) {
                if (i == 0) {
                    if (l == 0) {
                        data.add(new MakeBean(i, Constant.StickyType));
                    } else {
                        data.add(new MakeBean(TimeUtils.Data2String(days.get(l - 1), "yyyy-MM-dd"), Constant.StickyType));
                    }
                } else {
                    if (l == 0) {
                        data.add(new MakeBean(i, DocConstant.timeTitle));
                    } else {
                        data.add(new MakeBean(i, TimeUtils.Data2String(days.get(l - 1), "yyyy-MM-dd"), 1));
                    }
                }
            }
        }

        return data;
    }

    private void newScheduling() {
        List<SchedulingSubmitBian> list = new ArrayList<>();
        SparseBooleanArray array = adapter.getmSelectedPositions();
        for (int i = 0; i < array.size() ; i++){
            if (array.valueAt(i)){
                MakeBean bean = adapter.getmDatas().get(array.keyAt(i));
                list.add(new SchedulingSubmitBian(bean.getDate(), bean.getTime()));
            }
        }

        if (list.size() == 0){
            T.showShort(R.string.schedulingTime);
            return;
        }

        ArrayMap<String, Object> parame = new ArrayMap<>();
        parame.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        parame.put("startDate", tvStartDate.getText().toString().trim().substring(0, 11));
        parame.put("endDate", tvEndDate.getText().toString().trim().substring(0, 11));
        parame.put("listSchedule", GsonUtils.listToJson(list));

        RequestUtils.create(ApiService.class)
                .newScheduling(parame)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<String>((new NetDialog().init(this).setDialogMsg(R.string.loadingSubmit))) {
                    @Override
                    protected void onSuccess(String tips) {
                        T.showShort(R.string.successSubmit);
                        JumpUtils.exitActivity(SchedulingNewActivity.this);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }
}
