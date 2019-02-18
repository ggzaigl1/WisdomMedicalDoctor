package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;

/**
 * Created by QKun on 2018/9/13.
 */
public class DepartmentBean implements Serializable {

    /**
     * departmentName : 眼科
     * departmentNo : 008
     * id : 8
     */

    private String departmentName;
    private String departmentNo;
    private int id;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
