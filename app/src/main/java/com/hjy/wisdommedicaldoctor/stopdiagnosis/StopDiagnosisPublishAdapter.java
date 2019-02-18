package com.hjy.wisdommedicaldoctor.stopdiagnosis;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.rv.adapter.MultiCommonAdapter;
import com.fy.baselibrary.rv.adapter.MultiTypeSupport;
import com.fy.baselibrary.utils.TimeUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.SchedulingBean;
import com.hjy.wisdommedicaldoctor.utils.SelectorUtils;

import java.util.Date;
import java.util.List;

/**
 * 停诊发布 列表adapter
 * Created by fangs on 2018/8/15 17:12.
 */
public class StopDiagnosisPublishAdapter extends MultiCommonAdapter<SchedulingBean> {

    public static final int funType = 1001;//标题
    public static final int timeTitle = 1002;//标题

    public StopDiagnosisPublishAdapter(Context context, List<SchedulingBean> datas) {
        super(context, datas, new MultiTypeSupport<SchedulingBean>(){
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == funType){
                    return R.layout.scheduling_new_item_title;
                } else if (itemType == timeTitle){
                    return R.layout.scheduling_new_item_time_title;
                } else {
                    return R.layout.scheduling_new_item_content;
                }
            }

            @Override
            public int getItemViewType(int position, SchedulingBean t) {
                if (position > 0 && position < 8) {
                    return funType;
                } else if(position % 8 == 0){
                    return timeTitle;
                } else {
                    return -1;
                }
            }
        });
    }

    @Override
    public void convert(ViewHolder holder, SchedulingBean schedulingBean, int position) {
        if (position > 0 && position < 8) {
            TextView tvTitle = holder.getView(R.id.tvTitle);
            Date date = schedulingBean.getDate();
            if (TimeUtils.isSameDay(System.currentTimeMillis(), date.getTime())) {
                Spannable sp = new SpannableString("今\n天");
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.txtSecondColor)),
                        0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvTitle.setText(sp);
            } else {
                String day = TimeUtils.Data2String(date, "dd");
                String week = TimeUtils.Data2String(date, "E").substring(1);
                String text = week + "\n" + day;

                Spannable sp = new SpannableString(text);
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.txtLight)),
                        0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.txtSecondColor)),
                        1, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvTitle.setText(sp);
            }
        } else if (position % 8 == 0) {
            if (position == 0) holder.setText(R.id.tvTimer, "");
            else holder.setText(R.id.tvTimer, schedulingBean.getTime());
        } else {
            TextView tvContent = holder.getView(R.id.tvContent);
            int makeStatus = schedulingBean.getMakeStatus();
            if (makeStatus == 0) {
                tvContent.setBackground(null);
            } else {
                Drawable selector = SelectorUtils.getSelector(R.drawable.svg_make, R.drawable.svg_rectangle, 1);
                tvContent.setBackground(selector);

                boolean isSelect = makeStatus > 1;
                tvContent.setSelected(isSelect);
                //点击事件
                tvContent.setOnClickListener(v -> {
                    int makeStatusClick = schedulingBean.getMakeStatus();
                    boolean isSelectClick = makeStatusClick > 1;

                    mDatas.get(position).setMakeStatus(isSelectClick ? 1 : 2);//不管在不在屏幕里 都需要改变数据

                    setItemChecked(position, isSelectClick);
                    tvContent.setSelected(isSelectClick);
                    notifyItemChanged(position);
                });
            }

        }
    }

}
