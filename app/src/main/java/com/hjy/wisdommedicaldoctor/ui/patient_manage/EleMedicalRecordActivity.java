package com.hjy.wisdommedicaldoctor.ui.patient_manage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.EleMedicalRecordAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 电子病历
 * Created by Stefan on 2018/7/27
 */

public class EleMedicalRecordActivity extends AppCompatActivity implements IBaseActivity{
    @BindView(R.id.rv_mList)
    RecyclerView rv_mList;

    EleMedicalRecordAdapter adapter;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_ele_medical_record;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        setDummy();
        rv_mList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new EleMedicalRecordAdapter(list);
        rv_mList.setAdapter(adapter);
    }

    ArrayList list;
    private void setDummy() {
        list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            list.add("");
        }
    }
}
