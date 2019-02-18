package com.hjy.wisdommedicaldoctor.gratuitous;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 义诊设置
 * Created by fangs on 2018/8/29 09:33.
 */
public class GratuitousSettingActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener{

    @BindView(R.id.switchInquisition)
    Switch switchInquisition;
    @BindView(R.id.tvSettingScheduling)
    TextView tvSettingScheduling;
    @BindView(R.id.tvGratuitousNum)
    TextView tvGratuitousNum;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.gratuitous_setting_actvity;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        Drawable rightDraw = TintUtils.getDrawable(R.drawable.svg_arrow_right, 1);
        TintUtils.setTxtIconLocal(tvSettingScheduling, rightDraw, 2);
    }

    @OnClick({R.id.llGratuitousNum, R.id.tvSettingScheduling})
    @ClickFilter
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llGratuitousNum://义诊次数
                showEditDialog();
                break;
            case R.id.tvSettingScheduling://进入义诊排班
                JumpUtils.jump(this, GratuitousSchedulingActivity.class, null);
                break;
            case R.id.btnOk://弹窗确定按钮监听

                break;
        }
    }

    private void showEditDialog(){
        NiceDialog.init()
                .setLayoutId(R.layout.edit_dialog)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        EditText editGratuitousNum = holder.getView(R.id.editNum);
                        editGratuitousNum.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void afterTextChanged(Editable s) {
                                if (s.length() > 1 && s.charAt(0) == '0') {
                                    try {
                                        Integer integer = Integer.valueOf(s.toString());
                                        editGratuitousNum.setText(integer.toString());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                        holder.setText(R.id.tvTitle, R.string.gratuitousNum);
                        holder.setText(R.id.tvUnit, R.string.num);
                        holder.setOnClickListener(R.id.btnOk, v -> {
                            String gratuitousNum = editGratuitousNum.getText().toString().trim();
                            if (TextUtils.isEmpty(gratuitousNum)){
                                T.showLong(R.string.tipsNum);
                            } else {
                                tvGratuitousNum.setText(ResUtils.getReplaceStr(R.string.replaceNum, gratuitousNum));
                                dialog.dismiss(false);
                            }
                        });
                    }
                }).setHide(true)
                .setWidthPercent(75)
                .show(getSupportFragmentManager());
    }
}
