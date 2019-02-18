package com.hjy.wisdommedicaldoctor.ui.myorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.utils.L;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.OrderBean;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;

import butterknife.BindView;

/**
 * 订单详情
 * Created by 初夏小溪 on 2018/7/27 0027.
 */
public class OrderParticularsActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_servicePrice)
    TextView mTvServicePrice;
    @BindView(R.id.tv_reserve_time)
    TextView mTvReserveTime;
    @BindView(R.id.tv_orderStatus)
    TextView mTvOrderStatus;
    @BindView(R.id.tv_type)
    TextView mTvType;

    private OrderBean mOrder;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_order_particular;
    }

    @StatusBar(statusColor = R.color.statusBarColor, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        mOrder = (OrderBean) getIntent().getSerializableExtra("order");
        L.d(mOrder.getDiseaseName() + "");
        toolbar.setNavigationIcon(TintUtils.getTintDrawable(R.drawable.svg_arrow_left, 1, R.color.white));
        toolbar.setBackgroundColor(ResUtils.getColor(R.color.titleBar));
        toolbarTitle.setTextColor(ResUtils.getColor(R.color.white));
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        mTvName.setText(mOrder.getVisitMemberName());
        String gender = mOrder.getGender();
        switch (gender) {
            case "0":
                mTvSex.setText("女");
                break;
            case "1":
                mTvSex.setText("男");
                break;
        }
        mTvAge.setText(mOrder.getAge() + "岁");
        mTvServicePrice.setText(mOrder.getServicePrice());
        String position = TextUtils.isEmpty(mOrder.getReserveTime()) ? "0" : mOrder.getReserveTime();
        mTvReserveTime.setText(mOrder.getReserveDate() + " " + DocConstant.time[Integer.parseInt(position)]);

        int serviceId = mOrder.getServiceId();
        switch (serviceId) {
            case 1:
                mTvType.setText("图文咨询");
                break;
            case 2:
                mTvType.setText("视频咨询");
                break;
            case 3:
                mTvType.setText("语音咨询");
                break;
        }

        int orderStatus = mOrder.getOrderStatus();
        switch (orderStatus) {
            case 0:
                mTvOrderStatus.setText("已删除");
                break;
            case 1:
                mTvOrderStatus.setText("已取消");
                break;
            case 2:
                mTvOrderStatus.setText("未支付");
                break;
            case 3:
                mTvOrderStatus.setText("已支付");
                break;
            case 4:
                mTvOrderStatus.setText("已完成");
                break;
            case 5:
                mTvOrderStatus.setText("已回复");
                break;
            case 6:
                mTvOrderStatus.setText("未就诊");
                break;
            case 8:
                mTvOrderStatus.setText("已完成");
                break;
        }
    }
}
