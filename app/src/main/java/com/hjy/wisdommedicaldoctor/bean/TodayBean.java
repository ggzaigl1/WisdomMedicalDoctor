package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by QKun on 2018/9/12.
 */
public class TodayBean implements Serializable {

    /**
     * serviceId : null
     * selectedCount : null
     * listConsultRecord : [{"id":197,"cusId":75,"username":"doc06","docId":14,"serviceId":3,"visitMemberId":64,"diseaseName":"空调病","consultContent":"东方红斤斤计较斤斤计较哈哈不哈","image":"/medicalRecords/20180921/3900e593ea664ce499d93c3ff588a4be.png","reserveDate":"2018-09-21","reserveTime":"10","orderNo":"YY1809211414884719","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":201,"cusId":55,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":54,"diseaseName":"空调病","consultContent":"皮皮时光机","image":"","reserveDate":"2018-09-21","reserveTime":"11","orderNo":"SP1809211451378312","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":202,"cusId":55,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":58,"diseaseName":"感冒","consultContent":"摸摸头","image":"","reserveDate":"2018-09-21","reserveTime":"12","orderNo":"SP1809211452735935","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":192,"cusId":72,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":63,"diseaseName":"中暑","consultContent":"风风光光该喝喝古古怪怪嘎嘎嘎嘎嘎嘎嘎","image":"","reserveDate":"2018-09-21","reserveTime":"3","orderNo":"SP1809211357408465","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":187,"cusId":72,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":63,"diseaseName":"空调病","consultContent":"发个呵呵叫姐姐斤斤计较叽叽叽叽叽叽叽叽","image":"/medicalRecords/20180921/19fc4c0a7a134e389ca3efd14487991c.png","reserveDate":"2018-09-21","reserveTime":"5","orderNo":"SP1809211116117145","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":188,"cusId":72,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":63,"diseaseName":"空调病","consultContent":"发个呵呵叫姐姐斤斤计较叽叽叽叽叽叽叽叽","image":"/medicalRecords/20180921/2f2cb8ce51a7415eb16db4da4cae2146.png","reserveDate":"2018-09-21","reserveTime":"5","orderNo":"SP1809211116490909","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":189,"cusId":72,"username":"doc06","docId":14,"serviceId":3,"visitMemberId":63,"diseaseName":"感冒","consultContent":"发个哼哼唧唧斤斤计较急急急卡卡咔咔咔","image":"/medicalRecords/20180921/d2ee680abc254d63b65be8b18c40c608.png","reserveDate":"2018-09-21","reserveTime":"6","orderNo":"YY1809211129551190","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":194,"cusId":55,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":58,"diseaseName":"感冒","consultContent":"fff","image":"","reserveDate":"2018-09-21","reserveTime":"8","orderNo":"SP1809211400794847","orderStatus":8,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null},{"id":196,"cusId":75,"username":"doc06","docId":14,"serviceId":2,"visitMemberId":64,"diseaseName":"空调病","consultContent":"大概红红火火恍恍惚惚哈哈哈哈哈哈","image":"/medicalRecords/20180921/70ee01f78ee748cb9c2fcfe784444613.png","reserveDate":"2018-09-21","reserveTime":"9","orderNo":"SP1809211414392712","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":null}]
     * listTempConsultRecord : [{"serviceId":2,"selectedCount":7,"listConsultRecord":null,"listTempConsultRecord":null},{"serviceId":3,"selectedCount":2,"listConsultRecord":null,"listTempConsultRecord":null}]
     */

    private Object serviceId;
    private Object selectedCount;
    private List<TodayBean.ListConsultRecordBean> listConsultRecord;
    private List<TodayBean.ListTempConsultRecordBean> listTempConsultRecord;

    public Object getServiceId() {
        return serviceId;
    }

    public void setServiceId(Object serviceId) {
        this.serviceId = serviceId;
    }

    public Object getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Object selectedCount) {
        this.selectedCount = selectedCount;
    }

    public List<TodayBean.ListConsultRecordBean> getListConsultRecord() {
        return listConsultRecord;
    }

    public void setListConsultRecord(List<TodayBean.ListConsultRecordBean> listConsultRecord) {
        this.listConsultRecord = listConsultRecord;
    }

    public List<TodayBean.ListTempConsultRecordBean> getListTempConsultRecord() {
        return listTempConsultRecord;
    }

    public void setListTempConsultRecord(List<TodayBean.ListTempConsultRecordBean> listTempConsultRecord) {
        this.listTempConsultRecord = listTempConsultRecord;
    }

    public static class ListConsultRecordBean implements Serializable {
        /**
         * id : 197
         * cusId : 75
         * username : doc06
         * docId : 14
         * serviceId : 3
         * visitMemberId : 64
         * diseaseName : 空调病
         * consultContent : 东方红斤斤计较斤斤计较哈哈不哈
         * image : /medicalRecords/20180921/3900e593ea664ce499d93c3ff588a4be.png
         * reserveDate : 2018-09-21
         * reserveTime : 10
         * orderNo : YY1809211414884719
         * orderStatus : 6
         * payType : null
         * isEvaluation : null
         * gmtCreate : null
         * gmtModified : null
         * memberName : null
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
        private String reserveTime;
        private String orderNo;
        private int orderStatus;
        private Object payType;
        private Object isEvaluation;
        private Object gmtCreate;
        private Object gmtModified;
        private Object memberName;

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

        public Object getMemberName() {
            return memberName;
        }

        public void setMemberName(Object memberName) {
            this.memberName = memberName;
        }
    }

    public static class ListTempConsultRecordBean implements Serializable {
        /**
         * serviceId : 2
         * selectedCount : 7
         * listConsultRecord : null
         * listTempConsultRecord : null
         */

        private int serviceId;
        private int selectedCount;
        private Object listConsultRecord;
        private Object listTempConsultRecord;

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public int getSelectedCount() {
            return selectedCount;
        }

        public void setSelectedCount(int selectedCount) {
            this.selectedCount = selectedCount;
        }

        public Object getListConsultRecord() {
            return listConsultRecord;
        }

        public void setListConsultRecord(Object listConsultRecord) {
            this.listConsultRecord = listConsultRecord;
        }

        public Object getListTempConsultRecord() {
            return listTempConsultRecord;
        }

        public void setListTempConsultRecord(Object listTempConsultRecord) {
            this.listTempConsultRecord = listTempConsultRecord;
        }
    }
}
