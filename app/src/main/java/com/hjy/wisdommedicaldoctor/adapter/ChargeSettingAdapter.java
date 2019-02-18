package com.hjy.wisdommedicaldoctor.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 初夏小溪 on 2018/8/24 0024.
 */

public class ChargeSettingAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment = new ArrayList<>();
    private String[] mTitles;
    private Context context;

    public ChargeSettingAdapter(AppCompatActivity context, List<Fragment> fragments, String[] titles) {
        super(context.getSupportFragmentManager());
        this.context = context;
        this.listFragment = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
