package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;

/**
 * Created by QKun on 2018/9/12.
 */
public class OrderBean implements Serializable{

    /**
     * id : 70
     * cusId : 25
     * username : null
     * docId : 2
     * serviceId : 3
     * visitMemberId : 47
     * diseaseName : 感冒
     * consultContent : 不舒服
     * image : /medicalRecords/20180904/a69313af83f14486af1c82fc9c79d9b9.jpeg
     * reserveDate : 2018-07-27
     * reserveTime : 09:30~10:00
     * orderNo : YY1809041020136353
     * orderStatus : 2
     * payType : null
     * isEvaluation : null
     * gmtCreate : 2018-09-04 10:21:48
     * gmtModified : null
     * visitMemberName : 张学友
     * gender : 0
     * birthday : 2006-08-14
     * age : 12
     * servicePrice : 300.00
     * appDocEntityVO : null
     */

    private int id;
    private int cusId;
    private String username;
    private int docId;
    private int serviceId;
    private int visitMemberId;
    private String diseaseName;
    private String consultContent;
    private String image;
    private String reserveDate;
    private String reserveTime = "";
    private String orderNo;
    private int orderStatus;
    private Object payType;
    private Object isEvaluation;
    private String gmtCreate;
    private Object gmtModified;
    private String visitMemberName;
    private String gender;
    private String birthday;
    private int age;
    private String servicePrice;
    private Object appDocEntityVO;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getVisitMemberId() {
        return visitMemberId;
    }

    public void setVisitMemberId(int visitMemberId) {
        this.visitMemberId = visitMemberId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getConsultContent() {
        return consultContent;
    }

    public void setConsultContent(String consultContent) {
        this.consultContent = consultContent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getIsEvaluation() {
        return isEvaluation;
    }

    public void setIsEvaluation(Object isEvaluation) {
        this.isEvaluation = isEvaluation;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Object getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Object gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getVisitMemberName() {
        return visitMemberName;
    }

    public void setVisitMemberName(String visitMemberName) {
        this.visitMemberName = visitMemberName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Object getAppDocEntityVO() {
        return appDocEntityVO;
    }

    public void setAppDocEntityVO(Object appDocEntityVO) {
        this.appDocEntityVO = appDocEntityVO;
    }
}
