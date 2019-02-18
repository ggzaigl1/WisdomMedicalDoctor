package com.hjy.wisdommedicaldoctor.constant;

/**
 * Created by QKun on 2018/7/6.
 */
public class DocConstant {

    public static final String BASE_URL = "http://192.168.100.251:8089/";

    public static final String token = "token";

    /**
     * 用户是否登录 key
     */
    public static String isLogin = "is_Login";


    public static final String username = "username";

    public static final String docName = "docName";

    public static final String nickname = "nickname";

    public static final String password = "password";

    public static final String userId = "id";

    public static final String titleId = "titleId";

    public static final String titleName = "titleName";

    public static final String docInfo = "docInfo";

    public static final String docSex = "docSex";

    public static final String docMobile = "docMobile";

    public static final String docSpecialty = "docSpecialty";

    public static final String hospitalName = "hospitalName";

    public static final String departmentName = "departmentName";

    public static final String hospitalId = "hospitalId";

    public static final String departmentId = "departmentId";

    public static final String DocPhotoUrl = "DocPhotoUrl";

    public static final int timeTitle = 1002;//标题

    //统一定义 排班时间段
    public static final String[] time = {
            "",
            "09:00~09:30",
            "09:30~10:00",
            "10:00~10:30",
            "10:30~11:00",
            "11:00~11:30",
            "11:30~12:00",
            "14:00~14:30",
            "14:30~15:00",
            "15:00~15:30",
            "15:30~16:00",
            "16:00~16:30",
            "16:30~17:00" };
}
