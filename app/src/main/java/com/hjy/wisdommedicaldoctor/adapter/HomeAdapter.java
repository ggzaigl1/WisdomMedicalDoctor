package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.TodayBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;

import java.util.List;

/**
 * Created by 初夏小溪 on 2018/7/25 0025.
 * adapter
 */

public class HomeAdapter extends BaseQuickAdapter<TodayBean.ListConsultRecordBean, BaseViewHolder> {

    public HomeAdapter(@Nullable List<TodayBean.ListConsultRecordBean> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayBean.ListConsultRecordBean item) {
        int serviceId = item.getServiceId();
//        TextView tv_appointment_time = helper.getView(R.id.tv_appointment_time);
        switch (serviceId) {
            case 1:
                helper.setText(R.id.tv_title, "图文咨询");
//                tv_appointment_time.setVisibility(View.INVISIBLE);
                break;
            case 2:
                helper.setText(R.id.tv_title, "视频咨询");
//                tv_appointment_time.setVisibility(View.VISIBLE);
                break;
            case 3:
                helper.setText(R.id.tv_title, "语音咨询");
//                tv_appointment_time.setVisibility(View.VISIBLE);
                break;
        }
        if (!TextUtils.isEmpty(item.getReserveTime())) {
            helper.setText(R.id.tv_appointment_time, item.getReserveDate() + " " + DocConstant.time[Integer.parseInt(item.getReserveTime())]);
//            tv_appointment_time.setText(item.getReserveDate() + " " + DocConstant.time[Integer.parseInt(item.getReserveTime())]);
        }
    }
}
