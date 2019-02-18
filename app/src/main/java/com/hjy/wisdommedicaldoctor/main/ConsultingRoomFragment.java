package com.hjy.wisdommedicaldoctor.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.utils.ResUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.main.ConsultingRoomListFragment.ImageTextRoomFragment;
import com.hjy.wisdommedicaldoctor.main.ConsultingRoomListFragment.VideoTextRoomFragment;
import com.hjy.wisdommedicaldoctor.main.ConsultingRoomListFragment.VoiceTextRoomFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 診室
 * Created by fangs on 2018/7/24 11:46.
 */
public class ConsultingRoomFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.llConsulting)
    LinearLayout llConsulting;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragments;
    private int mServiceId = 0;


    public static ConsultingRoomFragment newInstance(int params) {

        Bundle bundle = new Bundle();
        bundle.putInt(TAG, params);
        ConsultingRoomFragment fragment = new ConsultingRoomFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_consulting_room;
    }


    @Override
    protected void baseInit() {

        llConsulting.addView(MdStatusBar.createStatusBarView(getActivity(), ResUtils.getColor(R.color.statusBarColor)), 0);

//        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        FragmentManager mFragmentManager = getChildFragmentManager();
        mTabLayout.post(() -> setIndicator(mTabLayout, 10, 10));
        mFragments = new ArrayList<>();
        mFragments.add(new ImageTextRoomFragment());
        mFragments.add(new VideoTextRoomFragment());
        mFragments.add(new VoiceTextRoomFragment());
//        mFragments.add(new OfflineTextRoomFragment());
        mViewPager.setAdapter(new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {

                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getResources().getStringArray(R.array.consulting_room)[position];
            }
        });
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onResume() {
        super.onResume();
        mServiceId = getArguments().getInt("serviceId", 0);
        if (mServiceId == 0) {
            return;
        }
//        mTabLayout.setupWithViewPager(mViewPager);
        switch (mServiceId) {
            case 1: //图文咨询
                mViewPager.setCurrentItem(0);
                mTabLayout.getTabAt(0).select();
                break;
            case 2: //视频咨询
                mViewPager.setCurrentItem(1);
                mTabLayout.getTabAt(1).select();
                break;
            case 3: //语音咨询
                mViewPager.setCurrentItem(2);
                mTabLayout.getTabAt(2).select();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset == 0 || positionOffsetPixels == 0) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @OnClick({})
    @Override
    public void onClick(View view) {
        super.onClick(view);

    }


}
