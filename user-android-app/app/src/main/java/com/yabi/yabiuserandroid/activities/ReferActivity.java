package com.yabi.yabiuserandroid.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.Drawer;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.ui.uiutils.DrawerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReferActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = DrawerUtil.setUpDrawer(this,toolbar,4);
//        drawer.setSelectionAtPosition(4);
    }
    @Override
    protected void onStart() {
        super.onStart();
//        drawer.setSelectionAtPosition(4);
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
