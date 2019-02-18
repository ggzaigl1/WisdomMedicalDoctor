package com.hjy.wisdommedicaldoctor.ui.patient_manage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.TimeUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.PatientListBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 患者详情
 * Created by Stefan on 2018/7/25 10:58.
 */

public class PatientDetailActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.iv_header)
    AppCompatImageView mIvHeader;
    @BindView(R.id.tv_docName)
    TextView mTvDocName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.rl_record)
    RelativeLayout mRlRecord;
    @BindView(R.id.tv_arrow)
    AppCompatImageView mTvArrow;
    @BindView(R.id.rl_case)
    RelativeLayout mRlCase;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_Email)
    TextView mTvEmail;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_number)
    TextView mTvNumber;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_patient_detail;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        PatientListBean patientBean = (PatientListBean) getIntent().getSerializableExtra("patientBean");
        mTvDocName.setText(patientBean.getMemberName());
        int gender = patientBean.getGender();
        switch (gender) {
            case 0:
                mTvSex.setText("女");
                mIvHeader.setBackgroundResource(R.mipmap.icon_woman);
                break;
            case 1:
                mTvSex.setText("男");
                mIvHeader.setBackgroundResource(R.mipmap.icon_man);
                break;
        }
        int age = TimeUtils.calculationAge(patientBean.getBirthday(), "yyyy-MM-dd");
        mTvAge.setText(age + "岁");
        mTvPhone.setText(patientBean.getMobile());
        mTvNumber.setText(patientBean.getIdNumber());
        mTvAddress.setText(patientBean.getProvince() + patientBean.getCity() + patientBean.getDistrict() + patientBean.getStreet());
        if (!TextUtils.isEmpty(patientBean.getEmail())) {
            mTvEmail.setText(patientBean.getEmail());
        }

    }

    @OnClick({R.id.rl_record, R.id.rl_case})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_record://就诊记录
                JumpUtils.jump(this, MedicalRecordActivity.class, null);
                break;
            case R.id.rl_case://电子病历
                JumpUtils.jump(this, EleMedicalRecordActivity.class, null);
                break;
        }
    }
}
