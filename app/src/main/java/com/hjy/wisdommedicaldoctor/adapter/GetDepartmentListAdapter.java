package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.DepartmentBean;
import com.hjy.wisdommedicaldoctor.widget.BaseWheelView;

import java.util.Arrays;
import java.util.List;


/**
 * Created by 初夏小溪 on 2018/9/13 0013.
 * 设置-个人资料 科室
 */
public class GetDepartmentListAdapter extends BaseQuickAdapter<DepartmentBean, BaseViewHolder> {


    public GetDepartmentListAdapter(@Nullable List<DepartmentBean> data) {
        super(R.layout.department_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentBean item) {
        helper.setText(R.id.tv_title, item.getDepartmentName());
        helper.addOnClickListener(R.id.tv_title);
    }
}
