package com.hjy.wisdommedicaldoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 排班列表 实体类
 * Created by fangs on 2018/7/31 15:50.
 */
public class SchedulingListBean implements Serializable {

    /**
     * offset : 0
     * limit : 2147483647
     * pageNo : 1
     * pageSize : 20
     * rows : [{"id":27,"startDate":"2018-09-13 00:00:00.0","endDate":"2018-09-20 00:00:00.0","status":1,"docId":2,"gmtCreate":"2018-09-14 18:38:14","gmtModified":null,"listSchedule":null},{"id":26,"startDate":"2018-09-14 00:00:00.0","endDate":"2018-09-21 00:00:00.0","status":1,"docId":2,"gmtCreate":"2018-09-14 18:33:53","gmtModified":null,"listSchedule":null},{"id":25,"startDate":"2018-10-08 00:00:00.0","endDate":"2018-10-14 00:00:00.0","status":1,"docId":17,"gmtCreate":"2018-09-14 16:34:41","gmtModified":null,"listSchedule":null}]
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

    public static class RowsBean {
        /**
         * id : 27
         * startDate : 2018-09-13 00:00:00.0
         * endDate : 2018-09-20 00:00:00.0
         * status : 1
         * docId : 2
         * gmtCreate : 2018-09-14 18:38:14
         * gmtModified : null
         * listSchedule : null
         */

        private int id;
        private String startDate = "";
        private String endDate = "";
        private int status;//1：启用；2：停用
        private int docId;
        private String gmtCreate = "";
        private Object gmtModified;
        private Object listSchedule;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDocId() {
            return docId;
        }

        public void setDocId(int docId) {
            this.docId = docId;
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

        public Object getListSchedule() {
            return listSchedule;
        }

        public void setListSchedule(Object listSchedule) {
            this.listSchedule = listSchedule;
        }
    }
}
