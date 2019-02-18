package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.HospitalListBean;

import java.util.List;


/**
 * Created by 初夏小溪 on 2018/9/13 0013.
 * 设置-个人资料 醫院
 */
public class GetHospitalListAdapter extends BaseQuickAdapter<HospitalListBean, BaseViewHolder> {


    public GetHospitalListAdapter(@Nullable List<HospitalListBean> data) {
        super(R.layout.department_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HospitalListBean item) {
        helper.setText(R.id.tv_title, item.getHosName());
        helper.addOnClickListener(R.id.tv_title);
    }
}
