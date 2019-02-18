package com.hjy.wisdommedicaldoctor.gratuitous;

/**
 * 义诊排班 列表实体类
 * Created by fangs on 2018/8/29 15:15.
 */
public class GratuitousSchedulingBean {

    private String content = "";

    private int makeStatus = 0;//预约状态 （0：不能预约；1：可预约；2：预约成功）

    public GratuitousSchedulingBean() {}

    public GratuitousSchedulingBean(String content) {
        this.content = content;
    }

    public GratuitousSchedulingBean(String content, int makeStatus) {
        this.content = content;
        this.makeStatus = makeStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(int makeStatus) {
        this.makeStatus = makeStatus;
    }
}
