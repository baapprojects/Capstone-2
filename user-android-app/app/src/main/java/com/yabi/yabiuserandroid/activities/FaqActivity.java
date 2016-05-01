package com.yabi.yabiuserandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.ui.uiutils.DrawerUtil;

public class FaqActivity extends AppCompatActivity {
    Drawer drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = DrawerUtil.setUpDrawer(this,toolbar,7);
//        drawer.setSelectionAtPosition(7);

    }

}
