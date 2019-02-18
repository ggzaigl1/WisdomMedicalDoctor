package com.hjy.wisdommedicaldoctor.ui.charge.fragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.ChargeSettingAdapter;
import com.hjy.wisdommedicaldoctor.ui.charge.fragment.activity.fragment.ChargeSettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 初夏小溪 on 2018/8/24 0024.
 * 费用设置
 */

public class ChargeSettingActivity extends AppCompatActivity implements IBaseActivity{

    @BindView(R.id.tabLremind)
    TabLayout tabLremind;
    @BindView(R.id.vpRemind)
    ViewPager vpRemind;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_charge_setting;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        initViewPager();
    }

    private void initViewPager() {
        String[] titles = {"视频问诊", "语音问诊", "图文问诊","线下挂号"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChargeSettingFragment());
        fragments.add(new ChargeSettingFragment());
        fragments.add(new ChargeSettingFragment());
        fragments.add(new ChargeSettingFragment());

        vpRemind.setAdapter(new ChargeSettingAdapter(this, fragments, titles));
        tabLremind.setupWithViewPager(vpRemind);

    }
}
