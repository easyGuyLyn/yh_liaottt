package com.nwf.sports.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * <p>类描述： FragmentAdapter
 * <p>创建人：Simon
 * <p>创建时间：2019-03-28
 * <p>修改人：Simon
 * <p>修改时间：2019-03-28
 * <p>修改备注：
 **/
public class AdapterFragment extends FragmentPagerAdapter {
    List<Fragment> mFragments = new ArrayList<>();

    public AdapterFragment(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments.clear();
        mFragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = mFragments.get(position).toString();
//            return title.toString();
//        }
}