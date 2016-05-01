package com.yabi.yabiuserandroid.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.fragments.LoginFragment;
import com.yabi.yabiuserandroid.interfaces.ToolbarInterface;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ToolbarInterface {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, LoginFragment.newInstance()).commit();
    }
    // Toolbar Interface Starts

    @Override
    public void toggleToolbarVisibility(boolean value) {
        if (value) {
            getSupportActionBar().show();

        } else {
            getSupportActionBar().hide();

        }
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {
        getSupportActionBar().setTitle(toolbarTitle);
        setActionBarFont();

    }

    private void setActionBarFont() {
        SpannableString s = new SpannableString(getTitle());
        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) actionBar.setTitle(s);
    }

    @Override
    public void setToolbarTheme(int toolbarTheme) {
//        toolbar.setPopupTheme(toolbarTheme);
    }

    @Override
    public void setHomeUpEnabled(boolean value) {
         getSupportActionBar().setDisplayHomeAsUpEnabled(value);
    }

    @Override
    public void setToolbarTitleTextColor(int toolbarTitleTextColor) {
//        toolbar.setTitleTextColor(toolbarTitleTextColor);
    }

    @Override
    public void setHomeUpIndicator(int homeUpIndicator) {
        getSupportActionBar().setHomeAsUpIndicator(homeUpIndicator);
    }

    @Override
    public void setToolbarMenu(Menu menu) {

    }

    @Override
    public void setToolbarBackgroundColor(int color) {
//        toolbar.setBackgroundColor(color);
    }

    @Override
    public void showMenuItems(boolean value) {

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


