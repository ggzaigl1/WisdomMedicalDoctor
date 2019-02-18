package com.hjy.wisdommedicaldoctor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.EvaluationDoctorInfoAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 初夏小溪 on 2018/8/9 0009.
 * 患者详情 评价列表（分页）
 */

public class EvaluationDoctorActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.tv_commentCount)
    TextView tv_commentCount;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.Ll_evaluation)
    LinearLayout mLinearLayout;
    private EvaluationDoctorInfoAdapter mAdapter;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_evaluation_doctor;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        initView();
        getListByDocIdToApp();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new EvaluationDoctorInfoAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_advice_error, mLinearLayout, false);
        mAdapter.setEmptyView(view);
    }

//    private void initView() {
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
//        layoutManager.setFlexWrap(FlexWrap.WRAP);
//        layoutManager.setFlexDirection(FlexDirection.ROW);
//        layoutManager.setAlignItems(AlignItems.STRETCH);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setHasFixedSize(true);
//        mAdapter = new EvaluationDoctorInfoAdapter(new ArrayList<>());
//        mRecyclerView.setAdapter(mAdapter);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_advice_error, null, false);
//        mAdapter.setEmptyView(view);
//    }

    /**
     * 评价列表
     */
    private void getListByDocIdToApp() {
        ArrayMap<String, Object> prams = new ArrayMap<>();
        prams.put("pageNumber", 1);
        prams.put("pageSize ", 20);
//        prams.put("docNo", SpfUtils.getSpfSaveStr(DocConstant.docNo));
//        RxHttpUtils.createApi(ApiService.class)
//                .listByDocIdToApp(prams)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CommonObserver<BaseBean<EvaluationDoctorBean>>() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    protected void onSuccess(BaseBean<EvaluationDoctorBean> doctorInfoBeanBaseBean) {
//                        mAdapter.setNewData(doctorInfoBeanBaseBean.getRows().getRows());
//                        tv_commentCount.setText(doctorInfoBeanBaseBean.getRows().getTotal() + "条评价");
//                    }
//
//                    @Override
//                    protected void onError(String errorMsg) {
//
//                    }
//                });
    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}