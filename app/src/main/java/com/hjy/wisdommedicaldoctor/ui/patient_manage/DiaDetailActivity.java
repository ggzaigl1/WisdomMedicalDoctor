package com.hjy.wisdommedicaldoctor.ui.patient_manage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.hjy.wisdommedicaldoctor.R;

import butterknife.BindView;

/**
 * 诊断详情
 * Created by Stefan on 2018/7/27 14:32.
 */
public class DiaDetailActivity extends AppCompatActivity implements IBaseActivity{

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_dia_detail;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
    }
}
