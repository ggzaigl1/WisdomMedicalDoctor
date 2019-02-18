package com.hjy.wisdommedicaldoctor.bean;

/**
 * 日程实体类
 * Created by fangs on 2018/7/27 17:36.
 */
public class ScheduleBean {

    private String scheduleDesc;

    public ScheduleBean(String scheduleDesc) {
        this.scheduleDesc = scheduleDesc;
    }

    public String getScheduleDesc() {
        return scheduleDesc;
    }

    public void setScheduleDesc(String scheduleDesc) {
        this.scheduleDesc = scheduleDesc;
    }
}
