package com.hjy.wisdommedicaldoctor.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;
import com.hjy.wisdommedicaldoctor.login.LoginActivity;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置-修改密码 Activity
 * Created by Stefan on 2018/7/9 14:04.
 */
public class ModifyPsActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.iv_old_eye)
    ImageView iv_old_eye;
    @BindView(R.id.iv_new_eye)
    ImageView iv_new_eye;
    @BindView(R.id.iv_sureNew_eye)
    ImageView iv_sureNew_eye;
    @BindView(R.id.et_OldPs)
    EditText et_OldPs;
    @BindView(R.id.et_NewPs)
    EditText et_NewPs;
    @BindView(R.id.et_sure_newPs)
    EditText et_sure_newPs;

    boolean flagA = true;
    boolean flagB = true;
    boolean flagC = true;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_modify_ps;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
        setInitialState();
    }

    private void setInitialState() {
        iv_old_eye.setBackgroundResource(R.mipmap.login_password_off);
        iv_new_eye.setBackgroundResource(R.mipmap.login_password_off);
        iv_sureNew_eye.setBackgroundResource(R.mipmap.login_password_off);
    }

    @OnClick({R.id.iv_old_eye, R.id.iv_new_eye, R.id.iv_sureNew_eye, R.id.bt_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_old_eye:
                if (flagA) {
                    iv_old_eye.setBackgroundResource(R.mipmap.login_password_on);
                    //选中显示密码
                    et_OldPs.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //把光标设置在文字结尾
                    et_OldPs.setSelection(et_OldPs.getText().length());
                    flagA = !flagA;
                } else {
                    iv_old_eye.setBackgroundResource(R.mipmap.login_password_off);
                    //隐藏密码
                    et_OldPs.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //把光标设置在文字结尾
                    et_OldPs.setSelection(et_OldPs.getText().length());
                    flagA = !flagA;
                }
                break;
            case R.id.iv_new_eye:
                if (flagB) {
                    iv_new_eye.setBackgroundResource(R.mipmap.login_password_on);
                    et_NewPs.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_NewPs.setSelection(et_NewPs.getText().length());
                    flagB = !flagB;
                } else {
                    iv_new_eye.setBackgroundResource(R.mipmap.login_password_off);
                    et_NewPs.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_NewPs.setSelection(et_NewPs.getText().length());
                    flagB = !flagB;
                }

                break;
            case R.id.iv_sureNew_eye:
                if (flagC) {
                    iv_sureNew_eye.setBackgroundResource(R.mipmap.login_password_on);
                    et_sure_newPs.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_sure_newPs.setSelection(et_sure_newPs.getText().length());
                    flagC = !flagC;
                } else {
                    iv_sureNew_eye.setBackgroundResource(R.mipmap.login_password_off);
                    et_sure_newPs.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_sure_newPs.setSelection(et_sure_newPs.getText().length());
                    flagC = !flagC;
                }
                break;
            case R.id.bt_modify:
                GetSave();
                break;
        }
    }


    /**
     * 修改密码接口
     */
    private void GetSave() {
        if (et_OldPs.getText().toString().equals("")) {
            T.showShort("旧密码不能为空");
            return;
        } else if (et_NewPs.getText().toString().equals("")) {
            T.showShort("新密码不能为空");
            return;
        } else if (et_sure_newPs.getText().toString().equals("")) {
            T.showShort("新密码不能为空");
            return;
        } else if (!et_NewPs.getText().toString().equals(et_sure_newPs.getText().toString())) {
            T.showShort("两次新密码不一致");
            return;
        }
        RequestUtils.create(ApiService.class)
                .updatePwd(SpfUtils.getSpfSaveStr(DocConstant.username), et_OldPs.getText().toString().trim(), et_sure_newPs.getText().toString().trim())
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<Object>(new NetDialog().init(this).setDialogMsg(R.string.loading_modify)) {
                    @Override
                    protected void onSuccess(Object t) {
                        T.showLong(getString(R.string.update_password));
                        Intent intent = new Intent(ModifyPsActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    protected void updataLayout(int flag) {
                    }
                });
    }
}
