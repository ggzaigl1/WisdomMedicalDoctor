package com.lzy.imagepicker.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by QKun on 2018/4/17.
 */

public class ImageItem implements Parcelable,Serializable{
    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间

    private boolean isAdded;   //是否额外添加了最后一个图片

    public ImageItem(boolean isAdded) {
        this.isAdded = isAdded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.mimeType);
        dest.writeLong(this.addTime);
    }

    public ImageItem() {
    }

    protected ImageItem(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mimeType = in.readString();
        this.addTime = in.readLong();
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
