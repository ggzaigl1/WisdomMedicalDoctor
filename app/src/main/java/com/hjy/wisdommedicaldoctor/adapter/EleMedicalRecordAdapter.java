package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjy.wisdommedicaldoctor.R;

import java.util.List;

/**
 * Created by Stefan on 2018/7/27.
 */
public class EleMedicalRecordAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public EleMedicalRecordAdapter(@Nullable List<Object> data) {
        super(R.layout.item_ele_medical_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
