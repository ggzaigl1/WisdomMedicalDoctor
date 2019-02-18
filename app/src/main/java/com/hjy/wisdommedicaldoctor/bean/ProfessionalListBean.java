package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;

/**
 * Created by QKun on 2018/9/19.
 */
public class ProfessionalListBean implements Serializable {

    /**
     * id : 4
     * titleName : 主任医师
     * titleInfo : null
     * gmtCreate : null
     * gmtModified : null
     */

    private int id;
    private String titleName;
    private Object titleInfo;
    private Object gmtCreate;
    private Object gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Object getTitleInfo() {
        return titleInfo;
    }

    public void setTitleInfo(Object titleInfo) {
        this.titleInfo = titleInfo;
    }

    public Object getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Object gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Object getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Object gmtModified) {
        this.gmtModified = gmtModified;
    }
}
