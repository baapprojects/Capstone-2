package com.yabi.yabiuserandroid.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yabi.yabiuserandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginImageFragment extends Fragment {
    private static final String KEY_CONTENT = "LoginImageFragment:Content";
    int content;


    public LoginImageFragment() {
        // Required empty public constructor
    }

    public static LoginImageFragment newInstance(int content){
        LoginImageFragment loginFragment =  new LoginImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CONTENT,content);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            content = savedInstanceState.getInt(KEY_CONTENT);
        }
        else if((getArguments() != null) && getArguments().containsKey(KEY_CONTENT)) {
            content = getArguments().getInt(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(getResources().getColor(R.color.splash_screen_color_primary));
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setImageResource(content);
        return imageView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONTENT, content);
    }

}
