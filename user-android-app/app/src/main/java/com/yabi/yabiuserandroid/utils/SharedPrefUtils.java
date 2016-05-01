package com.yabi.yabiuserandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yabi.yabiuserandroid.R;

/**
 * Created by yogeshmadaan on 23/01/16.
 */
public class SharedPrefUtils {
    private static final int MODE = Context.MODE_PRIVATE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public SharedPrefUtils(Context context) {
        sharedPreferences = getPreferences(context);
        editor = getEditor(context);
    }

    public SharedPreferences getPreferences(Context context) {
        String appName = context.getResources().getString(R.string.app_name);
        return context.getSharedPreferences(appName, MODE);
    }

    public void clearSharedPreferenceFile(Context context) {
        getEditor(context).clear().apply();
    }

    public SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public String getUserName() {
        return sharedPreferences.getString("userName", null);
    }

    public void setUserName(String userName) {
        editor.putString("userName", userName);
        editor.commit();
    }

    public String getUserEmail() {
        return sharedPreferences.getString("userEmail", null);
    }

    public void setUserEmail(String userEmail) {
        editor.putString("userEmail", userEmail);
        editor.commit();
    }

    public String getUserPhone() {
        return sharedPreferences.getString("userPhone", null);
    }

    public void setUserPhone(String userPhone) {
        editor.putString("userPhone", userPhone);
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("userId", 0);
    }

    public void setUserId(int userId) {
        editor.putInt("userId", userId);
        MyLogger.e("user id saved", "" + userId);
        editor.commit();
    }
    public String getUserProfilePic() {
        return sharedPreferences.getString("userProfilePic", null);
    }

    public void setUserProfilePic(String userProfilePic) {
        editor.putString("userProfilePic", userProfilePic);
        editor.commit();
    }
    public String getUserCoverPic() {
        return sharedPreferences.getString("userCoverPic", null);
    }

    public void setUserCoverPic(String userCoverPic) {
        editor.putString("userCoverPic", userCoverPic);
        editor.commit();
    }
    public String getUserProfileLink() {
        return sharedPreferences.getString("userProfileLink", null);
    }

    public void setUserProfileLink(String userProfileLink) {
        editor.putString("userProfileLink", userProfileLink);
        editor.commit();
    }

    public String getUserGender() {
        return sharedPreferences.getString("userGender", "Male");
    }

    public void setUserGender(String userGender) {
        editor.putString("userGender", userGender);
        editor.commit();
    }

    public Long getUserJoiningTimestamp() {
        return sharedPreferences.getLong("userJoining", 0);
    }

    public void setUserJoining(Long userJoining) {
        editor.putLong("userJoining", userJoining);
        editor.commit();
    }

    public int getUserAge() {
        return sharedPreferences.getInt("userAge", 0);
    }

    public void setUserAge(int userAge) {
        editor.putInt("userAge", userAge);
        editor.commit();
    }
}
