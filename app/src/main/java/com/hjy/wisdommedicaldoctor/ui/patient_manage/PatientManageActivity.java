package com.hjy.wisdommedicaldoctor.ui.patient_manage;

import android.app.Activity;
import android.net.VpnService;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.PatientManageAdapter;
import com.hjy.wisdommedicaldoctor.bean.PatientListBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 患者管理 Activity
 * Created by Stefan on 2018/7/24 9:30.
 */
public class PatientManageActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    PatientManageAdapter mAdapter;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_patient_manage;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        GetPatientList();
        initView();
    }

    private void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PatientManageAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("patientBean", mAdapter.getData().get(position));
            JumpUtils.jump(PatientManageActivity.this, PatientDetailActivity.class, bundle);
        });
    }

    private void GetPatientList() {
        RequestUtils.create(ApiService.class)
                .getPatientList(SpfUtils.getSpfSaveInt(DocConstant.userId))
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<List<PatientListBean>>(new NetDialog().init(this).setDialogMsg(R.string.loadProgress)) {
                    @Override
                    protected void onSuccess(List<PatientListBean> t) {
                        mAdapter.setNewData(t);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }
}
