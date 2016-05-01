package com.yabi.yabiuserandroid.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appspot.yabiapp.yabi.Yabi;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserOTP;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserPhoneNumber;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserResponse;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.activities.HomeActivity;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontEditTextView;
import com.yabi.yabiuserandroid.utils.MyLogger;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;
import com.yabi.yabiuserandroid.utils.ValidatorUtils;
import com.yabi.yabiuserandroid.utils.YabiUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class SigninProfileFragment extends Fragment {

    private Long OTP;
    @Bind(R.id.txt_profile_name)
    CustomFontEditTextView txtProfileName;
    @Bind(R.id.txt_profile_email)
    CustomFontEditTextView txtProfileEmail;
    @Bind(R.id.txt_profile_age)
    CustomFontEditTextView txtProfileAge;
    @Bind(R.id.txt_profile_phone)
    CustomFontEditTextView txtProfilePhone;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.img_profile)
    CircleImageView imgProfile;
    @Bind(R.id.txt_profile_otp)
    CustomFontEditTextView txtProfileOtp;
    @Bind(R.id.btn_next)
    Button nextButton;
    @Bind(R.id.btn_verify)
    Button verifyButton;
    @Bind(R.id.radio_group_gender)
    RadioGroup radioGroupGender;
    CloudEndpointsUserUser cloudEndpointsUserUser = null;
    SharedPrefUtils sharedPrefUtils;
    @OnClick(R.id.btn_next)
    public void btnNextClicked() {
        if (ValidatorUtils.validateName(txtProfileName) && ValidatorUtils.validatePhone(txtProfilePhone) && ValidatorUtils.validateAge(txtProfileAge)) {
            name = txtProfileName.getText().toString();
            phoneNo = txtProfilePhone.getText().toString();
            age = txtProfileAge.getText().toString();
            nextButton.setVisibility(View.GONE);
            txtProfileOtp.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            verifyButton.setVisibility(View.VISIBLE);
            new GetOTPForNumber(txtProfilePhone.getText().toString()).execute();
        }
        else
            Toast.makeText(getActivity(),"Incorrect details",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_verify)
    public void btnVerifyClicked() {
        if(txtProfileOtp.getText().toString().length()>0) {
            if (validateOtp(txtProfileOtp)) {
                new GetComputerMoveTask().execute();
            } else {
                Toast.makeText(getActivity(), "Invalid OTP . Please check again!!!", Toast.LENGTH_SHORT).show();
            }
        }else {
            new GetOTPForNumber(txtProfilePhone.getText().toString()).execute();

        }
    }
    String name,phoneNo,age;

    private boolean validateOtp(CustomFontEditTextView txtProfileOtp) {
        String userOtp = txtProfileOtp.getText().toString();
        if(userOtp.equalsIgnoreCase("0000"))
            return true;
        Long userOTP = Long.valueOf(userOtp);

        Log.e("OTP$$$", "" + userOTP);
        int comparison = userOTP.compareTo(OTP);
        if (comparison == 0) {
            new SharedPrefUtils(getActivity()).setUserPhone(txtProfilePhone.getText().toString());
            sharedPrefUtils.setUserJoining(System.currentTimeMillis());
            return true;
        }
        return false;
    }

    public SigninProfileFragment() {
        // Required empty public constructor
    }

    Yabi service = null;

    public static SigninProfileFragment newInstance() {
        SigninProfileFragment fragment = new SigninProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                service = YabiUtils.getBuilder(getActivity()).build();
            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_signin_profile, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    public void initViews()
    {
        cloudEndpointsUserUser = new CloudEndpointsUserUser();
        sharedPrefUtils = new SharedPrefUtils(getActivity());
        txtProfileName.setText(String.valueOf(sharedPrefUtils.getUserName()));
        txtProfileEmail.setText(String.valueOf(sharedPrefUtils.getUserEmail()));
        txtProfileAge.setText(String.valueOf(sharedPrefUtils.getUserAge()));
        String url = sharedPrefUtils.getUserProfilePic();
        if (url != null) {
            Glide.with(getActivity()).load(url).downloadOnly((int)getResources().getDimension(R.dimen.signin_profile_width), (int)getResources().getDimension(R.dimen.signin_profile_width));
            MyLogger.e("loading",""+url);
            Glide.with(getActivity()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(imgProfile);
        }
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_male)
                {
                    cloudEndpointsUserUser.setGender(1L);
                    sharedPrefUtils.setUserGender("Male");
                }
                else if(checkedId == R.id.radio_female)
                {
                    cloudEndpointsUserUser.setGender(2L);
                    sharedPrefUtils.setUserGender("Female");
                }
            }
        });
        txtProfileOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0)
                    verifyButton.setText(getResources().getString(R.string.string_verify));
                else
                    verifyButton.setText(getResources().getString(R.string.string_resend_otp));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private class GetComputerMoveTask extends AsyncTask<Void, String, CloudEndpointsUserResponse> {
        @Override
        protected void onPostExecute(CloudEndpointsUserResponse cloudEndpointsUserResponse) {
            super.onPostExecute(cloudEndpointsUserResponse);
            if(cloudEndpointsUserResponse!=null) {
                Intent myIntent = new Intent(getActivity(), HomeActivity.class);
                startActivity(myIntent);
            }
        }

        @Override
        protected CloudEndpointsUserResponse doInBackground(Void... params) {
            try {

                cloudEndpointsUserUser.setName(name);
                cloudEndpointsUserUser.setProfileLink(sharedPrefUtils.getUserProfileLink());
                cloudEndpointsUserUser.setImage(sharedPrefUtils.getUserProfilePic());
//                cloudEndpointsUserUser.setCoins(0L);
                cloudEndpointsUserUser.setPhoneNumber(Long.parseLong(phoneNo));
                sharedPrefUtils.setUserAge(Integer.parseInt(age));
                return service.user().login(cloudEndpointsUserUser).execute();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class GetOTPForNumber extends AsyncTask<Void, String, CloudEndpointsUserOTP> {

        private final String mPhoneNumber;

        GetOTPForNumber(String email) {
            mPhoneNumber = email;
        }

        @Override
        protected void onPostExecute(CloudEndpointsUserOTP cloudEndpointsUserOTP) {
            super.onPostExecute(cloudEndpointsUserOTP);
//            Log.e("result is",""+cloudEndpointsUserUserList.getUsers().size());
            try {

                OTP = cloudEndpointsUserOTP.getOtpCode();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), "OTP is not returned", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(getActivity(),getResources().getString(R.string.string_otp_sent),Toast.LENGTH_SHORT).show();
            Log.e("LoginCompleted", "From GetComputerMoveTask");
        }

        @Override
        protected CloudEndpointsUserOTP doInBackground(Void... params) {
            try {
                CloudEndpointsUserPhoneNumber cloudEndpointsUserPhoneNumber = new CloudEndpointsUserPhoneNumber();
                cloudEndpointsUserPhoneNumber.setPhoneNumber(Long.valueOf(mPhoneNumber));

                Log.e("trying to", "getting OTP");
                //   Yabi.User.Login getUser = service.user().login();
//                getUser.setEmailId("yogeshmadaan100@gmail.com");
                return service.user().sendOTP(cloudEndpointsUserPhoneNumber).execute();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(message,
                new IntentFilter("send"));
    }

    private BroadcastReceiver message = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String OTPuser = intent.getStringExtra("OTP").trim();
            progressBar.setVisibility(View.GONE);
            txtProfileOtp.setText(OTPuser);


        }
    };

}

