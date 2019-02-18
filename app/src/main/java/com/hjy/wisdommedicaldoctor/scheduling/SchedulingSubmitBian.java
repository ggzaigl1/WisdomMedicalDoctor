package com.hjy.wisdommedicaldoctor.scheduling;

/**
 * 新建排班 提交实体类
 * Created by fangs on 2018/9/14 14:46.
 */
public class SchedulingSubmitBian {

    /**
     * date : 2018-10-10
     * time : 09:00~09:30,15:30~16:00,16:30~17:00
     */

    private String date;
    private int time;

    public SchedulingSubmitBian(String date, int time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SchedulingSubmitBian{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
