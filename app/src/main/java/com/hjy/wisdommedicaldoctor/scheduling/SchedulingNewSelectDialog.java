package com.hjy.wisdommedicaldoctor.scheduling;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.haibin.time.RadialPickerLayout;
import com.haibin.time.TimePickerDialog;
import com.hjy.wisdommedicaldoctor.R;

/**
 * 选择时间
 * Created by fangs on 2018/8/7 11:01.
 */
public class SchedulingNewSelectDialog extends CommonDialog implements View.OnClickListener, CalendarView.OnDateSelectedListener, CalendarView.OnYearChangeListener, TimePickerDialog.OnTimeSetListener{

    private Calendar calendar;
    private String time = "00:00";
    private long selectTimeMillis;

    TextView tvDateTitle;
    TextView tvSetTime;

    private OnDataListener dataListener;

    @Override
    protected int initLayoutId() {
        return R.layout.scheduling_new_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, CommonDialog dialog) {
        Bundle bundle = getArguments();
        assert bundle != null;

        CalendarView calendarView = holder.getView(R.id.calendarView);
        calendarView.setOnDateSelectedListener(this);
        calendarView.setOnYearChangeListener(this);

        tvDateTitle = holder.getView(R.id.tvDateTitle);
        tvDateTitle.setText(calendarView.getCurYear() + "年" + calendarView.getCurMonth() + "月");

        tvSetTime = holder.getView(R.id.tvSetTime);
        tvSetTime.setText(bundle.getString("SetTimeTag", ""));
        tvSetTime.setOnClickListener(this);
        TintUtils.setTxtIconLocal(tvSetTime, TintUtils.getDrawable(R.drawable.svg_clock, 1), 0);

        holder.setOnClickListener(R.id.cancel, this);
        holder.setOnClickListener(R.id.ok, this);

        setHide(true);
        setDimAmount(0.8f);
    }

    public void setDataListener(OnDataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void onYearChange(int year) {
        T.showLong(year + "年");
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        this.calendar = calendar;
        tvDateTitle.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSetTime:

                java.util.Calendar now = java.util.Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        this,
                        now.get(java.util.Calendar.HOUR_OF_DAY),
                        now.get(java.util.Calendar.MINUTE),
                        true);

                tpd.show(getFragmentManager());
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.ok:
                time = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + " " + time;
                selectTimeMillis = TimeUtils.timeString2long(time, "yyyy-MM-dd HH:mm");
                if (null != dataListener)dataListener.onSuccess(selectTimeMillis);
                dismiss();
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        time = hourString + ":" + minuteString;
        tvSetTime.setText(time);
    }

    /**
     * 定义接口，用于dialog 和 activity通信
     */
    public interface OnDataListener{
        /**
         * 选中的时间戳
         * @param selectTimeMillis
         */
        void onSuccess(long selectTimeMillis);
    }
}
