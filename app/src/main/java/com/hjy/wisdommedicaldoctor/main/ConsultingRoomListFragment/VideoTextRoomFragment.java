package com.hjy.wisdommedicaldoctor.main.ConsultingRoomListFragment;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fy.baselibrary.aop.annotation.NeedPermission;
import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.EntireOrderRoomVideoListAdapter;
import com.hjy.wisdommedicaldoctor.bean.RoomListBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;
import com.hjy.wisdommedicaldoctor.hx.single.CallManager;
import com.hjy.wisdommedicaldoctor.hx.single.VideoCallActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 診室视频咨询
 * Created by 初夏小溪 on 2018/7/26 0026.
 */
public class VideoTextRoomFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener{

    @BindView(R.id.rv_entire)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int mPageNo = 1;
    private EntireOrderRoomVideoListAdapter mAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_entire_order;
    }

    @Override
    protected void baseInit() {
        super.baseInit();
        initRefresh();
        initRv();
        mRefreshLayout.autoRefresh();
    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new EntireOrderRoomVideoListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    /**
     * 视频 serviceId ：2
     */
    private void getListConsult(int mPageNo) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        map.put("serviceId", 2);
        map.put("pageSize", 10);
        map.put("pageNumber", mPageNo);
        RequestUtils.create(ApiService.class)
                .getRoomList(map)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(new NetCallBack<RoomListBean>() {
                    @Override
                    protected void onSuccess(RoomListBean t) {
                        if (mRefreshLayout.isRefreshing()) {
                            mAdapter.setNewData(t.getRows());
                            mRefreshLayout.finishRefresh();
                        } else if (mRefreshLayout.isLoading()) {
                            mAdapter.getData().addAll(t.getRows());
                            mRefreshLayout.finishLoadMore();
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mAdapter.setNewData(t.getRows());
                        }

                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.bt_commit:
                RoomListBean.RowsBean bean = mAdapter.getData().get(position);
                getIsComeIn(bean.getId(), bean.getUsername());
                break;
        }
    }

    @NeedPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    public void callVideo(AppCompatActivity act, String toChatUsername) {
        Intent intent = new Intent(act, VideoCallActivity.class);
        CallManager.getInstance().setChatId(toChatUsername);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
        act.startActivity(intent);
    }

    /**
     * 分页加载数据
     */
    private void initRefresh() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPageNo += 1;
//                if (mAdapter.getData().size() < 3) {
//                    mRefreshLayout.setEnableLoadMore(false);
//                }else {
                    getListConsult(mPageNo);
//                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPageNo = 1;
                getListConsult(mPageNo);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListConsult(mPageNo);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    /**
     * 判断患者是否进入诊室
     * @param id
     * @param hxid
     */
    public void getIsComeIn(int id, String hxid) {
        RequestUtils.create(ApiService.class)
                .isComeIn(id)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(new NetCallBack<Boolean>(new NetDialog().init((AppCompatActivity) getActivity()).setDialogMsg(R.string.loadingGet)) {
                    @Override
                    protected void onSuccess(Boolean isComeIn) {
                        if (isComeIn){
                            callVideo(mContext, hxid);
                        } else {
                            T.showShort("对方没有进入诊室");
                        }
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }
}
