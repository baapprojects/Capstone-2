package com.yabi.yabiuserandroid.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.activities.HomeActivity;
import com.yabi.yabiuserandroid.activities.LoginActivity;
import com.yabi.yabiuserandroid.interfaces.ToolbarInterface;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontTextView;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SplashFragment extends Fragment {

    @Bind(R.id.img_logo)
    ImageView imgLogo;
    @Bind(R.id.text_restaurant_name)
    CustomFontTextView textRestaurantName;
    @Bind(R.id.text_setting_up_things)
    CustomFontTextView textSettingUpThings;
    private Animation animation2;
    ToolbarInterface toolbarInterface;
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView =  (ViewGroup)inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this,rootView);
        animate();
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    if (new SharedPrefUtils(getActivity()).getUserPhone() != null)
                        intent = new Intent(getActivity(), HomeActivity.class);
                    else
                        intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }, getResources().getInteger(R.integer.fragment_splash_timer_value));
        }catch(Exception e)
        {

        }
        return rootView;
    }
    private void animate() {
        try {
            imgLogo.setVisibility(View.VISIBLE);

            animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            animation2.setDuration(getResources().getInteger(R.integer.default_animation_value));
            animation2.restrictDuration(getResources().getInteger(R.integer.default_animation_value));
            animation2.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    textRestaurantName.setVisibility(View.VISIBLE);
                    textSettingUpThings.setVisibility(View.VISIBLE);

                }
            });
            animation2.scaleCurrentDuration(1);

            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_center);
            animation1.setDuration(getResources().getInteger(R.integer.default_animation_value));
            animation1.restrictDuration(getResources().getInteger(R.integer.default_animation_value));
            animation1.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgLogo.setVisibility(View.VISIBLE);
                    textRestaurantName.setVisibility(View.VISIBLE);
                    textRestaurantName.startAnimation(animation2);
                    textSettingUpThings.setVisibility(View.VISIBLE);
                    textSettingUpThings.startAnimation(animation2);
                }
            });
            animation1.scaleCurrentDuration(getResources().getInteger(R.integer.integer_1));
            imgLogo.startAnimation(animation1);
            imgLogo.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            imgLogo.setVisibility(View.VISIBLE);
            imgLogo.setVisibility(View.VISIBLE);
            textRestaurantName.setVisibility(View.VISIBLE);
            textSettingUpThings.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            toolbarInterface = (ToolbarInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarProperties();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        toolbarInterface = null;
    }

    public void setToolbarProperties()
    {
        if(null==toolbarInterface)
            return;
        toolbarInterface.toggleToolbarVisibility(false);
        toolbarInterface.setHomeUpEnabled(false);
    }



}
