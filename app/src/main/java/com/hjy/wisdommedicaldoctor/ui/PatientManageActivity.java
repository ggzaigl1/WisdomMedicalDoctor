package com.hjy.wisdommedicaldoctor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.PatientManageAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 患者管理 Activity
 * Created by Stefan on 2018/7/24 9:30.
 */
public class PatientManageActivity extends AppCompatActivity implements IBaseActivity {
    //    @BindView(R.id.rv_list)
//    RecyclerView rv_list;
//    @BindView(R.id.tv_title)
//    TextView tv_title;
    PatientManageAdapter adapter;
    ArrayList list;

    private void setDummy() {
        list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
    }

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.activity_patient_manage;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        RecyclerView rv_list = findViewById(R.id.rv_list);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("患者管理");
        setDummy();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientManageAdapter(list);
        rv_list.setAdapter(adapter);
    }
}
