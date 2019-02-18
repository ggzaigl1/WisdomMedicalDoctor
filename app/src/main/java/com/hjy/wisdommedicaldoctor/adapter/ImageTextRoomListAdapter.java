package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.RoomListBean;

import java.util.List;

/**
 * Created by 初夏小溪 on 2018/9/4 0004.
 * 图文咨询
 */
public class ImageTextRoomListAdapter extends BaseQuickAdapter<RoomListBean.RowsBean, BaseViewHolder> {

    public ImageTextRoomListAdapter(@Nullable List<RoomListBean.RowsBean> data) {
        super(R.layout.item_text_image_room, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RoomListBean.RowsBean item) {
        helper.setText(R.id.tv_name,item.getMemberName());
        helper.setText(R.id.tv_context,item.getDiseaseName());
        helper.setText(R.id.tv_time,item.getReserveDate()+" " +item.getReserveTime());

        helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.red));
        helper.setText(R.id.tv_status,"咨询中...");
        helper.addOnClickListener(R.id.Ll_Chat);
    }
}
