package com.hjy.wisdommedicaldoctor.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.RoomListBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.utils.SelectorUtils;

import java.util.List;

/**
 * Created by 初夏小溪 on 2018/7/26 0026.
 * 诊室 语音 adapter
 */

public class EntireOrderVoiceRoomListAdapter extends BaseQuickAdapter<RoomListBean.RowsBean, BaseViewHolder> {

    public EntireOrderVoiceRoomListAdapter(@Nullable List<RoomListBean.RowsBean> data) {
        super(R.layout.item_consulting_image_room, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomListBean.RowsBean item) {
        helper.setText(R.id.tv_name, "就诊人:" + item.getMemberName());
        helper.setText(R.id.tv_time, "就诊时间:" + DocConstant.time[Integer.parseInt(item.getReserveTime())]);

        String date = item.getReserveDate().trim();
        String time = DocConstant.time[Integer.parseInt(item.getReserveTime())];
        long startTime = TimeUtils.timeString2long(date + " " + time.substring(0, 5), "yyyy-MM-dd HH:mm");
        long endTime = TimeUtils.timeString2long(date + " " + time.substring(6, 11), "yyyy-MM-dd HH:mm");

        Button bt_commit = helper.getView(R.id.bt_commit);
        if (startTime <= System.currentTimeMillis() && System.currentTimeMillis() <= endTime) {
            helper.addOnClickListener(R.id.bt_commit);
            Drawable drawable = TintUtils.getDrawable(R.drawable.shape_rounded_rect_small, 0);
            bt_commit.setBackground(SelectorUtils.getPressed(drawable, R.color.pressedColor, R.color.mainColor));
        } else {
            bt_commit.setBackground(TintUtils.getTintDrawable(R.drawable.shape_rounded_rect_small, 0, R.color.noClickBtn));
        }
    }
}
