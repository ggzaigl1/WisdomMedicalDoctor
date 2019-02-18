package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;

/**
 * Created by Stefan on 2018/4/24.
 */
public class LoginBean implements Serializable {


    /**
     * appUser : {"id":3,"docNo":"DLWP180908141904PKT","docName":"王曙光","titleId":4,"docPhotoUrl":null,"hospitalId":2,"departmentId":2,"specialty":"内分泌系统、鼻炎、青光眼","docInfo":"为人民服务","fans":null,"grade":null,"score":4.5,"commentCount":null,"diagnoseCount":30,"serviceCount":null,"serviceIncome":null,"duties":null,"personalDocPrice":null,"isExpert":null,"isTcm":null,"isShow":null,"isCheck":null,"isMarried":null,"isFreeClinicr":null,"isStopDiagnosis":null,"gmtCreate":null,"gmtModified":null,"nickname":"doc02","email":"wtuzhang@163.com","mobile":null,"sex":null,"hospitalName":"武汉同济医院","departmentName":"内科","titleName":"主任医师"}
     * expire : 2018-10-20 09:52:30
     * token : 1c8af795655b5148adbdf52ff20b649b
     */

    private AppUserBean appUser;
    private String expire;
    private String token;

    public AppUserBean getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserBean appUser) {
        this.appUser = appUser;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class AppUserBean {
        /**
         * id : 3
         * docNo : DLWP180908141904PKT
         * docName : 王曙光
         * titleId : 4
         * docPhotoUrl : null
         * hospitalId : 2
         * departmentId : 2
         * specialty : 内分泌系统、鼻炎、青光眼
         * docInfo : 为人民服务
         * fans : null
         * grade : null
         * score : 4.5
         * commentCount : null
         * diagnoseCount : 30
         * serviceCount : null
         * serviceIncome : null
         * duties : null
         * personalDocPrice : null
         * isExpert : null
         * isTcm : null
         * isShow : null
         * isCheck : null
         * isMarried : null
         * isFreeClinicr : null
         * isStopDiagnosis : null
         * gmtCreate : null
         * gmtModified : null
         * nickname : doc02
         * email : wtuzhang@163.com
         * mobile : null
         * sex : null
         * hospitalName : 武汉同济医院
         * departmentName : 内科
         * titleName : 主任医师
         */

        private int id;
        private String docNo;
        private String docName;
        private int titleId;
        private String docPhotoUrl;
        private int hospitalId;
        private int departmentId;
        private String specialty;
        private String docInfo;
        private Object fans;
        private Object grade;
        private double score;
        private Object commentCount;
        private int diagnoseCount;
        private Object serviceCount;
        private Object serviceIncome;
        private Object duties;
        private Object personalDocPrice;
        private Object isExpert;
        private Object isTcm;
        private Object isShow;
        private Object isCheck;
        private Object isMarried;
        private Object isFreeClinicr;
        private Object isStopDiagnosis;
        private Object gmtCreate;
        private Object gmtModified;
        private String nickname;
        private String email;
        private String mobile;
        private int sex;
        private String hospitalName;
        private String departmentName;
        private String titleName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDocNo() {
            return docNo;
        }

        public void setDocNo(String docNo) {
            this.docNo = docNo;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public int getTitleId() {
            return titleId;
        }

        public void setTitleId(int titleId) {
            this.titleId = titleId;
        }

        public String getDocPhotoUrl() {
            return docPhotoUrl;
        }

        public void setDocPhotoUrl(String docPhotoUrl) {
            this.docPhotoUrl = docPhotoUrl;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getDocInfo() {
            return docInfo;
        }

        public void setDocInfo(String docInfo) {
            this.docInfo = docInfo;
        }

        public Object getFans() {
            return fans;
        }

        public void setFans(Object fans) {
            this.fans = fans;
        }

        public Object getGrade() {
            return grade;
        }

        public void setGrade(Object grade) {
            this.grade = grade;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public Object getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Object commentCount) {
            this.commentCount = commentCount;
        }

        public int getDiagnoseCount() {
            return diagnoseCount;
        }

        public void setDiagnoseCount(int diagnoseCount) {
            this.diagnoseCount = diagnoseCount;
        }

        public Object getServiceCount() {
            return serviceCount;
        }

        public void setServiceCount(Object serviceCount) {
            this.serviceCount = serviceCount;
        }

        public Object getServiceIncome() {
            return serviceIncome;
        }

        public void setServiceIncome(Object serviceIncome) {
            this.serviceIncome = serviceIncome;
        }

        public Object getDuties() {
            return duties;
        }

        public void setDuties(Object duties) {
            this.duties = duties;
        }

        public Object getPersonalDocPrice() {
            return personalDocPrice;
        }

        public void setPersonalDocPrice(Object personalDocPrice) {
            this.personalDocPrice = personalDocPrice;
        }

        public Object getIsExpert() {
            return isExpert;
        }

        public void setIsExpert(Object isExpert) {
            this.isExpert = isExpert;
        }

        public Object getIsTcm() {
            return isTcm;
        }

        public void setIsTcm(Object isTcm) {
            this.isTcm = isTcm;
        }

        public Object getIsShow() {
            return isShow;
        }

        public void setIsShow(Object isShow) {
            this.isShow = isShow;
        }

        public Object getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(Object isCheck) {
            this.isCheck = isCheck;
        }

        public Object getIsMarried() {
            return isMarried;
        }

        public void setIsMarried(Object isMarried) {
            this.isMarried = isMarried;
        }

        public Object getIsFreeClinicr() {
            return isFreeClinicr;
        }

        public void setIsFreeClinicr(Object isFreeClinicr) {
            this.isFreeClinicr = isFreeClinicr;
        }

        public Object getIsStopDiagnosis() {
            return isStopDiagnosis;
        }

        public void setIsStopDiagnosis(Object isStopDiagnosis) {
            this.isStopDiagnosis = isStopDiagnosis;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
    }
}