package com.hjy.wisdommedicaldoctor.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.Validator;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 初夏小溪 on 2018/9/12 0012.
 * 公共跳转编辑
 */
public class NewUserEditActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.id_editor_detail_name)
    EditText mEditText;

    private int mType;
    private String mString;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_new_user_edit;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        View menuLayout = menu.findItem(R.id.menuSchedule).getActionView();
        menuLayout.setOnClickListener(this);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        mType = getIntent().getIntExtra("type", -1);

        switch (mType) {
            case R.id.Ll_Nick:
                toolbarTitle.setText(getString(R.string.nickname));
                mEditText.setHint(getString(R.string.please_nickname));
                mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)}); //最大输入长度
                break;
            case R.id.Ll_name:
                toolbarTitle.setText(getString(R.string.person_name));
                String personName = getIntent().getStringExtra("personName");
                if (null != personName) {
                    mEditText.setText(personName);
                    mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)}); //最大输入长度
                    mEditText.setSelection(personName.length());
                }
                break;
            case R.id.Ll_phone:
                toolbarTitle.setText(getString(R.string.add_phone));
                String classPhone = getIntent().getStringExtra("classPhone");
                if (null != classPhone) {
                    mEditText.setText(classPhone);
                    mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
                    mEditText.setSelection(classPhone.length());
                }
                break;
            case R.id.Ll_docInfo:
                toolbarTitle.setText(R.string.personal_profile);
                String classAddress = getIntent().getStringExtra("classAddress");
                if (null != classAddress) {
                    mEditText.setText(classAddress);
                    mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)}); //最大输入长度
                    mEditText.setSelection(classAddress.length());
                }
                break;
            case R.id.Ll_goodAt:
                toolbarTitle.setText(getString(R.string.good_at));
                String goodAt = getIntent().getStringExtra("goodAt");
                if (null != goodAt) {
                    mEditText.setText(goodAt);
                    mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)}); //最大输入长度
                    mEditText.setSelection(goodAt.length());
                }
                break;
        }
    }

    @OnClick({})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuLayout://菜单按钮
                setResult();
                break;
        }
    }

    private void setResult(){
        mString = mEditText.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("trim", mString);

        switch (mType) {
            case R.id.Ll_Nick://昵称
                break;
            case R.id.Ll_name://姓名
                break;
            case R.id.Ll_phone://手机号
                if (!Validator.isMobile(mString)) {
                    T.showShort(R.string.please_enter_phone_number);
                    return;
                }
                break;
            case R.id.Ll_docInfo://个人简介
                break;
            case R.id.Ll_goodAt://擅长
                break;
        }

        JumpUtils.jumpResult(this, bundle);
    }
}
