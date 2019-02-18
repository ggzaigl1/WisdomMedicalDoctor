package com.hjy.wisdommedicaldoctor.ui.charge.fragment.activity.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.hjy.wisdommedicaldoctor.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 初夏小溪 on 2018/8/24 0024.
 * 費用設置 视频问诊
 */
public class ChargeSettingFragment extends BaseFragment {

    @BindView(R.id.tv_order)
    TextView mTextViewOrder;
    String order;

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_charge_setting;
    }

    @Override
    protected void baseInit() {
        mTextViewOrder.setText(ResUtils.getReplaceStr(R.string.unit, ""));
    }

    @OnClick({R.id.bt_next, R.id.Ll_order})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next://菜单按钮
                JumpUtils.exitActivity(mContext);
                break;
            case R.id.Ll_order:
                NiceDialog();
                break;
        }
    }

    private void NiceDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.edit_dialog)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        holder.setText(R.id.tvTitle, R.string.servicePrice);
                        holder.setText(R.id.tvUnit, ResUtils.getReplaceStr(R.string.unit, ""));
                        EditText et_order = holder.getView(R.id.editNum);
                        et_order.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void afterTextChanged(Editable s) {
                                if (s.length() > 1 && s.charAt(0) == '0') {
                                    try {
                                        Integer integer = Integer.valueOf(s.toString());
                                        et_order.setText(integer.toString());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                        holder.setOnClickListener(R.id.btnOk, v -> {
                            //打开选择,本次允许选择的数量
                            order = et_order.getText().toString().trim();
                            mTextViewOrder.setText(ResUtils.getReplaceStr(R.string.unit, order));
                            dialog.dismiss();
                        });
                    }
                })
                .setHide(true)
                .setDimAmount(0.5f)
                .show(mContext.getSupportFragmentManager());
    }
}
