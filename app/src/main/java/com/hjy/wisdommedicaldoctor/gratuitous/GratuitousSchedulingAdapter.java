package com.hjy.wisdommedicaldoctor.gratuitous;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.rv.adapter.RvCommonAdapter;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.utils.SelectorUtils;

import java.util.List;

/**
 * 义诊排班列表 adapter
 * Created by fangs on 2018/8/29 15:16.
 */
public class GratuitousSchedulingAdapter extends RvCommonAdapter<GratuitousSchedulingBean> {

    public GratuitousSchedulingAdapter(Context context, List<GratuitousSchedulingBean> datas) {
        super(context, R.layout.scheduling_new_item_content, datas);
    }

    @Override
    public void convert(ViewHolder holder, GratuitousSchedulingBean itemBean, int position) {
        TextView tvContent = holder.getView(R.id.tvContent);
        tvContent.setText(itemBean.getContent());

        int makeStatus = itemBean.getMakeStatus();
        if (makeStatus == 0){
            tvContent.setBackground(null);
        } else {
            Drawable drawNomal = TintUtils.getDrawable(R.drawable.svg_rectangle, 1);
            tvContent.setBackground(SelectorUtils.getSelector(drawNomal, R.color.haveColor, R.color.operableColor));

            boolean isSelect = makeStatus > 1;
            tvContent.setSelected(isSelect);

            //点击事件
            tvContent.setOnClickListener(v -> {
                int makeStatusClick = itemBean.getMakeStatus();
                boolean isSelectClick = makeStatusClick > 1;

                mDatas.get(position).setMakeStatus(isSelectClick ? 1 : 2);//不管在不在屏幕里 都需要改变数据

                setItemChecked(position, isSelectClick);
                tvContent.setSelected(isSelectClick);
                notifyItemChanged(position);
            });
        }

    }
}
