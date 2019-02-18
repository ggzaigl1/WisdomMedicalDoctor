package com.hjy.wisdommedicaldoctor.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.HomeAdapter;
import com.hjy.wisdommedicaldoctor.bean.TodayBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;
import com.hjy.wisdommedicaldoctor.schedule.ScheduleActivity;
import com.hjy.wisdommedicaldoctor.ui.myorder.MyOrderActivity;
import com.hjy.wisdommedicaldoctor.ui.patient_manage.PatientManageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by QKun on 2018/7/24 11:38.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.ry_data)
    RecyclerView mRecyclerView;
    HomeAdapter mAdapter;
    private static final String TAG = "home";

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, params);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void baseInit() {
        setDummyData();
        initRv();
    }

    private void initRv() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_headview, (ViewGroup) mRecyclerView.getParent(), false);
        TextView tvToBeTreated = view.findViewById(R.id.tvToBeTreated);
        tvToBeTreated.setOnClickListener(this);
        Drawable svg_right_arrow = TintUtils.getDrawable(R.drawable.svg_arrow_right, 1);
        TintUtils.setTxtIconLocal(tvToBeTreated, svg_right_arrow, 2);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new HomeAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(view);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("main", true);
                bundle.putInt("serviceId", mAdapter.getData().get(position).getServiceId());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                listterner.process(mAdapter.getData().get(position).getServiceId());

            }
        });
    }

//    private FragmentInteraction listterner;
//
//    public interface FragmentInteraction {
//        void process(int str);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof FragmentInteraction) {
//            listterner = (FragmentInteraction)context; // 2.2 获取到宿主activity并赋值
//        } else{
//            throw new IllegalArgumentException("activity must implements FragmentInteraction");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        listterner =null;
//    }

    private void setDummyData() {
        int docId = SpfUtils.getSpfSaveInt(DocConstant.userId);
        RequestUtils.create(ApiService.class)
                .todayTODO(docId)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(new NetCallBack<TodayBean>(new NetDialog().init((AppCompatActivity) getActivity()).setDialogMsg(R.string.loading_get)) {
                    @Override
                    protected void onSuccess(TodayBean t) {
                        List<TodayBean.ListConsultRecordBean> listConsultRecord = t.getListConsultRecord();
                        mAdapter.setNewData(listConsultRecord);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    @ClickFilter()
    @OnClick({R.id.Ll_order, R.id.Ll_patient})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Ll_order:
                JumpUtils.jump(mContext, MyOrderActivity.class, null);
                break;
            case R.id.Ll_patient:
                JumpUtils.jump(mContext, PatientManageActivity.class, null);
                break;
            case R.id.tvToBeTreated:
                JumpUtils.jump(this, ScheduleActivity.class, null);
                break;
        }
    }
}
