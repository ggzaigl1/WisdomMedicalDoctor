package com.hjy.wisdommedicaldoctor.main.ConsultingRoomListFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fy.baselibrary.base.BaseFragment;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.ImageTextRoomListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 初夏小溪 on 2018/7/26 0026.
 * 診室线下咨询
 */

public class OfflineTextRoomFragment extends BaseFragment {

    @BindView(R.id.rv_entire)
    RecyclerView mRecyclerView;
    private ImageTextRoomListAdapter mAdapter;

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

    private List<String> mStrings;

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ImageTextRoomListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setDummyData() {
        mStrings = new ArrayList();
        for (int i = 0; i < 5; i++) {
            mStrings.add("");
        }
    }
}
