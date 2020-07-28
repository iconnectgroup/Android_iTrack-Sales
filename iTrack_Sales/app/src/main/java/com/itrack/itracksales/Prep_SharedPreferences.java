package com.itrack.itracksales;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Sandip on 24-12-2016.
 */

public class Prep_SharedPreferences {
    /*
    * Prep_SharedPreferences Class is used to maintain shared preferences
    */

    /*
     * Prep_SharedPreferences Members Declarations
	 */
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private String str_PrefName = "preppref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    public Prep_SharedPreferences(Context context){
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(str_PrefName, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    /*
     * prep_SharedPreference Constroctor Implementation
     */
    public Prep_SharedPreferences(Context context,
                                  SharedPreferences.OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener) {
        this.mContext = context;
        if (mContext != null) {
            if (str_PrefName != null) {
                mSharedPreferences = mContext.getSharedPreferences(str_PrefName, Context.MODE_WORLD_READABLE);
                if (mOnSharedPreferenceChangeListener != null) {
                    mSharedPreferences.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
                }
            }
        }
    }

    /**
     * This method is used to store String value in SharedPreferences
     */

    public void putStringValue(String editorkey, String editorvalue) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(editorkey, editorvalue);
        mEditor.commit();
    }

    /*
    * This method is used to store int value in sharePrefrences
    */
    public void putIntValue(String editorkey, int editorvalue) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(editorkey, editorvalue);
        mEditor.commit();
    }

    /*
    * This method is used to store boolean value in SharePrefreance
    */
    public void putBooleanValue(String editorkey, boolean editorvalue) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(editorkey, editorvalue);
        mEditor.commit();
    }

    /*
    * This method is used to get value from sharedPrefrence
    */
    public String getStringValue(String editorkey, String defValue) {
        String PrefValue = mSharedPreferences.getString(editorkey, defValue);

        return PrefValue;
    }

    /**
     * This method is used to get int value from SharedPreferences
     */
    public int getIntValue(String editorkey, int defValue) {
        int PrefValue = mSharedPreferences.getInt(editorkey, defValue);

        return PrefValue;
    }

    /**
     * This method is used to get boolean value from SharedPreferences
     */
    public boolean getBooleanValue(String editorkey, boolean defValue) {
        boolean PrefValue = mSharedPreferences.getBoolean(editorkey, defValue);

        return PrefValue;
    }

    public void putCategoryValue(String editorkey, String editorvalue) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(editorkey, editorvalue);
        mEditor.commit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String password){
        // Storing login value as TRUE
        mEditor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        mEditor.putString(KEY_NAME, name);

        // Storing email in pref
        mEditor.putString(KEY_PASSWORD, password);

        // commit changes
        mEditor.commit();
    }
    /**
            * Get stored session data
    * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, mSharedPreferences.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_PASSWORD, mSharedPreferences.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, Written_sales_Activity1.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Staring Login Activity
            mContext.startActivity(i);
        }

    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
      //  mEditor.clear();
       // mEditor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, Login_Activity.class);
        i.putExtra("username",mSharedPreferences.getString("Email",""));

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }

    public void login_Clear(){
        // Clearing all data from Shared Preferences
         mEditor.clear();
         mEditor.commit();

        // After logout redirect user to Loing Activity
       // Intent i = new Intent(mContext, Login_Activity.class);
       // i.putExtra("username",mSharedPreferences.getString("Email",""));

        // Closing all the Activities
      //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
       // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        //mContext.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return mSharedPreferences.getBoolean(IS_LOGIN, false);
    }


}

