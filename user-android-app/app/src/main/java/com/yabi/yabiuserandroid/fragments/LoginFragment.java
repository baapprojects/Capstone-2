package com.yabi.yabiuserandroid.fragments;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appspot.yabiapp.yabi.Yabi;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.yabi.AppConstants;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.adapters.LoginImagesTabAdapter;
import com.yabi.yabiuserandroid.ui.uiutils.viewpagerindicator.PageIndicator;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class LoginFragment extends Fragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    EventBus eventBus;
    // Butter Knife Injection
    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.indicator)
    PageIndicator pageIndicator;
    @OnClick(R.id.btn_sign_in)
    public void signInClicked()
    {
        signInWithGplus();

    }
    @Bind(R.id.btn_sign_in)
    SignInButton signInButton;
    LoginImagesTabAdapter mAdapter;
    Yabi service = null;
    public static final int RESULT_OK = -1;


    private static final int RC_GET_AUTH_CODE = 9003;
    private String mEmailAccount = "";
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "LoginFragment";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;
    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Plus.SCOPE_PLUS_LOGIN)
                .requestScopes(Plus.SCOPE_PLUS_PROFILE)
                .requestServerAuthCode(AppConstants.WEB_CLIENT_ID)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)
//                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,rootView);
        initViews();

        return rootView;
    }

    public void initViews()
    {
        eventBus = EventBus.getDefault();
//        eventBus.register(this);
        mAdapter = new LoginImagesTabAdapter(getActivity().getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(mAdapter);
        pageIndicator.setViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicator.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void onStart() {
        super.onStart();
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        if (mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), getActivity(),
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_AUTH_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "onActivityResult:GET_AUTH_CODE:success:" + result.getStatus().isSuccess());

            if (result.isSuccess()) {
                // [START get_auth_code]
                GoogleSignInAccount acct = result.getSignInAccount();
                String authCode = acct.getServerAuthCode();

                // Show signed-in UI.
                Log.e("auth token",""+authCode);
//                mAuthCodeTextView.setText(getString(R.string.auth_code_fmt, authCode));
                getProfileInformation();


                // TODO(user): send code to server and exchange for access/refresh/ID tokens.
                // [END get_auth_code]
            } else {
                // Show signed-out UI.
                updateUI(false);
            }
        }
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }




//    /**
//     * Verifies OAuth2 token access for the application and Google account combination with
//     * the {@code AccountManager} and the Play Services installed application. If the appropriate
//     * OAuth2 access hasn't been granted (to this application) then the task may fire an
//     * {@code Intent} to request that the user approve such access. If the appropriate access does
//     * exist then the button that will let the user proceed to the next activity is enabled.
//     */
//    class AuthorizationCheckTask extends AsyncTask<String, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(String... emailAccounts) {
//            Log.i(TAG, "Background task started.");
//
//            if (!AppConstants.checkGooglePlayServicesAvailable(getActivity())) {
//                return false;
//            }
//
//            String emailAccount = emailAccounts[0];
//            // Ensure only one task is running at a time.
//            mAuthTask = this;
//
//            // Ensure an email was selected.
//            if (Strings.isNullOrEmpty(emailAccount)) {
//                publishProgress(R.string.app_name);
//                // Failure.
//                return false;
//            }
//
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "Attempting to get AuthToken for account: " + mEmailAccount);
//            }
//
//            try {
//                // If the application has the appropriate access then a token will be retrieved, otherwise
//                // an error will be thrown.
//                GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
//                        getActivity(), AppConstants.AUDIENCE);
//                credential.setSelectedAccountName(emailAccount);
//                android.accounts.Account accounts = credential.getSelectedAccount();
//                String accessToken = credential.getToken();
//                Log.e("access token",""+accessToken);
//                if (BuildConfig.DEBUG) {
//                    Log.d(TAG, "AccessToken retrieved");
//                }
//                Yabi.Builder builder = new Yabi.Builder(
//                        AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
//                        credential)
//                        .setRootUrl("https://yabiapp.appspot.com/_ah/api/");
//
//                service = builder.build();
//                // Success.
//                return true;
//            } catch (GoogleAuthException unrecoverableException) {
//                Log.e(TAG, "Exception checking OAuth2 authentication.", unrecoverableException);
//                publishProgress(R.string.common_google_play_services_unknown_issue);
//                // Failure.
//                return false;
//            } catch (IOException ioException) {
//                Log.e(TAG, "Exception checking OAuth2 authentication.", ioException);
//                publishProgress(R.string.common_google_play_services_unknown_issue);
//                // Failure or cancel request.
//                return false;
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... stringIds) {
//            // Toast only the most recent.
//            Integer stringId = stringIds[0];
//            Toast.makeText(getActivity(), stringId, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            mAuthTask = this;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean success) {
//            if (success) {
//                // Authorization check successful, set internal variable.
//                Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
//
//                new GetComputerMoveTask().execute();
//            } else {
//                // Authorization check unsuccessful, reset TextView to empty.
//                Toast.makeText(getActivity(),"Failure",Toast.LENGTH_LONG).show();
//            }
//            mAuthTask = null;
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//        }
//    }


    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(getActivity(), "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();

        // Update the UI after signin

    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if(isSignedIn)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, SigninProfileFragment.newInstance()).addToBackStack(null).commit();

    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            Log.e("current name",""+ Plus.AccountApi.getAccountName(mGoogleApiClient));
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                int age = currentPerson.getAgeRange().getMin();
                String personCoverUrl = null;
                if(currentPerson!=null && currentPerson.getCover()!=null && currentPerson.getCover().getCoverPhoto()!=null)
                    personCoverUrl= currentPerson.getCover().getCoverPhoto().getUrl();
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;
                Log.i(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                Log.i(TAG,currentPerson.toString());

//                txtName.setText(personName);
//                txtEmail.setText(email);
                new SharedPrefUtils(getActivity()).setUserName(personName);
                new SharedPrefUtils(getActivity()).setUserEmail(email);
                new SharedPrefUtils(getActivity()).setUserProfilePic(personPhotoUrl);
                new SharedPrefUtils(getActivity()).setUserProfileLink(personGooglePlusProfile);
                new SharedPrefUtils(getActivity()).setUserCoverPic(personCoverUrl);
                new SharedPrefUtils(getActivity()).setUserAge(age);
                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X


//                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
                if(personName!=null && email!=null && personGooglePlusProfile !=null)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, SigninProfileFragment.newInstance()).addToBackStack(null).commit();
                else
                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(),"Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }


    /**
     * Button on click listener
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
        }
    }

    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
//        if (!mGoogleApiClient.isConnecting()) {
//            mSignInClicked = true;
//            resolveSignInError();
//        }
        getAuthCode();
    }

    /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    /**
     * Revoking access from google
     * */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }


    private void getAuthCode() {
        // Start the retrieval process for a server auth code.  If requested, ask for a refresh
        // token.  Otherwise, only get an access token if a refresh token has been previously
        // retrieved.  Getting a new access token for an existing grant does not require
        // user consent.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

}

