package com.hjy.wisdommedicaldoctor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.hjy.wisdommedicaldoctor.R;

/**
 * Created by 初夏小溪 on 2018/8/6 0006.
 * 拍摄标准
 */
public class ShootStandardActivity extends AppCompatActivity implements IBaseActivity {

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_shoot_standard;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

    }

}
