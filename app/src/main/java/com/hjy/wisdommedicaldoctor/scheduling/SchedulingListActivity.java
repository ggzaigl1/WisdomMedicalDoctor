package com.hjy.wisdommedicaldoctor.scheduling;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.base.popupwindow.CommonPopupWindow;
import com.fy.baselibrary.base.popupwindow.NicePopup;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.rv.divider.ListItemDecoration;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.AnimUtils;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.bean.SchedulingListBean;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.DocConstant;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 排班列表 activity
 * Created by fangs on 2018/7/31 09:47.
 */
public class SchedulingListActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.imgTimeOrderingPull)
    ImageView imgTimeOrderingPull;

    @BindView(R.id.imgFiltratePull)
    ImageView imgFiltratePull;


    @BindView(R.id.rvScheduling)
    RecyclerView rvScheduling;
    SchedulingListAdapter mAdapter;
    private int status = 1;//1：启用；2：停用
    private int sort = 1; //1：升序；2：降序


    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.scheduling_activity_list;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        initRv();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListSchedule(status, sort);
    }

    private void getListSchedule(int status, int sort) {
        ArrayMap<String, Object> objectMap = new ArrayMap<>();
        objectMap.put("docId", SpfUtils.getSpfSaveInt(DocConstant.userId));
        objectMap.put("status", status);
        objectMap.put("sort", sort);
        RequestUtils.create(ApiService.class)
                .getListSchedule(objectMap)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(new NetCallBack<SchedulingListBean>(new NetDialog().init(this).setDialogMsg(R.string.data_loading)) {
                    @Override
                    protected void onSuccess(SchedulingListBean t) {
                        mAdapter.setNewData(t.getRows());
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        View menuLayout = menu.findItem(R.id.menuSchedule).getActionView();
        menuLayout.setOnClickListener(this);

        AppCompatImageView imgMenu = menuLayout.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.svg_add);
        return true;
    }


    private void initRv() {
        mAdapter = new SchedulingListAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SchedulingListBean.RowsBean bean = (SchedulingListBean.RowsBean) adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("id", bean.getId());
            bundle.putInt("status", bean.getStatus());
            bundle.putString("startDate", bean.getStartDate().substring(0, 10));
            bundle.putString("endDate", bean.getEndDate().substring(0, 10));

            JumpUtils.jump(SchedulingListActivity.this, SchedulingDetailsActivity.class, bundle);
        });
        rvScheduling.setLayoutManager(new LinearLayoutManager(this));
        rvScheduling.addItemDecoration(new ListItemDecoration.Builder().setDraw(false).setmSpace(R.dimen.dp_10).create(this));
        rvScheduling.setAdapter(mAdapter);
    }


    @OnClick({R.id.llTimeOrdering, R.id.llFiltrate})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuLayout://菜单按钮
                JumpUtils.jump(this, SchedulingNewActivity.class, null);
                break;
            case R.id.llTimeOrdering://按排班开始时间排序
                showTimeOrderPopup(v, imgTimeOrderingPull, false);
                break;
            case R.id.llFiltrate://筛选
                showTimeOrderPopup(v, imgFiltratePull, true);
                break;
            case R.id.popupLayout:
                popupWindow.dismiss();
                break;
        }
    }

    CommonPopupWindow popupWindow;

    private void showTimeOrderPopup(View view, ImageView img, boolean isFiltrate) {
//        int[] position = new int[2];
//        view.getLocationOnScreen(position);
//        int popupHeight = ScreenUtils.getScreenHeight() - position[1] - view.getHeight();

        AnimUtils.doArrowAnim(img, false);

        popupWindow = NicePopup.Builder.init()
                .setLayoutId(R.layout.scheduling_popup_time_order)
                .setConvertListener(holder -> {
                    holder.setOnClickListener(R.id.popupLayout, SchedulingListActivity.this);

                    TextView popupBtnOne = holder.getView(R.id.popupBtnOne);
                    TextView popupBtnTwo = holder.getView(R.id.popupBtnTwo);
                    if (isFiltrate) {
                        //启动 || 停止
                        popupBtnOne.setGravity(Gravity.RIGHT);
                        popupBtnOne.setText("启用中");

                        popupBtnOne.setOnClickListener(v -> {

                            status = 1;
                            getListSchedule(status, sort);
                            popupWindow.dismiss();
                        });
                        popupBtnTwo.setOnClickListener(v -> {
                            status = 2;
                            getListSchedule(status, sort);
                            popupWindow.dismiss();
                        });

                        popupBtnTwo.setGravity(Gravity.RIGHT);
                        popupBtnTwo.setText("停用中");
                    } else {
                        //时间排序
                        popupBtnOne.setOnClickListener(v -> {//降序
                            sort = 2;
                            getListSchedule(status, sort);
                            popupWindow.dismiss();
                        });
                        popupBtnTwo.setOnClickListener(v -> {//升序
                            sort = 1;
                            getListSchedule(status, sort);
                            popupWindow.dismiss();
                        });
                    }
                }).create()
                .setDismissListner(() -> AnimUtils.doArrowAnim(img, true))
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, -1)
                .setAnim(R.style.AnimTop)
                .setOutside(true)
                .setBgAlpha(1f)
                .onCreateView(this);

        popupWindow.showAsDropDown(view);

//        popupWindow.showAtLocation(findViewById(android.R.id.content),
//                Gravity.NO_GRAVITY,
//                0,
//                position[1] + view.getHeight());
    }
}
