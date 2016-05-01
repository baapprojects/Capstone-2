package com.yabi.yabiuserandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.fragments.LoginImageFragment;

/**
 * Created by yogeshmadaan on 15/01/16.
 */
public class LoginImagesTabAdapter extends FragmentStatePagerAdapter {
    private int[] contents = {R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5};

    public LoginImagesTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return LoginImageFragment.newInstance(contents[position]);
    }

    @Override
    public int getCount() {
        return contents.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}
