package com.yabi.yabiuserandroid.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.Drawer;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.fragments.ProfileFragment;
import com.yabi.yabiuserandroid.ui.uiutils.DrawerUtil;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    Drawer drawer;
    SharedPrefUtils sharedPrefUtils;
    @Bind(R.id.img_profile)
    ImageView imgProfile;
    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initViews();
        getSupportActionBar().setTitle("Profile");


    }
    public void initViews()
    {
        sharedPrefUtils = new SharedPrefUtils(getApplicationContext());
        collapsingToolbarLayout.setTitle(sharedPrefUtils.getUserName());
        drawer = DrawerUtil.setUpDrawer(this,toolbar,2);
        drawer.setSelectionAtPosition(2);
        Glide.with(this).load(sharedPrefUtils.getUserProfilePic()).asBitmap().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imgProfile);
        Glide.with(this).load(sharedPrefUtils.getUserCoverPic()).asBitmap().placeholder(R.drawable.drawer).error(R.drawable.drawer).into(backdrop);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, ProfileFragment.newInstance()).commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        drawer.setSelectionAtPosition(2);
    }
    @Override
    protected void onStart() {
        super.onStart();
        drawer.setSelectionAtPosition(2);
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            int count = this.getSupportFragmentManager().getBackStackEntryCount();
            Fragment frag = getSupportFragmentManager().getFragments().get(count > 0 ? count - 1 : count);
            frag.onResume();
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
