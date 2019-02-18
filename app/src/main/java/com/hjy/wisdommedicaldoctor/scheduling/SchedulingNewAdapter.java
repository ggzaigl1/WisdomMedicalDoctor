package com.hjy.wisdommedicaldoctor.scheduling;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.rv.adapter.MultiCommonAdapter;
import com.fy.baselibrary.rv.adapter.MultiTypeSupport;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.MakeBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;

import java.util.List;

/**
 * 排班适配器
 * Created by fangs on 2018/7/3.
 */
public class SchedulingNewAdapter extends MultiCommonAdapter<MakeBean> {

    boolean isClick = true;//是否可以点击

    public SchedulingNewAdapter(Context context, List<MakeBean> datas) {
        super(context, datas, new MultiTypeSupport<MakeBean>(){
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == Constant.StickyType){//第一行
                    return R.layout.scheduling_new_item_title;
                } else if (itemType == DocConstant.timeTitle){//第一列
                    return R.layout.scheduling_new_item_time_title;
                } else {
                    return R.layout.scheduling_new_item_content;
                }
            }

            @Override
            public int getItemViewType(int position, MakeBean t) {
                if (position / 8 == 0){
                    return Constant.StickyType;
                } else if (position % 8 == 0){
                    return DocConstant.timeTitle;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public void convert(ViewHolder holder, MakeBean makeBean, int position) {
        if (position / 8 == 0){
            TextView tvTitle = holder.getView(R.id.tvTitle);

            String date = makeBean.getDate();
            if (TextUtils.isEmpty(date)) return;

            long timeLong = TimeUtils.timeString2long(date, "yyyy-MM-dd");
            if (TimeUtils.isSameDay(System.currentTimeMillis(), timeLong)) {
                Spannable sp = new SpannableString("今\n天");
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.txtSecondColor)),
                        0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvTitle.setText(sp);
            } else {
                String day = date.substring(8, 10);
                String week = TimeUtils.Long2DataString(timeLong, "E").substring(1);
                String text = week + "\n" + day;

                Spannable sp = new SpannableString(text);
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.txtLight)),
                        0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.txtSecondColor)),
                        1, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvTitle.setText(sp);
            }
        } else if (position % 8 == 0) {
            holder.setText(R.id.tvTimer, DocConstant.time[makeBean.getTime()]);
        } else {
            TextView tvContent = holder.getView(R.id.tvContent);
            int makeStatus = makeBean.getStatus();
            if (makeStatus == 0) {
                tvContent.setBackground(null);
            } else {
                Drawable selector = getSelector(R.drawable.svg_make, 1, R.drawable.svg_rectangle);
                tvContent.setBackground(selector);

                boolean isSelect = makeStatus > 1;
                tvContent.setSelected(isSelect);

                setItemChecked(position, isSelect);
                //点击事件
                if (isClick){
                    tvContent.setOnClickListener(v -> {
                        int makeStatusClick = makeBean.getStatus();
                        boolean isSelectClick = makeStatusClick > 1;

                        mDatas.get(position).setStatus(isSelectClick ? 1 : 2);//不管在不在屏幕里 都需要改变数据

                        setItemChecked(position, !isSelectClick);
                        tvContent.setSelected(isSelectClick);
                        notifyItemChanged(position);
                    });
                }
            }
        }
    }


    public void setClick(boolean click) {
        isClick = click;
    }

    public static StateListDrawable getStateListDrawable(Drawable[] drawables, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int i = 0; i < drawables.length; i++) {
            int[] state = states[i];
            Drawable drawable = drawables[i];
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }

    /**
     * 获取 指定 ID的 drawable，生成的 选择器
     * @param draId1
     * @param draId2
     * @return
     */
    public static Drawable getSelector(@DrawableRes int draId1, int drawableType, @DrawableRes int draId2){

        int[][] states = new int[2][];
//        states[0] = new int[]{android.R.attr.state_pressed};
        states[0] = new int[]{android.R.attr.state_selected};
//        states[2] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};

        Drawable[] drawables = new Drawable[2];
        drawables[0] = TintUtils.getDrawable(draId1, drawableType);
        drawables[1] = TintUtils.getDrawable(draId2, drawableType);

        StateListDrawable stateListDrawable = getStateListDrawable(drawables, states);

        return stateListDrawable;
    }
}
