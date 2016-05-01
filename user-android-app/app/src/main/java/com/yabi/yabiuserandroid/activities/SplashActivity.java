package com.yabi.yabiuserandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.fragments.SplashFragment;
import com.yabi.yabiuserandroid.interfaces.ToolbarInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements ToolbarInterface{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, SplashFragment.newInstance()).commit();
        toggleToolbarVisibility(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    }
    @Override
    public void setToolbarTheme(int toolbarTheme) {
        toolbar.setPopupTheme(toolbarTheme);
    }

    @Override
    public void setHomeUpEnabled(boolean value) {
        // getSupportActionBar().setDisplayHomeAsUpEnabled(value);
    }

    @Override
    public void setToolbarTitleTextColor(int toolbarTitleTextColor) {
        toolbar.setTitleTextColor(toolbarTitleTextColor);
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
        toolbar.setBackgroundColor(color);
    }

    @Override
    public void showMenuItems(boolean value) {

    }

}
