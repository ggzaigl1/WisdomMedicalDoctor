package com.hjy.wisdommedicaldoctor.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fy.baselibrary.rv.divider.sticky.StickyBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * 医生可预约时间段 实体类
 * Created by fangs on 2018/7/3.
 */
public class MakeBean extends StickyBean implements Parcelable {

    /**
     * date : 2018-09-01
     * time : 1
     * status : 0
     */

    private String date = "";//日期
    private int time;   //时间段
    private int status; //0：空白 1：可选 2：自己选过的

    public MakeBean() {}

    public MakeBean(int time, int itemType) {
        this.time = time;
        setItemType(itemType);
    }

    public MakeBean(String date, int itemType) {
        this.date = date;
        setItemType(itemType);
    }

    public MakeBean(int time, String date, int status) {
        this.date = date;
        this.time = time;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MakeBean{" +
                "date='" + date + '\'' +
                ", time=" + time +
                ", status=" + status +
                '}';
    }


    protected MakeBean(Parcel in) {
        date = in.readString();
        time = in.readInt();
        status = in.readInt();
    }

    public static final Creator<MakeBean> CREATOR = new Creator<MakeBean>() {
        @Override
        public MakeBean createFromParcel(Parcel in) {
            return new MakeBean(in);
        }

        @Override
        public MakeBean[] newArray(int size) {
            return new MakeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(time);
        dest.writeInt(status);
    }
}
