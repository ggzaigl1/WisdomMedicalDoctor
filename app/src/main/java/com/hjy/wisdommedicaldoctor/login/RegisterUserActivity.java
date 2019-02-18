package com.hjy.wisdommedicaldoctor.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.Validator;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 初夏小溪 on 2018/7/6 0006.
 * 注册
 */
public class RegisterUserActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_new_psd)
    EditText et_new_psd;
    @BindView(R.id.iv_new_pad)
    ImageView iv_new_pad;

    @BindView(R.id.et_new_sure_psd)
    EditText et_new_sure_psd;
    @BindView(R.id.iv_new_sure_psd)
    ImageView iv_new_sure_psd;
    private Boolean showPassword = true;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_register_user;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
    }

    @OnClick({R.id.iv_new_pad, R.id.iv_new_sure_psd, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_new_pad:
                if (showPassword) {// 显示密码
                    iv_new_pad.setImageDrawable(getResources().getDrawable(R.mipmap.login_password_on));
                    et_new_psd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_new_psd.setSelection(et_new_psd.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    iv_new_pad.setImageDrawable(getResources().getDrawable(R.mipmap.login_password_off));
                    et_new_psd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_new_psd.setSelection(et_new_psd.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.iv_new_sure_psd:
                if (showPassword) {// 显示密码
                    iv_new_sure_psd.setImageDrawable(getResources().getDrawable(R.mipmap.login_password_on));
                    et_new_sure_psd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_new_sure_psd.setSelection(et_new_sure_psd.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    iv_new_sure_psd.setImageDrawable(getResources().getDrawable(R.mipmap.login_password_off));
                    et_new_sure_psd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_new_sure_psd.setSelection(et_new_sure_psd.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.btnRegister:
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String pass = et_new_psd.getText().toString().trim();
                String surePass = et_new_sure_psd.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    T.showLong(getString(R.string.username_empty));
                    return;
                } else if (TextUtils.isEmpty(email)) {
                    T.showLong(getString(R.string.email_empty));
                    return;
                } else if (!Validator.isEmail(email)) {
                    T.showLong(getString(R.string.username_not));
                    return;
                } else if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(surePass)) {
                    T.showLong(getString(R.string.pad_or_confirmationPas_empty));
                    return;
                } else if (!pass.equals(surePass)) {
                    T.showLong(getString(R.string.passwords_or_confirmation_not_match));
                    return;
                } else if (et_new_psd.getText().length() > 6) {
                    T.showShort(getString(R.string.new_psd_empty_len));
                    return;
                } else if (et_new_sure_psd.getText().toString().length() > 6) {
                    T.showShort(getString(R.string.sure_psd_empty_len));
                    return;
                }
                register(name, pass, email);
                break;
        }
    }

    /**
     * 注册 regSource 注册渠道
     * regSource 参数说明 患者端：1  医生端：2
     *
     * @param username
     * @param password
     * @param email
     */
    @SuppressLint("CheckResult")
    private void register(String username, String password, String email) {
        ArrayMap<String, String> parame = new ArrayMap<>();
        parame.put("username", username);
        parame.put("password", password);
        parame.put("email", email);
        parame.put("regSource", "2");
        RequestUtils.create(ApiService.class)
                .register(parame)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<Object>(new NetDialog().init(this).setDialogMsg(R.string.register_loading)) {
                    @Override
                    protected void onSuccess(Object t) {
                        T.showShort(getString(R.string.register_successful));
                        JumpUtils.exitActivity(RegisterUserActivity.this);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
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
