package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fy.baselibrary.utils.TimeUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.PatientListBean;

import java.util.List;

/**
 * Created by Stefan on 2018/7/24.
 */
public class PatientManageAdapter extends BaseQuickAdapter<PatientListBean, BaseViewHolder> {
    public PatientManageAdapter(@Nullable List<PatientListBean> data) {
        super(R.layout.item_patient_manage, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientListBean item) {
        helper.setText(R.id.tv_docName, item.getMemberName());
        int gender = item.getGender();
        switch (gender) {
            case 0:
                helper.setText(R.id.sex, "女");
                break;
            case 1:
                helper.setText(R.id.sex, "男");
                break;

        }
        int age = TimeUtils.calculationAge(item.getBirthday(), "yyyy-MM-dd");
        helper.setText(R.id.tv_age, age + "岁");
        helper.setText(R.id.tv_phone, item.getMobile());


    }
}
