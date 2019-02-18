package com.hjy.wisdommedicaldoctor.scheduling;

import android.app.Activity;
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
import com.fy.baselibrary.utils.GsonUtils;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.MakeBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改排班
 * Created by fangs on 2018/9/17 14:47.
 */
public class SchedulingModifyActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener{

    @BindView(R.id.rvScheduling)
    RecyclerView rvScheduling;
    SchedulingNewAdapter adapter;

    @BindView(R.id.tvMakeSuccess)
    TextView tvMakeSuccess;
    @BindView(R.id.tvCanMakeAppointment)
    TextView tvCanMakeAppointment;

    int schedulingId;
    String startDate;
    String endDate;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.scheduling_activity_modify;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
        initView();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        schedulingId = bundle.getInt("id", -1);
        startDate = bundle.getString("startDate", "");
        endDate = bundle.getString("endDate", "");
        ArrayList<MakeBean> data = bundle.getParcelableArrayList("MakeList");

        for (MakeBean makeBean : data) {
            int status = makeBean.getStatus() == 0 ? 1 : (makeBean.getStatus() == 1 ? 2 : 2);
            makeBean.setStatus(status);
        }

        initRv(data);
    }

    @OnClick({R.id.tvModify})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvModify://修改 排班按钮
                getScheduleInfo();
                break;
        }
    }

    private void initView() {
        TintUtils.setTxtIconLocal(tvCanMakeAppointment, TintUtils.getDrawable(R.drawable.svg_rectangle, 1), 0);
        TintUtils.setTxtIconLocal(tvMakeSuccess, TintUtils.getDrawable(R.drawable.svg_make, 1), 0);
    }

    private void initRv(ArrayList<MakeBean> data) {
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
        adapter = new SchedulingNewAdapter(this, data);
        rvScheduling.setAdapter(adapter);
    }

    //修改排班
    private void getScheduleInfo() {
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

        ArrayMap<String, Object> objectMap = new ArrayMap<>();
        objectMap.put("id", schedulingId);
        objectMap.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        objectMap.put("startDate", startDate);
        objectMap.put("endDate", endDate);
        objectMap.put("listSchedule", GsonUtils.listToJson(list));

        RequestUtils.create(ApiService.class)
                .updateSchedule(objectMap)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<String>(new NetDialog().init(this).setDialogMsg(R.string.loadingModify)) {
                    @Override
                    protected void onSuccess(String str) {
                        T.showShort(R.string.modifySuccess);
                        JumpUtils.exitActivity(SchedulingModifyActivity.this);
                    }

                    @Override
                    protected void updataLayout(int flag) {
                    }
                });
    }
}
