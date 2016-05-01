package com.yabi.yabiuserandroid.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yabi.yabiuserandroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FaqActivityFragment extends Fragment {

    public FaqActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }
}
