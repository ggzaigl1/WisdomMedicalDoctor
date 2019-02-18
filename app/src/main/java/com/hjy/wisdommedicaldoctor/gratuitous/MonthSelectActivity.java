package com.hjy.wisdommedicaldoctor.gratuitous;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.YearRecyclerView;
import com.haibin.calendarview.YearSelectLayout;
import com.hjy.wisdommedicaldoctor.R;

import butterknife.BindView;

/**
 * 月份选择器
 * Created by fangs on 2018/8/29 16:57.
 */
public class MonthSelectActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.yearlayout)
    YearSelectLayout yearlayout;

    int year;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.month_select_activity;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        year = bundle.getInt("year", 2018);
        toolbarTitle.setText(year + "年");

        yearlayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                toolbarTitle.setText(position + yearlayout.getMinYear() + "年");
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        yearlayout.setOnMonthSelectedListener((year, month) -> {
            Calendar calendar = new Calendar(year, month);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("Calendar", calendar);
            JumpUtils.jumpResult(MonthSelectActivity.this, bundle1);
        });
    }


}
