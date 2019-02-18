package com.hjy.wisdommedicaldoctor.ui.patient_manage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.MedicalRecordAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 就诊记录
 * Created by Stefan on 2018/7/27 15:02.
 */

public class MedicalRecordActivity extends AppCompatActivity implements IBaseActivity {
    @BindView(R.id.rv_mList)
    RecyclerView rv_mList;

    MedicalRecordAdapter adapter;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_medical_record;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        setDummy();
        rv_mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicalRecordAdapter(list);
        rv_mList.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            JumpUtils.jump(MedicalRecordActivity.this, DiaDetailActivity.class, null);
        });
    }

    ArrayList list;

    private void setDummy() {
        list = new ArrayList();
        for (int i = 0; i < 6; i++) {
            list.add("");
        }
    }
}
