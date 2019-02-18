package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by QKun on 2018/9/15.
 */
public class RoomListBean implements Serializable {


    /**
     * offset : 0
     * limit : 2147483647
     * pageNo : 1
     * pageSize : 10
     * rows : [{"id":124,"cusId":63,"username":"duzm","docId":2,"serviceId":2,"visitMemberId":56,"diseaseName":"空调病","consultContent":"ftfggg","image":"","reserveDate":"2018-09-20","reserveTime":"1","orderNo":"SP1809181124746664","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":"州府"},{"id":129,"cusId":53,"username":"aaa","docId":2,"serviceId":2,"visitMemberId":53,"diseaseName":"空调病","consultContent":"大王","image":"","reserveDate":"2018-09-19","reserveTime":"2","orderNo":"SP1809181434520850","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":"张静"},{"id":116,"cusId":55,"username":"bbb","docId":2,"serviceId":2,"visitMemberId":54,"diseaseName":"空调病","consultContent":"真的不舒服","image":"","reserveDate":"2018-09-09","reserveTime":"2","orderNo":"SP1809171004522740","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":"张静"},{"id":137,"cusId":53,"username":"aaa","docId":2,"serviceId":2,"visitMemberId":57,"diseaseName":"中暑","consultContent":"来巡山看看","image":"","reserveDate":"2018-09-19","reserveTime":"5","orderNo":"SP1809181710718151","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":"方帅"},{"id":138,"cusId":53,"username":"aaa","docId":2,"serviceId":2,"visitMemberId":57,"diseaseName":"中暑","consultContent":"老铁","image":"","reserveDate":"2018-09-19","reserveTime":"6","orderNo":"SP1809181718820521","orderStatus":6,"payType":null,"isEvaluation":null,"gmtCreate":null,"gmtModified":null,"memberName":"方帅"}]
     * total : 5
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

    public static class RowsBean {
        /**
         * id : 124
         * cusId : 63
         * username : duzm
         * docId : 2
         * serviceId : 2
         * visitMemberId : 56
         * diseaseName : 空调病
         * consultContent : ftfggg
         * image :
         * reserveDate : 2018-09-20
         * reserveTime : 1
         * orderNo : SP1809181124746664
         * orderStatus : 6
         * payType : null
         * isEvaluation : null
         * gmtCreate : null
         * gmtModified : null
         * memberName : 州府
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
        private String chatGroupId = "";//群聊id
        private int orderStatus;//订单状态
        private Object payType;
        private Object isEvaluation;
        private Object gmtCreate;
        private Object gmtModified;
        private String memberName;

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

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getChatGroupId() {
            return chatGroupId;
        }

        public void setChatGroupId(String chatGroupId) {
            this.chatGroupId = chatGroupId;
        }
    }
}

