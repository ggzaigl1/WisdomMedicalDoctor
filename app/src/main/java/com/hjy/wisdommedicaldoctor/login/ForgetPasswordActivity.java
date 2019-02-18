package com.hjy.wisdommedicaldoctor.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 初夏小溪 on 2018/7/6 0006.
 * 忘记密码
 */

public class ForgetPasswordActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.et_new_psd)
    EditText et_new_psd;
    @BindView(R.id.iv_new_pad)
    ImageView iv_new_pad;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_new_sure_psd)
    EditText et_new_sure_psd;
    @BindView(R.id.et_security_code)
    EditText et_security_code;
    @BindView(R.id.iv_new_sure_psd)
    ImageView iv_new_sure_psd;
    @BindView(R.id.bt_code)
    Button bt_code;

    private Boolean showPassword = true;
    private Disposable mDisposable;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_forget_password;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

    }

    @OnClick({R.id.iv_new_pad, R.id.iv_new_sure_psd, R.id.bt_code, R.id.login_btn})
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
            case R.id.bt_code:
                if (et_username.getText().toString().trim().equals("")) {
                    T.showShort(getString(R.string.please_enter_username));
                    return;
                }
                if (bt_code.getText().equals(getString(R.string.verification_code))) {
                    //获取验证码
                    GetFindPwdByEmail(et_username.getText().toString());
                    //倒计时完毕置为可点击状态
                    bt_code.setEnabled(false);
                    mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<Long>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    long mL = 59 - aLong;
                                    bt_code.setText(mL + "s");
                                }
                            })
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Exception {
                                    //倒计时完毕置为可点击状态
                                    bt_code.setEnabled(true);
                                    bt_code.setText(R.string.verification_code);
                                }
                            })
                            .subscribe();
                }
                break;
            case R.id.login_btn:
                if (et_username.getText().toString().trim().equals("")) {
                    T.showShort(getString(R.string.username_empty));
                    return;
                } else if (et_new_psd.getText().toString().equals("")) {
                    T.showShort(getString(R.string.new_psd_empty));
                    return;
                } else if (et_new_sure_psd.getText().toString().trim().equals("")) {
                    T.showShort(getString(R.string.sure_psd_empty));
                    return;
                } else if (et_security_code.getText().toString().equals("")) {
                    T.showShort(getString(R.string.code_empty));
                    return;
                } else if (!et_new_psd.getText().toString().equals(et_new_sure_psd.getText().toString())) {
                    T.showShort(getString(R.string.two_passwords_not_match));
                    return;
                }
                GetUpdatePwdByEmail(et_username.getText().toString(), et_new_sure_psd.getText().toString().trim(), et_security_code.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    /**
     * 通过邮箱找回密码 发送邮件（验证码）
     *
     * @param name
     */
    @SuppressLint("CheckResult")
    private void GetFindPwdByEmail(String name) {
        RequestUtils.create(ApiService.class)
                .findPwdByEmail(name)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<Object>(new NetDialog().init(this).setDialogMsg(R.string.loading_get)) {
                    @Override
                    protected void onSuccess(Object bean) {
                        T.showLong(getString(R.string.email_succeed));
                    }

                    @Override
                    protected void updataLayout(int flag) {
                    }
                });
    }

    /**
     * 重新设置密码
     *
     * @param username
     * @param newPwd
     * @param code
     */
    @SuppressLint("CheckResult")
    private void GetUpdatePwdByEmail(String username, String newPwd, String code) {
        RequestUtils.create(ApiService.class)
                .resetPwdByEmail(username, newPwd, code)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<Object>(new NetDialog().init(this).setDialogMsg(R.string.loadProgress)) {
                    @Override
                    protected void onSuccess(Object bean) {
                        T.showLong(getString(R.string.email_succeed));
                        JumpUtils.exitActivity(ForgetPasswordActivity.this);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
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
