package com.hjy.wisdommedicaldoctor.adapter;

import android.support.annotation.Nullable;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fy.baselibrary.utils.imgload.ImgLoadUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.EvaluationDoctorBean;

import java.util.List;

/**
 * Created by Stefan on 2018/6/29.
 * 医生详情患者评价
 */
public class EvaluationDoctorInfoAdapter extends BaseQuickAdapter<EvaluationDoctorBean.RowsBean, BaseViewHolder> {

    private RatingBar ratingBar;

    public EvaluationDoctorInfoAdapter(@Nullable List<EvaluationDoctorBean.RowsBean> data) {
        super(R.layout.evaluate_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluationDoctorBean.RowsBean item) {
//        helper.setText(R.id.tv_time, item.getGmtCreate())
//                .setText(R.id.tv_name, item.getUsername())
//                .setText(R.id.tv_context, item.getEvaluationContent());
//        ImgLoadUtils.loadCircularBead(mContext, ApiService.BASE_PIC_URL + item.getPhotoUrl(), helper.getView(R.id.iv_pic), 50);
//        ratingBar = helper.getView(R.id.RatingBar);
//        setRatingBar();
//        ratingBar.setStar((float) item.getSorce());
    }

//    private void setRatingBar() {
//        ratingBar.setStarEmptyDrawable(mContext.getResources().getDrawable(R.drawable.star_light));
//        ratingBar.setStarFillDrawable(mContext.getResources().getDrawable(R.drawable.star_fill));
//        ratingBar.setStarHalfDrawable(mContext.getResources().getDrawable(R.drawable.star_half));
//    }
}
