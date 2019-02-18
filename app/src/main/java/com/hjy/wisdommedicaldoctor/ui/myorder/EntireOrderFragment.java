package com.hjy.wisdommedicaldoctor.ui.myorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.EntireOrderListAdapter;
import com.hjy.wisdommedicaldoctor.bean.OrderBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 初夏小溪 on 2018/7/26 0026.
 * 全部订单
 */

public class EntireOrderFragment extends BaseFragment {

    @BindView(R.id.rv_entire)
    RecyclerView mRecyclerView;
    private EntireOrderListAdapter mAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_entire_order;
    }

    @Override
    protected void baseInit() {
        super.baseInit();
        setDummyData();
        initRv();
    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new EntireOrderListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean orderBean = mAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orderBean);
                JumpUtils.jump(mContext, OrderParticularsActivity.class, bundle);
            }
        });
    }

    /**
     * 我的订单列表 (全部 serviceId=0)
     */
    private void setDummyData() {
        RequestUtils.create(ApiService.class)
                .listOrder(SpfUtils.getSpfSaveInt(DocConstant.userId), 0)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(new NetCallBack<List<OrderBean>>(new NetDialog().init((AppCompatActivity) getActivity()).setDialogMsg(R.string.loadProgress)) {
                    @Override
                    protected void onSuccess(List<OrderBean> t) {
                        mAdapter.setNewData(t);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }
}
