package com.yabi.yabiuserandroid.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.adapters.TabsAdapter;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
    }

    public void initViews()
    {
        setUpViewPager();
    }

    public void setUpViewPager()
    {
        if(null == viewPager)
            return;
        List<String> tabsList = new ArrayList<>();
        tabsList.add(getResources().getString(R.string.string_online_deals));
        tabsList.add(getResources().getString(R.string.string_offline_deals));
        viewPager.setAdapter(new TabsAdapter(getActivity().getSupportFragmentManager(), tabsList));
        tabLayout.setupWithViewPager(viewPager);
        applyFontedTab(getActivity(), viewPager, tabLayout);
        viewPager.setOffscreenPageLimit(tabsList.size());
    }
    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            CustomFontTextView tv = (CustomFontTextView) activity.getLayoutInflater().inflate(R.layout.layout_tabs, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
