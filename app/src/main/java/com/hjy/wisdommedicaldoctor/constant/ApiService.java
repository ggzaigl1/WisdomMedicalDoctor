package com.hjy.wisdommedicaldoctor.constant;

import android.util.ArrayMap;

import com.hjy.wisdommedicaldoctor.bean.DepartmentBean;
import com.hjy.wisdommedicaldoctor.bean.HospitalListBean;
import com.hjy.wisdommedicaldoctor.bean.LoginBean;
import com.hjy.wisdommedicaldoctor.bean.MakeBean;
import com.hjy.wisdommedicaldoctor.bean.OrderBean;
import com.hjy.wisdommedicaldoctor.bean.PatientListBean;
import com.hjy.wisdommedicaldoctor.bean.PersonInfoBean;
import com.hjy.wisdommedicaldoctor.bean.ProfessionalListBean;
import com.hjy.wisdommedicaldoctor.bean.RoomListBean;
import com.hjy.wisdommedicaldoctor.bean.SchedulingListBean;
import com.hjy.wisdommedicaldoctor.bean.TodayBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 接口 API
 * Created by Stefan on 2018/4/23.
 */
public interface ApiService {

    String BASE_URL = "http://116.196.95.169/ ";
    String BASE_PIC_URL = "http://116.196.95.169/file/getImages?imageurl=";
//    测试地址
//    String BASE_URL = "http://192.168.100.251:8089/";
//    String BASE_PIC_URL = "http://192.168.100.251:8089/file/getImages?imageurl=";

    /**
     * 1.1.我的订单列表
     */
    @GET("app/doc/listOrder")
    Observable<DocBaseBean<List<OrderBean>>> listOrder(@Query("docId") int docId, @Query("serviceId") int serviceId);


    /**
     * 1.3.今日待办事项
     */
    @GET("app/doc/todayTODO")
    Observable<DocBaseBean<TodayBean>> todayTODO(@Query("docId") int docId);

    /**
     * 1.4.查询所有待办事项(日期筛选)
     */
    @GET("app/doc/allTODO")
    Observable<DocBaseBean<TodayBean>> allTODO(@QueryMap Map<String, Object> options);

    /**
     * 3.1.个人资料修改
     */
    @FormUrlEncoded
    @POST("app/doc/updateDoc")
    Observable<DocBaseBean<PersonInfoBean>> updateDoc(@FieldMap ArrayMap<String, Object> params);

    /**
     * 4.1.注册（邮箱注册）
     *
     * @return
     */
    @POST("app/user/registerToApp")
    Observable<DocBaseBean<Object>> register(@Body ArrayMap<String, String> params);

    /**
     * 4.2.重新发送激活邮件
     *
     * @param username
     * @return
     */
    @GET("app/user/sendEmailToAlive")
    Observable<DocBaseBean<Object>> sendEmailToAlive(@Query("username") String username);

    /**
     * 4.3.修改密码 设置--修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/updatePwd")
    Observable<DocBaseBean<Object>> updatePwd(@Field("username") String username, @Field("oldPwd") String oldPwd, @Field("newPwd") String password);

    /**
     * 4.4.通过邮箱找回密码
     * 4.4.1.发送邮件（验证码)
     *
     * @param username
     * @return
     */
    @GET("app/user/findPwdByEmail")
    Observable<DocBaseBean<Object> >findPwdByEmail(@Query("username") String username);

    /**
     * 4.4.2.重新设置密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/resetPwdByEmail")
    Observable<DocBaseBean<String>> resetPwdByEmail(@Field("username") String username
            , @Field("newPwd") String newPwd
            , @Field("code") String code);

    /**
     * 4.5.登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/loginToApp")
    Observable<DocBaseBean<LoginBean>> login(@Field("username") String username
            , @Field("password") String password
            , @Field("regSource") int regSource);

    /**
     * 排班列表
     */
    @GET("app/doc/listScheduleWeek")
    Observable<DocBaseBean<SchedulingListBean>> getListSchedule(@QueryMap ArrayMap<String, Object> params);

    /**
     * 新建排班
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/doc/saveSchedule")
    Observable<DocBaseBean<String>> newScheduling(@FieldMap ArrayMap<String, Object> params);

    /**
     * 医生可预约时间段
     *
     * @return
     */
    @GET("app/schedule/listToApp2")
    Observable<DocBaseBean<List<MakeBean>>> scheduleList(@QueryMap ArrayMap<String, Object> params);

    /**
     * 删除排班
     *
     * @return
     */
    @GET("app/doc/removeSchedule")
    Observable<DocBaseBean<String>> removeSchedule(@Query("id") long id);

    /**
     * 修改排班
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/doc/updateSchedule")
    Observable<DocBaseBean<String>> updateSchedule(@FieldMap ArrayMap<String, Object> params);

    /**
     * 停用/启用排班
     *
     * @return
     */
    @GET("app/doc/changeStatusSchedule")
    Observable<DocBaseBean<String>> changeStatusSchedule(@QueryMap ArrayMap<String, Object> params);

    /**
     * 医院列表
     *
     * @return
     */
    @GET("app/hospital/listToApp")
    Observable<DocBaseBean<List<HospitalListBean>>> hospitalList();


    /**
     * 科室列表
     *
     * @return
     */
    @GET("app/department/listToApp")
    Observable<DocBaseBean<List<DepartmentBean>>> departmentList();

    /**
     * 诊室列表
     *
     * @param params
     * @return
     */
    @GET("app/doc/consultRoom")
    Observable<DocBaseBean<RoomListBean>> getRoomList(@QueryMap ArrayMap<String, Object> params);

    /**
     * 职称列表
     *
     * @return
     */
    @GET("app/docTitle/listToApp")
    Observable<DocBaseBean<List<ProfessionalListBean>>> getProfessionalList();

    /**
     * 患者管理列表
     *
     * @param docId
     * @return
     */
    @GET("app/doc/patientManage")
    Observable<DocBaseBean<List<PatientListBean>>> getPatientList(@Query("docId") int docId);

    /**
     * 判断患者是否进入诊室
     *
     * @param id
     * @return
     */
    @GET("app/doc/isComeIn")
    Observable<DocBaseBean<Boolean>> isComeIn(@Query("id") int id);

    /**
     * 4.7.意见反馈
     */
    @POST("app/feedback/save")
    Observable<DocBaseBean<String>> feedback(@Body ArrayMap<String, Object> options);

    /**
     * 多图片上传
     *
     * @param type
     * @param files
     * @return
     */
    @Multipart
    @POST("file/uploadImages")
    Observable<DocBaseBean<String>> uploadPostFile(@Part("type") RequestBody type,
                                                   @Part List<MultipartBody.Part> files);

}

