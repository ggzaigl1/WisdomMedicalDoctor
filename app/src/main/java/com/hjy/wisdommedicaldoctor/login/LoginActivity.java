package com.hjy.wisdommedicaldoctor.login;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.aop.annotation.NeedPermission;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.LoginBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;
import com.hjy.wisdommedicaldoctor.hx.EMUtils;
import com.hjy.wisdommedicaldoctor.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by fangs on 2018/7/24 15:32.
 */
public class LoginActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.login_username)
    AppCompatEditText mLoginUsername;
    @BindView(R.id.login_password)
    AppCompatEditText mLoginPassword;
    @BindView(R.id.show_or_hide_password)
    AppCompatImageView mShowOrHidePassword;
    private Boolean showPassword = true;

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.activity_login;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

    }

    @OnClick({R.id.login_btn, R.id.show_or_hide_password, R.id.login_register,R.id.login_miss_password})
    @ClickFilter
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                String username = mLoginUsername.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();
                getLogin(username, password);
                break;
            case R.id.show_or_hide_password:
                if (showPassword) {// 显示密码
                    mShowOrHidePassword.setImageDrawable(getResources().getDrawable(R.mipmap.login_password_on));
                    mLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mLoginPassword.setSelection(mLoginPassword.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mShowOrHidePassword.setImageDrawable(getResources().getDrawable(R.mipmap.login_password_off));
                    mLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mLoginPassword.setSelection(mLoginPassword.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;

            case R.id.login_register:
                //注册
                startActivity(new Intent(LoginActivity.this, RegisterUserActivity.class));
                break;
            case R.id.login_miss_password:
                //忘记密码
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
        }
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     */
    @NeedPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void getLogin(String username, String password) {
        RequestUtils.create(ApiService.class)
                .login(username, password, 2)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<LoginBean>(new NetDialog().init(this).setDialogMsg(R.string.user_login)) {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        SpfUtils.saveStrToSpf(DocConstant.token, loginBean.getToken());
                        SpfUtils.saveBooleanToSpf(Constant.isLogin, true);
                        SpfUtils.saveStrToSpf(DocConstant.username, username);
                        SpfUtils.saveStrToSpf(DocConstant.password, password);

                        SpfUtils.saveStrToSpf(DocConstant.docName, loginBean.getAppUser().getDocName());//医生姓名
                        SpfUtils.saveStrToSpf(DocConstant.nickname, loginBean.getAppUser().getNickname());//医生昵称
                        SpfUtils.saveIntToSpf(DocConstant.userId, loginBean.getAppUser().getId());//医生id

                        SpfUtils.saveStrToSpf(DocConstant.docInfo, loginBean.getAppUser().getDocInfo());//个人简介
                        SpfUtils.saveIntToSpf(DocConstant.docSex, loginBean.getAppUser().getSex());//性别
                        SpfUtils.saveStrToSpf(DocConstant.docMobile, loginBean.getAppUser().getMobile());//手机
                        SpfUtils.saveStrToSpf(DocConstant.docSpecialty, loginBean.getAppUser().getSpecialty());//擅长

                        SpfUtils.saveStrToSpf(DocConstant.titleName, loginBean.getAppUser().getTitleName());//职称
                        SpfUtils.saveStrToSpf(DocConstant.hospitalName, loginBean.getAppUser().getHospitalName());//医院
                        SpfUtils.saveStrToSpf(DocConstant.departmentName, loginBean.getAppUser().getDepartmentName());//科室
                        SpfUtils.saveIntToSpf(DocConstant.titleId, loginBean.getAppUser().getTitleId());//医生职称ID
                        SpfUtils.saveIntToSpf(DocConstant.hospitalId, loginBean.getAppUser().getHospitalId());//医院id
                        SpfUtils.saveIntToSpf(DocConstant.departmentId, loginBean.getAppUser().getDepartmentId());//科室id
                        SpfUtils.saveStrToSpf(DocConstant.DocPhotoUrl, loginBean.getAppUser().getDocPhotoUrl());//頭像
                        JumpUtils.jump(LoginActivity.this, MainActivity.class, null);
                        EMUtils.loginEM(LoginActivity.this, mLoginUsername.getText().toString(), mLoginPassword.getText().toString());
                    }

                    @Override
                    protected void updataLayout(int flag) {
                        if (flag == 501) {
                            //重新激活发送验证码
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(LoginActivity.this.getString(R.string.system_title))
                                    .setMessage(LoginActivity.this.getString(R.string.login_notice))
                                    .setCancelable(true)
                                    .setPositiveButton(R.string.ok, (dialog, which) -> LoginActivity.this.GetSendEmailToAlive(username))
                                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss()).show();
                        }
                    }
                });
    }


    /**
     * 重新发送激活邮件
     *
     * @param username
     */
    private void GetSendEmailToAlive(String username) {
        RequestUtils.create(ApiService.class)
                .sendEmailToAlive(username)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<Object>((new NetDialog().init(this).setDialogMsg(R.string.loadProgress))) {
                    @Override
                    protected void onSuccess(Object t) {
                        T.showLong(getString(R.string.email_succeed));
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}
