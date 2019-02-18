package com.hjy.wisdommedicaldoctor.main.ConsultingRoomListFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.ImageTextRoomListAdapter;
import com.hjy.wisdommedicaldoctor.bean.RoomListBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.hx.chat.ChatActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 初夏小溪 on 2018/7/26 0026.
 * 診室图片咨询
 */

public class ImageTextRoomFragment extends BaseFragment {

    @BindView(R.id.rv_entire)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int mPageNo = 1;
    private ImageTextRoomListAdapter mAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_entire_order;
    }

    @Override
    protected void baseInit() {
        super.baseInit();
        initRefresh();
        initRv();

    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ImageTextRoomListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.Ll_Chat:
                    RoomListBean.RowsBean roomListBean = mAdapter.getData().get(position);
                    EMClient.getInstance().groupManager().asyncJoinGroup("61303032971265", new EMCallBack(){
                        @Override
                        public void onSuccess() {
                            Bundle bundle = new Bundle();
                            bundle.putString(EaseConstant.EXTRA_USER_ID, roomListBean.getChatGroupId());
                            bundle.putString(EaseConstant.EXTRA_USER_NAME, roomListBean.getUsername());
                            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);

                            JumpUtils.jump(mContext, ChatActivity.class, bundle);
                        }

                        @Override
                        public void onError(int i, String s) {
                            Bundle bundle = new Bundle();
                            bundle.putString(EaseConstant.EXTRA_USER_ID, roomListBean.getChatGroupId());
                            bundle.putString(EaseConstant.EXTRA_USER_NAME, roomListBean.getUsername());
                            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);

                            JumpUtils.jump(mContext, ChatActivity.class, bundle);
                        }

                        @Override
                        public void onProgress(int i, String s) {}
                    });
//                    todo 临时注释
//                    Bundle bundle = new Bundle();
//                    bundle.putString(EaseConstant.EXTRA_USER_ID, mAdapter.getData().get(position).getUsername());
//                    bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
//                    JumpUtils.jump(mContext, ChatActivity.class, bundle);
                    break;
            }
        });
    }

    /**
     * 图文 serviceId ：1
     */
    private void getListConsult(int mPageNo) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        map.put("serviceId", 1);
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
                getListConsult(mPageNo);
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
}
