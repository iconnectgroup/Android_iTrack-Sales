package com.itrack.itracksales;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.itrack.Async.Login_Async;
import com.itrack.ConstantClass.ItrackConstant;
import com.itrack.Interface.Login_Interface;
import com.itrack.Interface.Logout_Interface;
import com.itrack.Utility.UtilityDebug;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressLint({"HandlerLeak", "WorldWriteableFiles", "SimpleDateFormat"})
public class Login_Activity extends Activity implements View.OnClickListener,
        Login_Interface, Logout_Interface {

    /****************
     * All UI Elements Declaration
     */

    private Button btn_login, btn_cancel;
    private EditText edt_Username;
    private EditText edt_Password;
    private String getUsername;
    private String getPassword;
    public static final String Email = "userKey";
    public static final String Pass1 = "passKey";
    public static final String CHECKLOGIN = "checklogin";
    public boolean checkLogin = false;
    String  user, pass;

   /* private TextView mCompanyName;*/
    private Prep_SharedPreferences mSharedPreferences;

	/*
     * All class member declaration
	 */

    public static ConnectivityManager conMan;
    boolean networkStatus;
    boolean networkState;
    public static String textUsername, textPassword, UrlLogin = "",
            connectionType, device, myResponse, truckNo, driverName;

    public static String deviceID;

    public static String RegistrationId;

    private SharedPreferences msharedpref;
    private SharedPreferences Pref_offline;
    private SharedPreferences.Editor pref_editor;


    @SuppressWarnings("unused")
    private String driver;
    private String getautologinstatus;

    private String Loginmessage = "";

    private Handler mHandler;
    private String LogoutMeassage;
    private String Dateplusone;

    private String str_Logintype = "A";
    private SharedPreferences pref_checkstatus;
    private SharedPreferences prefLang;
    private SharedPreferences langPrefs;

    @SuppressWarnings({"unused", "deprecation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_);
        mSharedPreferences = new Prep_SharedPreferences(getApplication());
        //mSharedPreferences.checkLogin();
        InitView();
        prefLang = this.getSharedPreferences(ItrackConstant.SharedPreflang,
                Context.MODE_PRIVATE);
// get user data from session
      /*  HashMap<String, String> user = mSharedPreferences.getUserDetails();

        // name
            getUsername = user.get(Prep_SharedPreferences.KEY_NAME);

        // email
         getPassword = user.get(Prep_SharedPreferences.KEY_PASSWORD);


       // getUsername = prefLang.getString("username", null);
       // getPassword = prefLang.getString("password", null);

        if (getUsername != null && getPassword != null) {

                UtilityDebug.Debug(1, "on create else called");
                edt_Username.setText(getUsername);
                edt_Password.setText(getPassword);
                TruckApplication maApplication = (TruckApplication) getApplication();
                // maApplication.TruckDatabase.ClearAllDB();

        }*/

        mHandler = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    TruckApplication.ShowAlert(Login_Activity.this,
                            getString(R.string.app_name),
                            "Username And Password Are Required!", false);
                } else if (msg.what == 2) {
                    TruckApplication.ShowAlert(Login_Activity.this,
                            getString(R.string.app_name), "Username Required!",
                            false);
                } else if (msg.what == 3) {
                    TruckApplication.ShowAlert(Login_Activity.this,
                            getString(R.string.app_name), "Password Required!",
                            false);
                } else if (msg.what == 4) {
                    TruckApplication
                            .ShowAlert(
                                    Login_Activity.this,
                                    getString(R.string.app_name),
                                    "Internet connection not found! Please try again later.",
                                    false);

                } else if (msg.what == 5) {
                    TruckApplication.ShowAlert(Login_Activity.this,
                            getString(R.string.app_name), Loginmessage, false);
                } else if (msg.what == 6) {
                    TruckApplication
                            .ShowAlert(Login_Activity.this,
                                    getString(R.string.app_name),
                                    LogoutMeassage, false);
                } else if (msg.what == 7) {
                    TruckApplication
                            .ShowAlert(
                                    Login_Activity.this,
                                    getString(R.string.app_name),
                                    "Please check any of the login type and then press login button.",
                                    false);
                }
            }
        };
        device = android.os.Build.DEVICE;
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();
        if (networkInfo != null) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final android.net.NetworkInfo wifi = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            final android.net.NetworkInfo mobileinfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo networkInformation = connManager.getActiveNetworkInfo();
            final boolean connected = networkInfo != null
                    && networkInfo.isAvailable() && networkInfo.isConnected();
            final boolean connectedwifi = wifi != null && wifi.isAvailable()
                    && wifi.isConnected();
            final boolean connectedmob = mobileinfo != null
                    && mobileinfo.isAvailable() && mobileinfo.isConnected();
            if (connectedwifi || connected || connectedmob) {
                networkStatus = true;
            } else {
                networkStatus = false;
            }
            connectionType = networkInfo.getTypeName();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {

        TruckApplication.setmActivity(Login_Activity.this);
        TruckApplication.clearcacheMemory(Login_Activity.this);
        String x = Environment.getExternalStorageState();
        Log.v("mathur", "String x :" + x);
        super.onResume();
    }

    @Override
    protected void onPause() {

        TruckApplication.clearcacheMemory(Login_Activity.this);
        super.onPause();
    }

    /*
     * Initiate all view
     */
    @SuppressWarnings({"static-access", "deprecation"})
    private void InitView() {
        /*mCompanyName = (TextView) findViewById(R.id.companyname);*/
        edt_Username = (EditText) findViewById(R.id.username);
        edt_Password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.login);
        btn_login.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        deviceID = telephonyManager.getDeviceId();

        if (deviceID == null) {
            deviceID = getId();
        }
        edt_Username.requestFocus();
        if(mSharedPreferences.getStringValue(Email,"")!=null && !mSharedPreferences.getStringValue(Email,"").equalsIgnoreCase("") &&
                mSharedPreferences.getStringValue(Pass1,"")!=null && !mSharedPreferences.getStringValue(Pass1,"").equalsIgnoreCase("")){

            edt_Username.setText(""+mSharedPreferences.getStringValue(Email,""));
            edt_Password.setText(""+mSharedPreferences.getStringValue(Pass1,""));
        }
        /**********
         * get registration id from shared prif
         */

        SharedPreferences shared = getSharedPreferences("deviceid",
                this.MODE_WORLD_WRITEABLE);
        RegistrationId = shared.getString("id", "");

        if (RegistrationId.equalsIgnoreCase("")) {
            RegistrationId = shared.getString("id", "");
        }

        pref_checkstatus = getSharedPreferences("logintype",
                MODE_WORLD_WRITEABLE);

        if (!pref_checkstatus.getString("logintype", "").equalsIgnoreCase("")) {
            if (pref_checkstatus.getString("logintype", "").equalsIgnoreCase(
                    "A")) {
                str_Logintype = "A";
            }

        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    removeApplicationData();
                    break;
                case R.id.login:

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    textPassword = edt_Password.getText().toString();
                    textUsername = edt_Username.getText().toString();

                    if (textPassword.equalsIgnoreCase("")
                            && textUsername.equalsIgnoreCase("")) {

                        mHandler.sendEmptyMessage(1);

                    } else if (textUsername.equalsIgnoreCase("")) {

                        mHandler.sendEmptyMessage(2);

                    } else if (textPassword.equalsIgnoreCase("")) {

                        mHandler.sendEmptyMessage(3);

                    } else {

                        /**
                         * call asynctask to parse login api
                         */

                        if (TruckApplication.isActiveNetwork(Login_Activity.this)) {

                            Login_Interface loginInterface = this;

                            Login_Async loginasync = new Login_Async(
                                    Login_Activity.this, textUsername,
                                    textPassword, device, "Android", deviceID,
                                    connectionType, RegistrationId, loginInterface,
                                    str_Logintype);

                            loginasync.execute();
                        } else {

                             mHandler.sendEmptyMessage(4);

                            langPrefs = Login_Activity.this.getSharedPreferences(
                                    "langPrefs", MODE_WORLD_WRITEABLE);
                            textPassword = edt_Password.getText().toString().trim();
                            textUsername = edt_Username.getText().toString().trim();

                            // mHandler.sendEmptyMessage(4);

                            UtilityDebug.Debug(1, "text username" + textUsername);

                            UtilityDebug.Debug(1, "text password" + textPassword);

                            UtilityDebug.Debug(
                                    1,
                                    "save user name"
                                            + langPrefs.getString("username", ""));

                            UtilityDebug.Debug(
                                    1,
                                    "save password"
                                            + langPrefs.getString("password", ""));

                            if (langPrefs.getString("username", "")
                                    .equalsIgnoreCase(textUsername)
                                    && langPrefs.getString("password", "")
                                    .equalsIgnoreCase(textPassword)) {
                                storeLoginData(textUsername, textUsername);
                                // callServicecodeApi(truckno);

                            /*    TruckApplication.scheduleAlarms(
                                        Login_Activity.this,
                                        TruckApplication.Time_Interval, true);
*/
                            } else {
                                if (!langPrefs.getString("username", "")
                                        .equalsIgnoreCase(textUsername)) {
                                    TruckApplication.ShowAlert(Login_Activity.this,
                                            getString(R.string.app_name),
                                            "Username Are Incorrect!", false);
                                } else if (!langPrefs.getString("password", "")
                                        .equalsIgnoreCase(textPassword)) {
                                    TruckApplication.ShowAlert(Login_Activity.this,
                                            getString(R.string.app_name),
                                            "Password Are Incorrect!", false);
                                } else {
                                    TruckApplication.ShowAlert(Login_Activity.this,
                                            getString(R.string.app_name),
                                            "Username And Password Are Incorrect!",
                                            false);
                                }
                            }
                        }
                    }
                    break;

                default:
                    break;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onloginAsynccomplete(String result) {

        if (result != null && !result.equalsIgnoreCase("")) {
            UtilityDebug.Debug(1, "Mobile user login result" + result);
            try {
                JSONObject mObj = new JSONObject(result);
                // {"ContactID":"1","Name":"Amitesh Sinha","Type":"A","Message":"Login Successfully"}
                //JSONObject mObj = new JSONObject(jobj.getString("login").toString());
                TruckApplication.filecount += 1;
                String ContactID = mObj.getString("ContactID");
                String Name = mObj.getString("Name");
                String Type = mObj.getString("Type");
                Loginmessage = mObj.getString("Message");
                if(Loginmessage.equals("Login Successfully"))
                {mSharedPreferences = new Prep_SharedPreferences(this);

                    mSharedPreferences.putStringValue(Email, textUsername);
                    mSharedPreferences.putStringValue(Pass1, textPassword);
                    checkLogin = true;
                    mSharedPreferences.putBooleanValue(CHECKLOGIN, checkLogin);
                    mSharedPreferences.createLoginSession("preppref", textUsername);
                    Intent mIntent = new Intent(Login_Activity.this,
                            Written_sales_Activity.class);
                    startActivity(mIntent);
                    Login_Activity.this.finish();
                }
                else {
                    mHandler.sendEmptyMessage(5);
                }

                /*

                int cCheck = Integer.parseInt(code);

                if (cCheck == 0 || cCheck == 99) {

                    storeLoginData(truckno, truckdriver);
                    callServicecodeApi(truckno);

                   */
/* TruckApplication.scheduleAlarms(getApplicationContext(),
                            TruckApplication.Time_Interval, true);
                    TruckApplication.mArraylist_unreadStopno = new ArrayList<NotificationModel>();*//*

                }

                else {
                    mHandler.sendEmptyMessage(5);

                }
*/

            } catch (JSONException e) {

                e.printStackTrace();
                TruckApplication.ShowAlert(Login_Activity.this,
                        getString(R.string.app_name),
                        "Something went wrong, please try again!", false);

            }

        } else {
            TruckApplication.ShowAlert(Login_Activity.this,
                    getString(R.string.app_name),
                    "Something went wrong, please try again!", false);
        }
    }

    void callLoadStatusApi(String str_truckno) {

        SimpleDateFormat dateFormatnew = new SimpleDateFormat("MM/dd/yyyy");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String currentDate = dateFormat.format(new Date());


        /*******************************************
         * Async task to get the service codes from web service
         */

    }




   /* void setData(String truckno) {
        Pref_offline = getSharedPreferences(ItrackConstant.SharedPrefOffline,
                MODE_WORLD_WRITEABLE);
        pref_editor = Pref_offline.edit();
        pref_editor.putString(ItrackConstant.SharedPrefOfflineStopKey, "true");
        pref_editor.putString(ItrackConstant.SharedPrefOfflinePrepLoadKey,
                "true");
        pref_editor.commit();

       *//* if (!textUsername.contains("dmin")) {
            TruckApplication.updatelocation(Login_Activity.this);
            TruckApplication.startservicecommunication(Login_Activity.this);
        }*//*

        *//*if (truckno.contains("ST") || truckno.contains("st")) {
            Intent stopPage = new Intent(Login_Activity.this,
                    GetStops_Activity.class);// send to newstop page
            // when created
            startActivity(stopPage);
            Login_Activity.this.finish();

        } else {

            Intent stopPage = new Intent(Login_Activity.this,
                    PrepStopChoice.class);
            // stopPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(stopPage);
            Login_Activity.this.finish();
        }*//*
    }*/

    @SuppressWarnings("deprecation")
    void storeLoginData(String truckno, String truckdriver) {
        Log.d("Sandip", "store login data called");
       /* mSharedPreferences.putStringValue(ItrackConstant.CURRENTDATE,
         currentdate);*/
        textPassword = edt_Password.getText().toString();
        textUsername = edt_Username.getText().toString();

        // this shared preference is set for Location update & get
        // communication log count api

    }


    /**********************************
     * To get device id for non sim phone
     *
     * @return id
     */
    public String getId() {

        String id = android.provider.Settings.System.getString(
                super.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        return id;

    }

    /**
     * Method to check Internet connection type
     */

    public static Boolean CheckNetwork() {

        final android.net.NetworkInfo wifi = conMan
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobileinfo = conMan
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();
        final boolean connected = networkInfo != null
                && networkInfo.isAvailable() && networkInfo.isConnected();
        final boolean connectedwifi = wifi != null && wifi.isAvailable()
                && wifi.isConnected();
        final boolean connectedmob = mobileinfo != null
                && mobileinfo.isAvailable() && mobileinfo.isConnected();

        if (connectedwifi || connected || connectedmob) {
            connectionType = networkInfo.getTypeName();

            return true;
        } else {

            return false;
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onlogoutAsynccomplete(String result) {

        if (result != null) {

            try {

                JSONObject jobj = new JSONObject(result);

                JSONObject mObj = null;
                if (jobj.has("UserLogOutResult")) {
                    mObj = new JSONObject(jobj.getString("UserLogOutResult")
                            .toString());
                } else if (jobj.has("MobileUserLogOutResult")) {

                    mObj = new JSONObject(jobj.getString(
                            "MobileUserLogOutResult").toString());
                }

                String Code = mObj.getString("Code");
                LogoutMeassage = mObj.getString("Meassage");
                int cCheck = Integer.parseInt(Code);

                if (cCheck == 0 || cCheck == 99) {

                } else {

                    mHandler.sendEmptyMessage(6);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    public String DatePlusOne() {
        SimpleDateFormat dateFormatnew = new SimpleDateFormat("MM/dd/yyyy");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Dateplusone = dateFormat.format(new Date());

        Log.v("mathur", "mDate is :" + Dateplusone);

        return Dateplusone;

    }

    @Override
    protected void onStop() {

        super.onStop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        // unregisterReceiver(new DateTimeChangeReceiver());
    }

    private void removeApplicationData() {
        // if (TruckApplication.dateTimeChangeReceiver != null) {
        // unregisterReceiver(TruckApplication.dateTimeChangeReceiver);
        // TruckApplication.dateTimeChangeReceiver=null;
        // }
        mSharedPreferences = new Prep_SharedPreferences(Login_Activity.this);
        mSharedPreferences.login_Clear();

        final TruckApplication truckApp = (TruckApplication) getApplication();
        langPrefs = getSharedPreferences("langPrefs", MODE_WORLD_WRITEABLE);
        final SharedPreferences.Editor prefsEditor = langPrefs.edit();

        Pref_offline = getSharedPreferences("offline", MODE_WORLD_WRITEABLE);
        final SharedPreferences.Editor pref_editor = Pref_offline.edit();
        SharedPreferences Pref_company = getSharedPreferences("company",
                MODE_WORLD_WRITEABLE);
        final SharedPreferences.Editor pref_company = Pref_company.edit();

        SharedPreferences Pref_preppref = getSharedPreferences("preppref",
                MODE_WORLD_WRITEABLE);
        final SharedPreferences.Editor pref_preppref = Pref_preppref.edit();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                prefsEditor.clear();
                prefsEditor.commit();
                Log.d("Sandip", "prefsEditor lang pref clear");
            }
        }, 50);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pref_company.toString();
                pref_company.clear();
                pref_company.commit();
                Log.d("Sandip", "pref_company clear");
            }
        }, 100);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                pref_editor.clear();
                pref_editor.commit();
                Log.d("Sandip", "prefsEditor offline clear");
            }
        }, 150);

        if (truckApp != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    truckApp.clearApplicationData(Login_Activity.this);

                    Log.d("Sandip", "ClearAllDB clear");
                }
            }, 200);

        }

        /*Intent i = new Intent(Login_Activity.this, Authenticate_user_Activity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        Login_Activity.this.startActivity(i);*/
        final Intent companyPage = new Intent(this,
                Authenticate_user_Activity.class);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                companyPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                companyPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                companyPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TruckApplication.hideProgressDialog(Login_Activity.this);
                startActivity(companyPage);
            }
        }, 450);
    }
}
