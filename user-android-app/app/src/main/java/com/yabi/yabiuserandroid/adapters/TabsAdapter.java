package com.yabi.yabiuserandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yabi.yabiuserandroid.fragments.MerchantListingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogeshmadaan on 29/03/16.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    public static final int FIRST_TAB = 0;
    public static final int SECOND_TAB = 1;
    public static final int LAST_TAB = 1;
    public static final int TOTAL_TABS = LAST_TAB +1;
    //    private final boolean useFreshMenu;
    private List<String> tabsTitle = null;
    public TabsAdapter(FragmentManager fm, List<String> tabsTitle) {
        super(fm);
        this.tabsTitle = new ArrayList<>();
        this.tabsTitle.addAll(tabsTitle);
    }


    @Override
    public Fragment getItem(int position) {

                return MerchantListingFragment.newInstance(position);
         }

    @Override
    public int getCount() {
        return tabsTitle.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitle.get(position);
    }
}
