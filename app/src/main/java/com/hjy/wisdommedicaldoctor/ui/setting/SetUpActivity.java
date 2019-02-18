package com.hjy.wisdommedicaldoctor.ui.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.startactivity.StartActivity;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.hx.EMUtils;
import com.hjy.wisdommedicaldoctor.login.LoginActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置 Activity
 * Created by Stefan on 2018/7/9 10:37.
 */
public class SetUpActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.st_btn)
    Switch st_btn;

    @BindView(R.id.tvModifyPs)
    TextView tvModifyPs;
    @BindView(R.id.tvFeedBack)
    TextView tvFeedBack;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_set_up;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        st_btn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                T.showShort("开启推送");
            } else {
                T.showShort("关闭推送");
            }
        });

        Drawable svg_right_arrow = TintUtils.getDrawable(R.drawable.svg_arrow_right, 1);
        TintUtils.setTxtIconLocal(tvModifyPs, svg_right_arrow, 2);
        TintUtils.setTxtIconLocal(tvFeedBack, svg_right_arrow, 2);
    }

    @OnClick({R.id.tvModifyPs, R.id.tvFeedBack, R.id.tvExit})
    @ClickFilter
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvModifyPs:
                JumpUtils.jump(this, ModifyPsActivity.class, null);
                break;
            case R.id.tvFeedBack:
                JumpUtils.jump(this, FeedBackActivity.class, null);
                break;
            case R.id.tvExit:
                showExitDialog();
                break;
        }
    }

    private void showExitDialog() {
        NiceDialog.init().setLayoutId(R.layout.dialog_choose_sex)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        TextView tv_top = holder.getView(R.id.tv_top);
                        TextView tv_down = holder.getView(R.id.tv_down);
                        tv_top.setText(R.string.sureExit);
                        tv_down.setText(R.string.exit);
                        tv_down.setTextColor(ResUtils.getColor(R.color.tipsColor));
                        tv_down.setOnClickListener(v -> {
                            SpfUtils.saveBooleanToSpf(Constant.isLogin, false);
                            JumpUtils.jump(SetUpActivity.this, LoginActivity.class, null);
                            EMUtils.outLoginEM();
                            dialog.dismiss();
                        });

                        holder.setOnClickListener(R.id.tvCancel, v -> dialog.dismiss());
                    }
                }).setWidthPixels(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.BOTTOM)
                .setAnim(R.style.AnimUp)
                .setHide(true)
                .show(getSupportFragmentManager());
    }
}
