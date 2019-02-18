package com.hjy.wisdommedicaldoctor.ui.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.aop.resultfilter.ResultCallBack;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.retrofit.load.up.UpLoadUtils;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.fy.baselibrary.utils.imgload.ImgLoadUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.GetDepartmentListAdapter;
import com.hjy.wisdommedicaldoctor.adapter.GetHospitalListAdapter;
import com.hjy.wisdommedicaldoctor.adapter.ProfessionalAdapter;
import com.hjy.wisdommedicaldoctor.bean.DepartmentBean;
import com.hjy.wisdommedicaldoctor.bean.HospitalListBean;
import com.hjy.wisdommedicaldoctor.bean.PersonInfoBean;
import com.hjy.wisdommedicaldoctor.bean.ProfessionalListBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;
import com.hjy.wisdommedicaldoctor.ui.NewUserEditActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * 个人资料
 * Created by Stefan on 2018/8/2 10:36.
 */
public class PersonInfoActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener, ResultCallBack {

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_nick)//昵称
            TextView tv_nick;
    @BindView(R.id.tv_name)//姓名
            TextView tv_name;
    @BindView(R.id.tv_sex)//性别
            TextView tv_sex;
    @BindView(R.id.tv_phone)//手机号码
            TextView tv_phone;
    @BindView(R.id.tv_dept)//科室
            TextView tv_dept;
    @BindView(R.id.tv_hospital)//任职医院
            TextView tv_hospital;
    @BindView(R.id.tv_docInfo)//个人简介
            TextView tv_docInfo;
    @BindView(R.id.tv_goodAt)//擅长
            TextView tv_goodAt;
    @BindView(R.id.tv_professional)
    TextView mTvProfessional;

    GetDepartmentListAdapter mGetDepartmentListAdapter;
    GetHospitalListAdapter mHospitalListAdapter;
    ProfessionalAdapter mProfessionalAdapter;

    ArrayList<ImageItem> images = null;

    private String mTrim;
    private int mDepartmentBeanId;
    private int mHospitalListBeanId;
    private int mProfessionalListBeanId;
    private KProgressHUD mKProgressHUD;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_person_info;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        tv_nick.setText(SpfUtils.getSpfSaveStr(DocConstant.nickname));
        tv_name.setText(SpfUtils.getSpfSaveStr(DocConstant.docName));
        if (SpfUtils.getSpfSaveInt(DocConstant.docSex) == 0) {
            tv_sex.setText("女");
        } else {
            tv_sex.setText("男");
        }
        tv_phone.setText(SpfUtils.getSpfSaveStr(DocConstant.docMobile));
        tv_goodAt.setText(SpfUtils.getSpfSaveStr(DocConstant.docSpecialty));

        tv_hospital.setText(SpfUtils.getSpfSaveStr(DocConstant.hospitalName));
        tv_dept.setText(SpfUtils.getSpfSaveStr(DocConstant.departmentName));
        mTvProfessional.setText(SpfUtils.getSpfSaveStr(DocConstant.titleName));
        tv_docInfo.setText(SpfUtils.getSpfSaveStr(DocConstant.docInfo));
        mDepartmentBeanId = SpfUtils.getSpfSaveInt(DocConstant.departmentId);
        mHospitalListBeanId = SpfUtils.getSpfSaveInt(DocConstant.hospitalId);
        mProfessionalListBeanId = SpfUtils.getSpfSaveInt(DocConstant.titleId);

        if (!TextUtils.isEmpty(SpfUtils.getSpfSaveStr(DocConstant.DocPhotoUrl))) {
            ImgLoadUtils.loadCircularBead(getApplicationContext(), ApiService.BASE_PIC_URL + SpfUtils.getSpfSaveStr(DocConstant.DocPhotoUrl), iv_header);
        }

        Drawable svg_right_arrow = TintUtils.getDrawable(R.drawable.svg_arrow_right, 1);
        TintUtils.setTxtIconLocal(tv_sex, svg_right_arrow, 2);

    }

    @OnClick({R.id.rl_sex, R.id.iv_header, R.id.Ll_name, R.id.Ll_phone, R.id.Ll_docInfo, R.id.Ll_Nick,
            R.id.Ll_goodAt, R.id.Ll_dept, R.id.Ll_Hospital, R.id.Ll_professional})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", view.getId());

        switch (view.getId()) {
            case R.id.rl_sex:
                setSexChoose();
                break;
            case R.id.iv_header:
                SetPhotoChoose();
                break;
            case R.id.Ll_Nick://昵称
                JumpUtils.jump(this, NewUserEditActivity.class, bundle, R.id.Ll_Nick, this);
                break;
            case R.id.Ll_name: //姓名
                String personName = tv_name.getText().toString();//姓名
                bundle.putString("personName", personName);
                JumpUtils.jump(this, NewUserEditActivity.class, bundle, R.id.Ll_name, this);
                break;
            case R.id.Ll_phone:
                String classPhone = tv_phone.getText().toString();//电话
                bundle.putString("classPhone", classPhone);
                JumpUtils.jump(this, NewUserEditActivity.class, bundle, R.id.Ll_phone, this);
                break;
            case R.id.Ll_docInfo: //个人简介
                String classAddress = tv_docInfo.getText().toString();//地址
                bundle.putString("classAddress", classAddress);
                JumpUtils.jump(this, NewUserEditActivity.class, bundle, R.id.Ll_docInfo, this);
                break;
            case R.id.Ll_goodAt: //擅长
                String goodAt = tv_goodAt.getText().toString();//擅长
                bundle.putString("goodAt", goodAt);
                JumpUtils.jump(this, NewUserEditActivity.class, bundle, R.id.Ll_goodAt, this);
                break;
            case R.id.Ll_dept: //科室
                GetDepartmentData();
                break;
            case R.id.Ll_Hospital: //醫院
                GetHospitalData();
                break;
            case R.id.Ll_professional: //职称
                GetProfessionalData();
                break;
            case R.id.menuLayout://提交
                if (TextUtils.isEmpty(tv_dept.getText().toString().trim())) {
                    T.showLong("科室不能为空");
                    return;
                }
                if (TextUtils.isEmpty(tv_hospital.getText().toString().trim())) {
                    T.showLong("医院不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mTvProfessional.getText().toString().trim())) {
                    T.showLong("职称不能为空");
                    return;
                }
                uploadImages("photo", images);
                break;
        }
    }

    private void GetProfessionalData() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_add_user)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        getProfessionalList();
                        RecyclerView mRecyclerView = holder.getView(R.id.rv_dialog_person);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        mRecyclerView.setHasFixedSize(true);
                        mProfessionalAdapter = new ProfessionalAdapter(new ArrayList<>());
                        mRecyclerView.setAdapter(mProfessionalAdapter);
                        mProfessionalAdapter.setOnItemClickListener((adapter, view, position) -> {
                            ProfessionalListBean professionalListBean = mProfessionalAdapter.getData().get(position);
                            mProfessionalListBeanId = professionalListBean.getId();
                            String titleName = professionalListBean.getTitleName();
                            mTvProfessional.setText(titleName);

                            dialog.dismiss();
                        });
                        holder.setOnClickListener(R.id.iv_dialog_add_user, v -> dialog.dismiss());
                    }
                }).setAnim(R.style.AnimDown)
                .setGravity(Gravity.BOTTOM)
                .show(getSupportFragmentManager());
    }

    /**
     * 科室列表（不分页）
     * department/listToApp
     */
    private void GetDepartmentData() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_add_user)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        departmentList();
                        RecyclerView mRecyclerView = holder.getView(R.id.rv_dialog_person);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        mRecyclerView.setHasFixedSize(true);
                        mGetDepartmentListAdapter = new GetDepartmentListAdapter(new ArrayList<>());
                        mRecyclerView.setAdapter(mGetDepartmentListAdapter);
                        mGetDepartmentListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                            DepartmentBean departmentBean = mGetDepartmentListAdapter.getData().get(position);
                            mDepartmentBeanId = departmentBean.getId();
                            String departmentName = departmentBean.getDepartmentName();
                            tv_dept.setText(departmentName);

                            dialog.dismiss();
                        });
                        holder.setOnClickListener(R.id.iv_dialog_add_user, v -> dialog.dismiss());
                    }
                }).setAnim(R.style.AnimDown)
                .setGravity(Gravity.BOTTOM)
                .show(getSupportFragmentManager());
    }

    /**
     * 科室列表List
     */
    private void departmentList() {
        RequestUtils.create(ApiService.class)
                .departmentList()
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<List<DepartmentBean>>(new NetDialog().init(this).setDialogMsg(R.string.loadProgress)) {
                    @Override
                    protected void onSuccess(List<DepartmentBean> departmentBeans) {
                        mGetDepartmentListAdapter.setNewData(departmentBeans);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });

    }

    /**
     * 医院列表（不分页）
     * hospital/listToApp
     */
    private void GetHospitalData() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_add_user)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        hospitalList();
                        RecyclerView mRecyclerView = holder.getView(R.id.rv_dialog_person);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        mRecyclerView.setHasFixedSize(true);
                        mHospitalListAdapter = new GetHospitalListAdapter(new ArrayList<>());
                        mRecyclerView.setAdapter(mHospitalListAdapter);
                        mHospitalListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                            HospitalListBean hospitalListBean = mHospitalListAdapter.getData().get(position);
                            mHospitalListBeanId = hospitalListBean.getId();
                            String hosName = hospitalListBean.getHosName();
                            tv_hospital.setText(hosName);
                            dialog.dismiss();
                        });
                        holder.setOnClickListener(R.id.iv_dialog_add_user, v -> dialog.dismiss());
                    }
                }).setAnim(R.style.AnimDown)
                .setGravity(Gravity.BOTTOM)
                .show(getSupportFragmentManager());
    }

    /**
     * 医院列表List
     */
    private void hospitalList() {
        RequestUtils.create(ApiService.class)
                .hospitalList()
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<List<HospitalListBean>>((new NetDialog().init(this).setDialogMsg(R.string.loadProgress))) {
                    @Override
                    protected void onSuccess(List<HospitalListBean> hospitalListBeans) {
                        mHospitalListAdapter.setNewData(hospitalListBeans);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    /**
     * 职称列表
     */
    private void getProfessionalList() {
        RequestUtils.create(ApiService.class)
                .getProfessionalList()
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<List<ProfessionalListBean>>() {
                    @Override
                    protected void onSuccess(List<ProfessionalListBean> t) {
                        mProfessionalAdapter.setNewData(t);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });

    }

    /**
     * 修改个人资料 被观察者
     *
     * @param imgUrls
     * @return
     */
    private Observable updateToApp(String imgUrls) {

        ArrayMap<String, Object> param = new ArrayMap<>();
        param.put("token", SpfUtils.getSpfSaveStr(DocConstant.token));
        param.put("id", SpfUtils.getSpfSaveInt(DocConstant.userId));
        param.put("nickname", SpfUtils.getSpfSaveStr(DocConstant.nickname));
        param.put("docName", tv_name.getText().toString());
        if (tv_sex.getText().toString().trim().equals("女")) {
            param.put("sex", 0);
        } else {
            param.put("sex", 1);
        }
        param.put("email", "");
        param.put("mobile", tv_phone.getText().toString().trim());
        param.put("departmentId", mDepartmentBeanId);//科室id
        param.put("hospitalId", mHospitalListBeanId);//任职医院ID
        param.put("titleId", mProfessionalListBeanId);//职称ID
        param.put("docInfo",tv_docInfo.getText().toString().trim());//个人简介
        param.put("specialty", tv_goodAt.getText().toString().trim());//擅长
        param.put("docPhotoUrl", imgUrls);//头像

        return RequestUtils.create(ApiService.class)
                .updateDoc(param)
                .compose(RxHelper.handleResult());
    }

    /**
     * 上传图片并提交用户反馈
     *
     * @param type  图片类型	photo(头像)、feedback(反馈)、medicalRecords(病历)
     * @param lists 文件列表
     */
    @SuppressLint("CheckResult")
    private void uploadImages(String type, List<ImageItem> lists) {
        mKProgressHUD = KProgressHUD.create(PersonInfoActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Observable<PersonInfoBean> observable;
        if (null != lists && lists.size() > 0) {
//            observable = Observable.just(lists);
            observable = Observable.just(lists)
                    .observeOn(Schedulers.io())
                    .map(new Function<List<ImageItem>, List<String>>() {
                        @Override
                        public List<String> apply(List<ImageItem> imageItems) throws Exception {
                            List<String> imgPath = new ArrayList<>();
                            for (ImageItem imageItem : imageItems) {
                                imgPath.add(imageItem.getPath());
                            }
                            return imgPath;
                        }
                    })
                    .map(new Function<List<String>, List<File>>() {
                        @Override
                        public List<File> apply(List<String> list) throws Exception {
                            return Luban.with(PersonInfoActivity.this)
                                    .load(list)
                                    .ignoreBy(100)
//                                    .setTargetDir(Environment.getDataDirectory().getPath())
                                    .get();
                        }
                    })
                    .map(new Function<List<File>, List<MultipartBody.Part>>() {
                        @Override
                        public List<MultipartBody.Part> apply(List<File> fileList) throws Exception {
                            return UpLoadUtils.fileToMultipartBodyPart(fileList);
                        }
                    }).flatMap(new Function<List<MultipartBody.Part>, ObservableSource<String>>() {
                        @Override
                        public ObservableSource<String> apply(List<MultipartBody.Part> files) throws Exception {
                            return RequestUtils.create(ApiService.class)
                                    .uploadPostFile(RequestBody.create(MediaType.parse("multipart/form-data"), type), files)
                                    .compose(RxHelper.handleResult());
                        }
                    }).flatMap(new Function<String, ObservableSource<PersonInfoBean>>() {
                        @Override
                        public ObservableSource<PersonInfoBean> apply(String images) throws Exception {
                            return updateToApp(images);

                        }
                    });

        } else {
            observable = updateToApp("");
        }
        observable.subscribe(new Consumer<PersonInfoBean>() {
            @Override
            public void accept(PersonInfoBean personInfoBean) throws Exception {
                if (personInfoBean != null) {
                    SpfUtils.saveStrToSpf(DocConstant.docName, personInfoBean.getDocName());//医生姓名
                    SpfUtils.saveStrToSpf(DocConstant.nickname, personInfoBean.getNickname());//医生昵称
                    SpfUtils.saveIntToSpf(DocConstant.userId, personInfoBean.getId());//医生id
                    SpfUtils.saveStrToSpf(DocConstant.docInfo, personInfoBean.getDocInfo());//个人简介
                    SpfUtils.saveIntToSpf(DocConstant.docSex, personInfoBean.getSex());//性别
                    SpfUtils.saveStrToSpf(DocConstant.docMobile, personInfoBean.getMobile());//手机
                    SpfUtils.saveStrToSpf(DocConstant.docSpecialty, personInfoBean.getSpecialty());//擅长

                    SpfUtils.saveStrToSpf(DocConstant.titleName, personInfoBean.getTitleName());//职称
                    SpfUtils.saveIntToSpf(DocConstant.titleId, personInfoBean.getTitleId());//医生职称ID
                    SpfUtils.saveStrToSpf(DocConstant.hospitalName, personInfoBean.getHospitalName());//医院
                    SpfUtils.saveStrToSpf(DocConstant.departmentName, personInfoBean.getDepartmentName());//科室
                    SpfUtils.saveIntToSpf(DocConstant.hospitalId, personInfoBean.getHospitalId());//医院id
                    SpfUtils.saveIntToSpf(DocConstant.departmentId, personInfoBean.getDepartmentId());//科室id
                    SpfUtils.saveStrToSpf(DocConstant.DocPhotoUrl, personInfoBean.getDocPhotoUrl());//頭像
                    JumpUtils.exitActivity(PersonInfoActivity.this);
                    mKProgressHUD.dismiss();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mKProgressHUD.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        View menuLayout = menu.findItem(R.id.menuSchedule).getActionView();
        menuLayout.setOnClickListener(this);

        return true;
    }

    @Override
    public void onActResult(int requestCode, int resultCode, Intent data) {
        //添加图片返回
        if (data != null && requestCode == REQUEST_CODE_SELECT) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            //添加图片返回
            if (images != null) {
                RequestOptions options = RequestOptions
                        .circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(com.hyphenate.easeui.R.drawable.ease_default_expression)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(this)
                        .load(images.get(0).path)
                        .apply(options)
                        .into(iv_header);
            }
        }
        if (resultCode == RESULT_OK && data != null) {
            mTrim = data.getStringExtra("trim");
            switch (requestCode) {
                case R.id.Ll_Nick:
                    tv_nick.setText(mTrim);
                    break;
                case R.id.Ll_name:
                    tv_name.setText(mTrim);
                    break;
                case R.id.Ll_phone:
                    tv_phone.setText(mTrim);
                    break;
                case R.id.Ll_docInfo:
                    tv_docInfo.setText(mTrim);
                    break;
                case R.id.Ll_goodAt:
                    tv_goodAt.setText(mTrim);
                    break;
            }
        }
    }

    private void setSexChoose() {
        NiceDialog.init().setLayoutId(R.layout.dialog_choose_sex)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        TextView tv_top = holder.getView(R.id.tv_top);
                        TextView tv_down = holder.getView(R.id.tv_down);
                        tv_top.setText(R.string.man);
                        tv_down.setText(R.string.woman);
                        tv_top.setOnClickListener(v -> {
                            tv_sex.setText(tv_top.getText().toString());
                            dialog.dismiss();
                        });
                        tv_down.setOnClickListener(v -> {
                            tv_sex.setText(tv_down.getText().toString());
                            dialog.dismiss();
                        });

                        holder.setOnClickListener(R.id.tvCancel, v -> dialog.dismiss());
                    }
                }).setWidthPixels(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.BOTTOM)
                .setAnim(R.style.AnimUp)
                .setHide(true)
                .show(getSupportFragmentManager());
    }

    private void SetPhotoChoose() {
        NiceDialog.init().setLayoutId(R.layout.dialog_choose_sex)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        TextView tv_top = holder.getView(R.id.tv_top);
                        TextView tv_down = holder.getView(R.id.tv_down);
                        tv_top.setText(R.string.photograph);
                        tv_down.setText(R.string.select_photo);
                        tv_top.setOnClickListener(v -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(1);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);// 是否是直接打开相机
                            JumpUtils.jump(PersonInfoActivity.this, ImageGridActivity.class, bundle, REQUEST_CODE_SELECT, PersonInfoActivity.this);
                            dialog.dismiss();
                        });
                        tv_down.setOnClickListener(v -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(1);
                            JumpUtils.jump(PersonInfoActivity.this, ImageGridActivity.class, null, REQUEST_CODE_SELECT, PersonInfoActivity.this);
                            dialog.dismiss();
                        });

                        holder.setOnClickListener(R.id.tvCancel, v -> dialog.dismiss());
                    }
                }).setWidthPixels(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.BOTTOM)
                .setAnim(R.style.AnimUp)
                .setHide(true)
                .show(getSupportFragmentManager());
    }
}
