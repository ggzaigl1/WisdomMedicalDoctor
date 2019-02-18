package com.hjy.wisdommedicaldoctor.scheduling;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.rv.divider.GridItemDecoration;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.TimeUtils;
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
 * 排班详情
 * Created by fangs on 2018/8/8 09:50.
 */
public class SchedulingDetailsActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener{

    @BindView(R.id.tvTimeInterval)
    TextView tvTimeInterval;

    @BindView(R.id.tvStop)
    TextView tvStop;

    @BindView(R.id.rvScheduling)
    RecyclerView rvScheduling;
    SchedulingNewAdapter adapter;

    int schedulingId;
    int status;
    String startDate;
    String endDate;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.scheduling_activity_details;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        schedulingId = bundle.getInt("id", -1);
        status = bundle.getInt("status", 1);
        startDate = bundle.getString("startDate", "");
        endDate = bundle.getString("endDate", "");

        tvTimeInterval.setText(ResUtils.getReplaceStr(R.string.startAndStop, startDate, endDate));

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getScheduleInfo();
    }

    @OnClick({R.id.tvDelete, R.id.tvEdit, R.id.tvStop})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDelete:
                removeSchedule();
                break;
            case R.id.tvEdit:
                ArrayList<MakeBean> data = (ArrayList<MakeBean>) adapter.getmDatas();
                Bundle bundle = new Bundle();
                bundle.putInt("id", schedulingId);
                bundle.putString("startDate", startDate);
                bundle.putString("endDate", endDate);
                bundle.putParcelableArrayList("MakeList", data);

                JumpUtils.jump(this, SchedulingModifyActivity.class, bundle);
                break;
            case R.id.tvStop:
                changeStatusSchedule();
                break;
        }
    }

    private void init() {
        tvStop.setText(status == 1 ? R.string.disable : R.string.startUsing);

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
        adapter = new SchedulingNewAdapter(this, new ArrayList<>());
        adapter.setClick(false);
        rvScheduling.setAdapter(adapter);
    }

    //生成数据填充 列表
    private List<MakeBean> getData() {
        List<Date> days = TimeUtils.dateToWeek(TimeUtils.timeString2long(startDate, "yyyy-MM-dd"), 0);

        List<MakeBean> data = new ArrayList<>();
        data.add(new MakeBean());
        for (int i = 0; i < 7; i++){
            data.add(new MakeBean(TimeUtils.Data2String(days.get(i), "yyyy-MM-dd"), Constant.StickyType));
        }

        return data;
    }

    //医生可预约时间段
    private void getScheduleInfo() {
        ArrayMap<String, Object> objectMap = new ArrayMap<>();
        objectMap.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        objectMap.put("startDate", startDate);
        objectMap.put("endDate", endDate);
        RequestUtils.create(ApiService.class)
                .scheduleList(objectMap)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<List<MakeBean>>() {
                    @Override
                    protected void onSuccess(List<MakeBean> listData) {
                        List<MakeBean> data = getData();
                        data.addAll(listData);

                        adapter.setmDatas(data);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void updataLayout(int flag) {
                    }
                });
    }

    //删除排班
    private void removeSchedule() {
        RequestUtils.create(ApiService.class)
                .removeSchedule(schedulingId)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<String>(new NetDialog().init(this).setDialogMsg(R.string.loadingDelete)) {
                    @Override
                    protected void onSuccess(String t) {
                        T.showShort(R.string.deleteSuccess);
                        JumpUtils.exitActivity(SchedulingDetailsActivity.this);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

//    停用/启用排班
    private void changeStatusSchedule() {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("id", schedulingId);
        params.put("status", status == 1 ? 2 : 1);//1：启用；2：停用

        RequestUtils.create(ApiService.class)
                .changeStatusSchedule(params)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<String>(new NetDialog().init(this).setDialogMsg(R.string.loadingModify)) {
                    @Override
                    protected void onSuccess(String t) {
                        T.showShort(R.string.modifySuccess);
                        JumpUtils.exitActivity(SchedulingDetailsActivity.this);
                    }

                    @Override
                    protected void updataLayout(int flag) {
                    }
                });
    }
}
