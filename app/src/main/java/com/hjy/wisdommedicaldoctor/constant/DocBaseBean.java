package com.hjy.wisdommedicaldoctor.constant;

import com.fy.baselibrary.retrofit.BaseBean;

/**
 * Created by 初夏小溪 on 2018/9/10 0010.
 */
public class DocBaseBean<T> implements BaseBean<T> {

    /**
     * msg : 操作成功！
     * code : 0
     * rows : {}
     */

    private String msg = "";
    private int code = -1;
    private T rows;
    @Override
    public boolean isSuccess() {
        return code == 0 ? true : false;
    }
    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getData() {
        return rows;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DocBaseBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", rows=" + rows +
                '}';
    }
}
