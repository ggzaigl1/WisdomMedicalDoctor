package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.ProfessionalListBean;

import java.util.List;

/**
 * Created by QKun on 2018/9/19.
 */
public class ProfessionalAdapter extends BaseQuickAdapter<ProfessionalListBean, BaseViewHolder> {

    public ProfessionalAdapter(@Nullable List<ProfessionalListBean> data) {
        super(R.layout.professional_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProfessionalListBean item) {
        helper.setText(R.id.tv_title, item.getTitleName());
    }
}
