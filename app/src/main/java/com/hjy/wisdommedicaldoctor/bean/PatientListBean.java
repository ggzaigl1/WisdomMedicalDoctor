package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;

/**
 * Created by QKun on 2018/9/17.
 */
public class PatientListBean implements Serializable{

    /**
     * id : 25
     * cusId : 33
     * memberName : 王毅
     * idType : 0
     * idNumber : 420116856327845563
     * gender : 1
     * birthday : 1979-07-24
     * mobile : 18971622180
     * email : null
     * relation : 1
     * isMarried : null
     * isDefault : 0
     * province : 北京市
     * city : 北京市
     * district : 东城区
     * street : 北京
     * postcode : null
     * gmtCreate : 2018-07-24 11:53:40
     * gmtModified : 2018-07-27 16:38:02
     */

    private int id;
    private int cusId;
    private String memberName;
    private int idType;
    private String idNumber;
    private int gender;
    private String birthday;
    private String mobile;
    private String email;
    private int relation;
    private Object isMarried;
    private int isDefault;
    private String province;
    private String city;
    private String district;
    private String street;
    private Object postcode;
    private String gmtCreate;
    private String gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public Object getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(Object isMarried) {
        this.isMarried = isMarried;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Object getPostcode() {
        return postcode;
    }

    public void setPostcode(Object postcode) {
        this.postcode = postcode;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
