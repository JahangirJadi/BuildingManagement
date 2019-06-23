package com.marfarijj.buildingmanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mctx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_NUMBER = "user_number";
    private static final String KEY_FLAT_NO = "user_flat_no";
    private static final String KEY_Is_ADMIN = "false";
    private static final String KEY_Is_COLLECTOR= "false";

    private SharedPrefManager(Context context){
        mctx=context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance==null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String phoneNo, String username, Boolean isAdmin, Boolean isCollector, String flatNo){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NUMBER, phoneNo);
        editor.putBoolean(KEY_Is_ADMIN, isAdmin);
        editor.putBoolean(KEY_Is_COLLECTOR, isCollector);
        editor.putString(KEY_FLAT_NO, flatNo);

        editor.apply();

        return true;
    }

    public String getUserFlatNo(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FLAT_NO, null);
    }

    public String getUserPhoneNo(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NUMBER, null);
    }
    public String getUserName(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public Boolean getAdminStatus(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_Is_ADMIN, false);
    }

    public Boolean getCollectorStatus(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_Is_COLLECTOR, false);
    }

    public Boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, mctx.MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_NUMBER, null)!=null){
            return true;
        }

        return false;
    }

}
