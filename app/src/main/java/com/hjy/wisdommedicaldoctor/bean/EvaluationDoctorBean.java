package com.hjy.wisdommedicaldoctor.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Stefan on 2018/7/30.
 * 评价列表（分页）
 */
public class EvaluationDoctorBean implements Serializable {

    /**
     * offset : 0
     * limit : 2147483647
     * pageNo : 1
     * pageSize : 20
     * rows : [{"id":28,"consultId":null,"docNo":"21832496","userId":25,"serviceTypeId":1,"consultDisease":"空调病","sorce":4,"evaluationTags":"[很有医德, 态度很好, 治疗没有效果]","evaluationContent":"很不错的。","isEvaluation":null,"gmtCreate":"2018-08-09 15:05:01","gmtModified":null,"username":"yyy","photoUrl":"/photo/20180803/94dae6725f2e41a39d0c027ad8185a78.png"},{"id":23,"consultId":null,"docNo":"21832496","userId":25,"serviceTypeId":1,"consultDisease":null,"sorce":3,"evaluationTags":null,"evaluationContent":"好好好","isEvaluation":null,"gmtCreate":"2018-08-08 16:44:13","gmtModified":null,"username":"yyy","photoUrl":"/photo/20180803/94dae6725f2e41a39d0c027ad8185a78.png"},{"id":22,"consultId":null,"docNo":"21832496","userId":25,"serviceTypeId":1,"consultDisease":null,"sorce":0,"evaluationTags":null,"evaluationContent":"很棒","isEvaluation":null,"gmtCreate":"2018-08-08 16:32:27","gmtModified":null,"username":"yyy","photoUrl":"/photo/20180803/94dae6725f2e41a39d0c027ad8185a78.png"}]
     * total : 3
     * totalPages : 1
     * first : 1
     */

    private int offset;
    private int limit;
    private int pageNo;
    private int pageSize;
    private int total;
    private int totalPages;
    private int first;
    private List<RowsBean> rows;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable{
        /**
         * id : 28
         * consultId : null
         * docNo : 21832496
         * userId : 25
         * serviceTypeId : 1
         * consultDisease : 空调病
         * sorce : 4.0
         * evaluationTags : [很有医德, 态度很好, 治疗没有效果]
         * evaluationContent : 很不错的。
         * isEvaluation : null
         * gmtCreate : 2018-08-09 15:05:01
         * gmtModified : null
         * username : yyy
         * photoUrl : /photo/20180803/94dae6725f2e41a39d0c027ad8185a78.png
         */

        private int id;
        private Integer consultId;
        private String docNo;
        private int userId;
        private int serviceTypeId;
        private String consultDisease;
        private double sorce;
        private String evaluationTags;
        private String evaluationContent;
        private Integer isEvaluation;
        private String gmtCreate;
        private Object gmtModified;
        private String username;
        private String photoUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getConsultId() {
            return consultId;
        }

        public void setConsultId(Integer consultId) {
            this.consultId = consultId;
        }

        public String getDocNo() {
            return docNo;
        }

        public void setDocNo(String docNo) {
            this.docNo = docNo;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getServiceTypeId() {
            return serviceTypeId;
        }

        public void setServiceTypeId(int serviceTypeId) {
            this.serviceTypeId = serviceTypeId;
        }

        public String getConsultDisease() {
            return consultDisease;
        }

        public void setConsultDisease(String consultDisease) {
            this.consultDisease = consultDisease;
        }

        public double getSorce() {
            return sorce;
        }

        public void setSorce(double sorce) {
            this.sorce = sorce;
        }

        public String getEvaluationTags() {
            return evaluationTags;
        }

        public void setEvaluationTags(String evaluationTags) {
            this.evaluationTags = evaluationTags;
        }

        public String getEvaluationContent() {
            return evaluationContent;
        }

        public void setEvaluationContent(String evaluationContent) {
            this.evaluationContent = evaluationContent;
        }

        public Integer getIsEvaluation() {
            return isEvaluation;
        }

        public void setIsEvaluation(Integer isEvaluation) {
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }
}