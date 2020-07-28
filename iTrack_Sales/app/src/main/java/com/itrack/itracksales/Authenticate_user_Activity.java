package com.itrack.itracksales;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.View.OnClickListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itrack.Async.Authenticate_Async;
import com.itrack.ConstantClass.ItrackConstant;
import com.itrack.Database.DataBaseHandler;
import com.itrack.Interface.Authenticate_Interface;
import com.itrack.model.AuthenticateModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sandip on 22-12-2016.
 */

public class Authenticate_user_Activity extends Activity implements
        OnClickListener, Authenticate_Interface {
    /*
	 * All UI elements
	 */
    private Button btn_authenticate;
    private EditText edt_secretname;
   // private TextView txt_suggestion;
	/*
	 * All class member declarations
	 */
   public static String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE};
    public static int PERMISSION_ALL = 1;
    private DataBaseHandler mDataBaseHandler;
    private String Feild_authenticate;
    private Handler mHandler;
    private ArrayList<AuthenticateModel> mAuthenticateModelsArrayList;
    public static ArrayList<AuthenticateModel> Arraylistdb;
    private ArrayList<String> mArraylist;
    private String mError = "";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private int STORAGE_PERMISSION_CODE = 23;
    /**
     * @isLiveUrl =0 test url
     * @isLiveUrl =1 live url
     * */
    int isLiveUrl = 1;
    private Prep_SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mAuthenticateModelsArrayList = new ArrayList<AuthenticateModel>();
        mArraylist = new ArrayList<String>();
        preferences = new Prep_SharedPreferences(
                Authenticate_user_Activity.this, null);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    // txt_suggestion.setVisibility(View.VISIBLE);

                    TruckApplication.ShowAlert(Authenticate_user_Activity.this,
                            getString(R.string.app_name),
                            "Please enter the company name to proceed!", false);
                }

                else if (msg.what == 2) {
                    // txt_suggestion.setVisibility(View.VISIBLE);
                    TruckApplication.ShowAlert(Authenticate_user_Activity.this,
                            "Network Error!",
                            "Could Not Connect To Server, Please Try Again!",
                            false);

                }
                else if (msg.what == 3) {

                    // txt_suggestion.setVisibility(View.VISIBLE);
                    mAuthenticateModelsArrayList.clear();
                    mArraylist.clear();
                    String mMessage = "\""
                            + Feild_authenticate
                            + "\"  is not valid company name. Please enter correct company name.";

                    TruckApplication.ShowAlert(Authenticate_user_Activity.this,
                            getString(R.string.app_name), mMessage, false);
                }

                else if (msg.what == 4) {
                    boolean perm = hasPermissions(Authenticate_user_Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE});
                    if(!perm){
                        setContentView(R.layout.secret_name);
                        InitView();
                        ActivityCompat.requestPermissions(Authenticate_user_Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_ALL);
                    }
                    else {
                        Log.v("testing", " base url -->" + ItrackConstant.baseUrl);

                        Intent mIntent = new Intent(
                                Authenticate_user_Activity.this,
                                Login_Activity.class);
                        startActivity(mIntent);
                        Authenticate_user_Activity.this.finish();
                    }

                }

                else if (msg.what == 5) {

                    // txt_suggestion.setVisibility(View.VISIBLE);

                    TruckApplication.ShowAlert(Authenticate_user_Activity.this,
                            getString(R.string.app_name),
                            "Something went wrong, please try again later.",
                            false);

                }

            }
        };

        /***********************
         * . check in db for authentication data, if found send user direct to
         * the LOGIN PAGE.
         */

        mDataBaseHandler = ((TruckApplication) getApplication())
                .getTruckDatabase();
        try {

            Arraylistdb = mDataBaseHandler.getAuthenticate();

            if (Arraylistdb != null) {

                for (int i = 0; i < Arraylistdb.size(); i++) {

                    // ItrackConstant.baseUrl =
                    // Arraylistdb.get(i).getTestapiurl();

                    ItrackConstant.baseUrl = Arraylistdb.get(i).getLiveapiurl();
                    Log.d("sushil", "live url"
                            + Arraylistdb.get(i).getLiveapiurl());

                    Log.d("sushil", "test url"
                            + Arraylistdb.get(i).getTestapiurl());
                    ItrackConstant.Str_companyname = Arraylistdb.get(i)
                            .getName();
                }

                savecompanynametoPreferense(ItrackConstant.Str_companyname);

                // String base2 = ItrackConstant.baseUrl.substring(0, 37);

                String base2 = ItrackConstant.baseUrl.replace(
                        "/TruckTrackService.svc/", "");

                ItrackConstant.baseUrl2 = base2;

                Log.v("cgtlog", "arraylist authenticate size is--"
                        + Arraylistdb.size());

                mHandler.sendEmptyMessage(4);

            } else {
                setContentView(R.layout.secret_name);
                InitView();
                if(!hasPermissions(this, PERMISSIONS)){
                    ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                }

                // mPreferences = this.getSharedPreferences("company",
                // MODE_WORLD_WRITEABLE);
                // String Companyname = mPreferences
                // .getString("companyname", null);
                //
                // if (Companyname != null || !Companyname.equalsIgnoreCase(""))
                // {
                //
                // edt_secretname.setText(Companyname);
                //
                // } else {
                // edt_secretname.setText("");
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("iTrack","HAS NO PERMISSION");
                    boolean result=false;
                    return result;
                }
            }
        }
        Log.d("iTrack","HAS PERMISSION");
        return true;
    }
    /*************
     * Initiate all the views
     */

    private void InitView() {

        edt_secretname = (EditText) findViewById(R.id.edt_secretname);
        btn_authenticate = (Button) findViewById(R.id.btn_comfirm);
        btn_authenticate.setOnClickListener(this);
      //  txt_suggestion = (TextView) findViewById(R.id.txt_suggest);

        TruckApplication.setmActivity(Authenticate_user_Activity.this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {

            case R.id.btn_comfirm:

                try {
                    mError = "";
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // txt_suggestion.setVisibility(View.INVISIBLE);

                    Feild_authenticate = edt_secretname.getText().toString();

                    if (Feild_authenticate == null
                            || Feild_authenticate.equalsIgnoreCase("")) {

                        mHandler.sendEmptyMessage(1);

                    } else {

                        if (!TruckApplication.haveNetworkConnection(Authenticate_user_Activity.this, true)) {
                            Toast.makeText(getApplication(), "Please make sure that your device is " +
                                    "connected to active internet connection.",Toast.LENGTH_LONG).show();
                            mHandler.sendEmptyMessage(2);
                        }
                        else {
                            if(true)
                            {
                                Authenticate_Interface mAuthenticate_Interface = this;
                                Authenticate_Async mAuthenticate_Async = new Authenticate_Async(
                                        Authenticate_user_Activity.this,
                                        ItrackConstant.AuthenticateUrlNew
                                                + Feild_authenticate,
                                        mAuthenticate_Interface);
                                mAuthenticate_Async.execute();

                                Log.v("testing", "url on activity is :"
                                        + ItrackConstant.AuthenticateUrlNew
                                        + Feild_authenticate);
                            }
                                //requestStoragePermission();

                        } /*else {
                            mHandler.sendEmptyMessage(2);
                        }*/
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
    }
   /* //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;


        //If permission is not granted returning false
        return false;
    }
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }*/
    @Override
    public void ontaskcompleted(String result) {

        if (result != null) {
            // this is when we got an String from api Tr1VA
            if (result.startsWith("{")) {

                try {
                    JSONObject jobj = new JSONObject(result);
                    if (jobj.has("Message")
                            && jobj.getString("Message").equalsIgnoreCase(
                            "An error has occurred.")) {
                        TruckApplication
                                .ShowAlert(
                                        Authenticate_user_Activity.this,
                                        getString(R.string.app_name),
                                        "Unable to authenticate your company name. Please contact your administrator",
                                        false);
                    } else {
                        AuthenticateModel mAuthenticateModel = new AuthenticateModel();
                        if (jobj.has("Name")) {
                            String mName = jobj.getString("Name");
                            mAuthenticateModel.setName(mName);
                            mArraylist.add(mName);

                            ItrackConstant.Str_companyname = mName;



                            if (!mName.equalsIgnoreCase("No Client Exist!")) {
                                savecompanynametoPreferense(ItrackConstant.Str_companyname);
                            }
                        } else {
                            mAuthenticateModel.setName("");
                            ItrackConstant.Str_companyname = "";


                            savecompanynametoPreferense(ItrackConstant.Str_companyname);
                        }

                        if (jobj.has("ClientCode")
                                && jobj.getString("ClientCode").length() > 0) {
                            String ClientCode = jobj.getString("ClientCode");
//							Prep_SharedPreferences preferences = new Prep_SharedPreferences(
//									Authenticate_user_Activity.this, null);
                            preferences.putStringValue(
                                    ItrackConstant.ClientCode, ClientCode);
                        }

                        if (jobj.has("CheckLoadStatus")
                                && jobj.getString("CheckLoadStatus").length() > 0) {

                            String CheckLoadStatus = jobj
                                    .getString("CheckLoadStatus");
//							Prep_SharedPreferences preferences = new Prep_SharedPreferences(
//									Authenticate_user_Activity.this, null);
                            preferences.putStringValue(
                                    ItrackConstant.CheckLoadStatus,
                                    CheckLoadStatus);

                        }
                        if (jobj.has("WipedData")
                                && jobj.getString("WipedData").length() > 0) {

                            String WipedDataStatus = jobj
                                    .getString("WipedData");
                            // Prep_SharedPreferences preferences = new
                            // Prep_SharedPreferences(
                            // Authenticate_user_Activity.this, null);
                            preferences.putStringValue(
                                    ItrackConstant.WipedDataStatus,
                                    WipedDataStatus);

                            Date Mydate = new Date();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(Mydate);
                            cal.add(Calendar.DATE,
                                    Integer.parseInt(preferences
                                            .getStringValue(
                                                    ItrackConstant.WipedDataStatus,
                                                    "")) + 1); // add Wiped days

                            SimpleDateFormat sdf1 = new SimpleDateFormat(
                                    "MM/dd/yyyy");
                            String output = sdf1.format(cal.getTime());
                            preferences.putStringValue(
                                    ItrackConstant.WipedDate, output);
                        }

                        if (jobj.has("StopAuthCode")
                                && jobj.getString("StopAuthCode").length() > 0) {
                            String StopAuthCode = jobj
                                    .getString("StopAuthCode");

                            preferences.putStringValue(
                                    ItrackConstant.StopAuthCode, StopAuthCode);
                        }

                        if (jobj.has("LiveApiURL") && isLiveUrl == 1) {
                            Log.d("sushil", "isLiveurl" + isLiveUrl);
                            Log.d("sushil", "Live api callled");
                            String mLiveurl = jobj.getString("LiveApiURL");
                            mAuthenticateModel.setLiveapiurl(mLiveurl);

                            ItrackConstant.baseUrl = mLiveurl;

                            String base2 = ItrackConstant.baseUrl.replace(
                                    "/TruckTrackService.svc/", "");

                            ItrackConstant.baseUrl2 = base2;
                        } else if (jobj.has("TestApiURL") && isLiveUrl == 0) {
                            Log.d("sushil", "isLiveurl" + isLiveUrl);
                            Log.d("sushil", "Test api callled");
                            String mTestApiURL = jobj.getString("TestApiURL");
                            mAuthenticateModel.setLiveapiurl(mTestApiURL);

                            ItrackConstant.baseUrl = mTestApiURL;

                            String base2 = ItrackConstant.baseUrl.replace(
                                    "/TruckTrackService.svc/", "");

                            ItrackConstant.baseUrl2 = base2;
                        } else {
                            mAuthenticateModel.setLiveapiurl("");
                            ItrackConstant.baseUrl = "";
                        }

                        Log.d("sushil", "base 1" + ItrackConstant.baseUrl);

                        Log.d("sushil", "base 2" + ItrackConstant.baseUrl2);

                        // if (jobj.has("TestApiURL")) {
                        // String mTesturl = jobj.getString("TestApiURL");
                        // // New test api call
                        // mTesturl =
                        // "http://trucktrackdemo.iconnectgroup.com/TruckTrackService.svc/";
                        //
                        // mAuthenticateModel.setTestapiurl(mTesturl);
                        // ItrackConstant.baseUrl = mTesturl.toString();
                        // // String base2 = mTesturl.substring(0, 37);
                        //
                        // String base2 = ItrackConstant.baseUrl.replace(
                        // "/TruckTrackService.svc/", "");
                        //
                        // ItrackConstant.baseUrl2 = base2;
                        // Log.v("cgtlog", "url splite is-->" + mTesturl);
                        //
                        // } else {
                        // mAuthenticateModel.setTestapiurl("");
                        // ItrackConstant.baseUrl = "";
                        // }

                        if (jobj.has("Message")) {
                            mError = jobj.getString("Message");
                            mAuthenticateModel.setErrormessage(mError);

                            Log.v("testing", "error message is :" + mError);
                        } else {
                            mAuthenticateModel.setErrormessage("");
                        }

                        if (ItrackConstant.Str_companyname
                                .equalsIgnoreCase("No Client Exist!")) {
                            mError = ItrackConstant.Str_companyname;
                        } else {
                            mError = "";
                        }

                        mAuthenticateModelsArrayList.add(mAuthenticateModel);

                        /***************
                         * Save all the values to the data base
                         */

                        Log.v("testing", "error message is " + mError);
                        if (mAuthenticateModelsArrayList != null
                                && mAuthenticateModelsArrayList.size() > 0) {
                            if ((mError.equalsIgnoreCase("") || mError == null)) {

                                TruckApplication truckapp = (TruckApplication) getApplication();

                                for (AuthenticateModel iterable_element : mAuthenticateModelsArrayList) {

                                    iterable_element
                                            .saveAuthenticationDatabase(truckapp
                                                    .getTruckDatabase());
                                }

                                mHandler.sendEmptyMessage(4);
                            } else {

                                mHandler.sendEmptyMessage(3);
                            }
                        } else {
                            mHandler.sendEmptyMessage(5);
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();

                    mHandler.sendEmptyMessage(5);
                }
            }

            // this is when we got an array from api

            else if (result.startsWith("[")) {

                try {

                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject mJsonObject = jArray.getJSONObject(i);
                        AuthenticateModel mAuthenticateModel = new AuthenticateModel();

                        if (mJsonObject.has("Name")) {
                            String mName = mJsonObject.getString("Name");

                            mAuthenticateModel.setName(mName);
                            mArraylist.add(mName);

                            ItrackConstant.Str_companyname = mName;

                            Log.v("techno",
                                    "company name when saving data first time is :"
                                            + ItrackConstant.Str_companyname);
                            if (!mName.equalsIgnoreCase("No Client Exist!")) {
                                savecompanynametoPreferense(ItrackConstant.Str_companyname);
                            }

                        } else {
                            mAuthenticateModel.setName("");
                            ItrackConstant.Str_companyname = "";

                            Log.v("techno",
                                    "company name when saving data first time is :"
                                            + ItrackConstant.Str_companyname);
                            savecompanynametoPreferense(ItrackConstant.Str_companyname);
                        }

                        if (mJsonObject.has("LiveApiURL") && isLiveUrl == 1) {
                            String mLiveurl = mJsonObject
                                    .getString("LiveApiURL");
                            mAuthenticateModel.setLiveapiurl(mLiveurl);

                            ItrackConstant.baseUrl = mLiveurl.toString();
                            String base2 = ItrackConstant.baseUrl.replace(
                                    "/TruckTrackService.svc/", "");

                            ItrackConstant.baseUrl2 = base2;

                        } else if (mJsonObject.has("TestApiURL")
                                && isLiveUrl == 0) {
                            String mTestApiURL = mJsonObject
                                    .getString("TestApiURL");
                            mAuthenticateModel.setLiveapiurl(mTestApiURL);

                            ItrackConstant.baseUrl = mTestApiURL;

                            String base2 = ItrackConstant.baseUrl.replace(
                                    "/TruckTrackService.svc/", "");

                            ItrackConstant.baseUrl2 = base2;
                        } else if (mJsonObject.has("ID")){
                            ItrackConstant.CompanyID=mJsonObject.getString("ID");
                        } else
                        {
                            mAuthenticateModel.setLiveapiurl("");
                            ItrackConstant.baseUrl = "";
                        }

                        // if (mJsonObject.has("TestApiURL")) {
                        // String mTesturl = mJsonObject
                        // .getString("TestApiURL");
                        // mTesturl =
                        // "http://trucktrackdemo.iconnectgroup.com/TruckTrackService.svc/";
                        // mAuthenticateModel.setTestapiurl(mTesturl);
                        //
                        // ItrackConstant.baseUrl = mTesturl.toString();
                        // // String base2 = mTesturl.substring(0, 37);
                        //
                        // String base2 = ItrackConstant.baseUrl.replace(
                        // "/TruckTrackService.svc/", "");
                        //
                        // ItrackConstant.baseUrl2 = base2;
                        // } else {
                        // mAuthenticateModel.setTestapiurl("");
                        // ItrackConstant.baseUrl = "";
                        // }

                        if (mJsonObject.has("ClientCode")) {
                            String mClientcode = mJsonObject
                                    .getString("ClientCode");
                            mAuthenticateModel.setClientcode(mClientcode);
                        } else {
                            mAuthenticateModel.setClientcode(null);
                        }

                        if (mJsonObject.has("Message")) {
                            mError = mJsonObject.getString("Message");
                            mAuthenticateModel.setErrormessage(mError);

                            Log.v("testing", "error message is :" + mError);
                        } else {
                            mAuthenticateModel.setErrormessage("");
                        }

                        if (ItrackConstant.Str_companyname
                                .equalsIgnoreCase("No Client Exist!")) {
                            mError = ItrackConstant.Str_companyname;
                        } else {
                            mError = "";
                        }

                        mAuthenticateModelsArrayList.add(mAuthenticateModel);
                    }

                    /***************
                     * Save all the values to the data base
                     */

                    if ((mError.equalsIgnoreCase("") || mError == null)) {

                        TruckApplication truckapp = (TruckApplication) getApplication();

                        for (AuthenticateModel iterable_element : mAuthenticateModelsArrayList) {

                            iterable_element
                                    .saveAuthenticationDatabase(truckapp
                                            .getTruckDatabase());
                        }

                        mHandler.sendEmptyMessage(4);
                    } else {

                        mHandler.sendEmptyMessage(3);
                    }
                } catch (Exception e) {

                    e.printStackTrace();

                    mHandler.sendEmptyMessage(5);
                }
            }

        } else {
            // txt_suggestion.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method that save company name to shared preference {link Parameters}
     * company name
     */

    public void savecompanynametoPreferense(String companyname) {
        mPreferences = this.getSharedPreferences("company",
                MODE_WORLD_WRITEABLE);
        mEditor = mPreferences.edit();
        mEditor.putString("companyname", companyname);
        mEditor.commit();

        Prep_SharedPreferences prep_SharedPreferences = new Prep_SharedPreferences(
                Authenticate_user_Activity.this, null);
        prep_SharedPreferences.putStringValue(ItrackConstant.AuthCompany,
                companyname);
    }

}
