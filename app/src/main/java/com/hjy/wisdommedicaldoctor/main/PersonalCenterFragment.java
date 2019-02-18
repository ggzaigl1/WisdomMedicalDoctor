package com.hjy.wisdommedicaldoctor.main;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.gratuitous.GratuitousSettingActivity;
import com.hjy.wisdommedicaldoctor.scheduling.SchedulingListActivity;
import com.hjy.wisdommedicaldoctor.ui.setting.PersonInfoActivity;
import com.hjy.wisdommedicaldoctor.ui.setting.SetUpActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 * Created by fangs on 2018/7/24 11:43.
 */
public class PersonalCenterFragment extends BaseFragment {

    @BindView(R.id.tv_docName)
    TextView tv_docName;
    @BindView(R.id.tv_professional)
    TextView tv_professional;
    @BindView(R.id.iv_header)
    ImageView iv_header;


    public static PersonalCenterFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, params);
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void baseInit() {

    }

    @Override
    public void onResume() {
        super.onResume();
        tv_docName.setText(SpfUtils.getSpfSaveStr(DocConstant.docName));
        tv_professional.setText(SpfUtils.getSpfSaveStr(DocConstant.hospitalName) + SpfUtils.getSpfSaveStr(DocConstant.departmentName)
                + SpfUtils.getSpfSaveStr(DocConstant.titleName));
        if (!TextUtils.isEmpty(SpfUtils.getSpfSaveStr(DocConstant.DocPhotoUrl))) {
            loadCircularBead(mContext, ApiService.BASE_PIC_URL + SpfUtils.getSpfSaveStr(DocConstant.DocPhotoUrl), iv_header);
        }
    }

    public static void loadCircularBead(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .circleCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @OnClick({R.id.tv_scheduling, R.id.tv_suspend, R.id.tv_cost, R.id.tv_gratuitous, R.id.tv_perInfo, R.id.tv_credential, R.id.tv_evaluate, R.id.tv_setting,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_scheduling:
                //排班
                JumpUtils.jump(mContext, SchedulingListActivity.class, null);
                break;
            case R.id.tv_suspend:
                //停诊
                T.showLong("功能开发中...");
//                JumpUtils.jump(mContext, StopdiagnosisPublishActivity.class, null);
                break;
            case R.id.tv_cost:
                //费用
                T.showLong("功能开发中...");
//                JumpUtils.jump(mContext, ChargeSettingActivity.class, null);
                break;
            case R.id.tv_gratuitous:
                //义诊
                T.showLong("功能开发中...");
//                JumpUtils.jump(mContext, GratuitousSettingActivity.class, null);
                break;
            case R.id.tv_perInfo:
                //个人资料
                JumpUtils.jump(mContext, PersonInfoActivity.class, null);
                break;
            case R.id.tv_credential:
                //证书管理
                T.showLong("功能开发中...");
//                JumpUtils.jump(mContext, CertificateManagementActivity.class, null);
                break;
            case R.id.tv_evaluate:
                //评价
                T.showLong("功能开发中...");
//                JumpUtils.jump(mContext, EvaluationDoctorActivity.class, null);
                break;
            case R.id.tv_setting:
                //设置
                JumpUtils.jump(mContext, SetUpActivity.class, null);
                break;
        }
    }

}
