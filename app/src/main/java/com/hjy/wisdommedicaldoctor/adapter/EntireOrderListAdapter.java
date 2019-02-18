package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.OrderBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;

import java.util.List;

/**
 * Created by 初夏小溪 on 2018/7/26 0026.
 */

public class EntireOrderListAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {

    public EntireOrderListAdapter(@Nullable List<OrderBean> data) {
        super(R.layout.item_entire_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.tv_Name, item.getVisitMemberName());
        if (null != item.getUsername()) {
            helper.setText(R.id.tv_docName, item.getUsername());
        }

        if (!TextUtils.isEmpty(item.getReserveTime())) {
            helper.setText(R.id.tv_time, item.getReserveDate() + " " + DocConstant.time[Integer.parseInt(item.getReserveTime())]);
        }
        helper.setText(R.id.tv_diseaseName, item.getDiseaseName());

        LinearLayout Ll_time = helper.getView(R.id.Ll_time);
        int serviceId = item.getServiceId();
        switch (serviceId) {
            case 1:
                helper.setText(R.id.tv_type, "图文咨询");
                Ll_time.setVisibility(View.GONE);
                break;
            case 2:
                helper.setText(R.id.tv_type, "视频咨询");
                break;
            case 3:
                helper.setText(R.id.tv_type, "语音咨询");
                Ll_time.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        switch (item.getOrderStatus()) {
            case 0:
                helper.setText(R.id.tv_orderStatus, "已删除");
                break;
            case 1:
                helper.setText(R.id.tv_orderStatus, "已取消");
                break;
            case 2:
                helper.setText(R.id.tv_orderStatus, "未支付");
                break;
            case 3:
                helper.setText(R.id.tv_orderStatus, "已支付");
                break;
            case 4:
                helper.setText(R.id.tv_orderStatus, "已完成");
                break;
            case 5:
                helper.setText(R.id.tv_orderStatus, "已回复");
                break;
            case 6:
                helper.setText(R.id.tv_orderStatus, "未就诊");
                break;
            case 8:
                helper.setText(R.id.tv_orderStatus, "已完成");
                break;
            default:
                break;
        }
    }
}
