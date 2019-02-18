package com.hjy.wisdommedicaldoctor.bean;

import com.fy.baselibrary.rv.divider.sticky.StickyBean;

import java.util.Date;

/**
 * 预约时间 实体类
 * Created by fangs on 2018/7/3.
 */
public class SchedulingBean extends StickyBean {

    private Date date;

    private int timeId;
    private String time = "";

    private int makeStatus = 0;//预约状态 0：不能预约；1：可预约；2：预约成功

    public SchedulingBean() {
    }

    public SchedulingBean(Date date) {
        this.date = date;
    }

    public SchedulingBean(Date date, int itemType) {
        this.date = date;
        setItemType(itemType);
    }

    public SchedulingBean(String time, int itemType) {
        this.time = time;
        setItemType(itemType);
    }

    public SchedulingBean(String time) {
        this.time = time;
    }

    public SchedulingBean(int makeStatus) {
        this.makeStatus = makeStatus;
    }

    public SchedulingBean(int timeId, String time, Date date, int makeStatus) {
        this.timeId = timeId;
        this.time = time;
        this.date = date;
        this.makeStatus = makeStatus;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(int makeStatus) {
        this.makeStatus = makeStatus;
    }

    @Override
    public String toString() {
        return "SchedulingBean{" +
                "date=" + date +
                ", timeId=" + timeId +
                ", time='" + time + '\'' +
                ", makeStatus=" + makeStatus +
                '}';
    }
}
