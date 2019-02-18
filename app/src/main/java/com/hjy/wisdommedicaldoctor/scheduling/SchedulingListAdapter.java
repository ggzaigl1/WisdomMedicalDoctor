package com.hjy.wisdommedicaldoctor.scheduling;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.SchedulingListBean;
import com.hjy.wisdommedicaldoctor.utils.SelectorUtils;

import java.util.List;

/**
 * 排班列表 adapter
 * Created by fangs on 2018/7/31 15:48.
 */
public class SchedulingListAdapter extends BaseQuickAdapter<SchedulingListBean.RowsBean, BaseViewHolder> {


    public SchedulingListAdapter(@Nullable List<SchedulingListBean.RowsBean> data) {
        super(R.layout.scheduling_recycle_item_center, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchedulingListBean.RowsBean item) {
        helper.setText(R.id.tvDate, item.getStartDate().trim().substring(0, 10) + "至" + item.getEndDate().trim().substring(0, 10));
        int status = item.getStatus();
        TextView tvState = helper.getView(R.id.tvState);
        switch (status) {
            case 1:
                Drawable drawable = TintUtils.getDrawable(R.drawable.shape_rounded_rect_small, 0);
                tvState.setBackground(SelectorUtils.getPressed(drawable, R.color.pressedColor, R.color.mainColor));
                tvState.setText("启用中");
                break;
            case 2:
                tvState.setText("停用中");
                tvState.setBackground(TintUtils.getTintDrawable(R.drawable.shape_rounded_rect_small, 0, R.color.noClickBtn));
                break;
        }
    }



}
