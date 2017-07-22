package com.n22.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.n22.pages.LocalFragment;
import com.n22.pages.NetFragment;

/**
 * Created by zhanxiaolin-n22 on 2017/7/22.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    private String[] mTab = new String[]{"未上传", "已上传"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LocalFragment.newInstance();//纯文页面
        } else if (position == 1) {
            return NetFragment.newInstance();//纯图页面
        }
        return LocalFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
