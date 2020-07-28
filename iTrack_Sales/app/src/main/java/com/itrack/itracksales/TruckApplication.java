package com.itrack.itracksales;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import com.itrack.Database.DataBaseHandler;
import com.itrack.Utility.AndroidLogger;
import com.itrack.Utility.NetworkStrengthListener;
import com.itrack.Interface.AlertListener;

import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * Created by Sandip on 24-12-2016.
 */

@SuppressLint("ShowToast")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TruckApplication extends Application {

    private static ProgressDialog mProgressDialog;
    private static String filePath = "";
    public static boolean isRunapiforstopcount = false;

    static NetworkInfo activeNetInfo;
    static NetworkInfo mobNetInfo;
    static ConnectivityManager connec;
    static TelephonyManager telephonyManager;
    static WifiManager wifiManager;
    public static int strength;
    public static int mobileStrength;
    public static int signalStrengthValue;
    public static boolean mobileNetwork;
    public static boolean isdatechanged = false;

    public static boolean isLogoutpressed = false;
    public static boolean isAutosyncfromService = false;

    public DataBaseHandler TruckDatabase;

    public static DataBaseHandler databaseFile;

    public static boolean isNotecommenthandled = false;

    public static boolean isStopFinished = false;

    public static String baseUrl = null;
    public static String baseUrl2 = null;
    public static boolean isfromstoppage = false;
    public static boolean inSync = false;

    public static boolean sendupdatestatus = false;

    public static boolean isNoclciked = false;

    public static Activity mActivity;

    public static Activity stopDetail_Activity;

    public static boolean isGetstopActivityRaedy = false;

    // //////////////////////////////////////////////////////////////

    public static boolean isNewdateSelected = false;
    public static String str_newdate = "null";

    // public static ArrayList<Authenticate_Stop> mAuthenticate_Stops = new
    // ArrayList<Authenticate_Stop>();

    // ///////////////////////////////////////////////////////////////

    // ////////////////////////////////////////////////////////////////////

    public static boolean isNewdateSelectedprep = false;
    public static String str_newdateprep = "null";

    // ///////////////////////////////////////////////////////////////

    public static boolean isServicecodeselectedalready = false;

    public static int Back_Button_Pressed = 0; // "0" for Nothing,"1" for Load
    // page,"2" for Stop page,"3"
    // for Prep page

    /**
     * date picker work
     *
     * @return
     */

    private int count = 0;
   /* private DatePicker_Interface DateInterface;
    private DatePickerPrepLoad_Interface mDatePickerPrepLoad_Interface;*/
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private DatePickerDialog mDatePickerDialog;

    private String str_Year;
    private String str_Month;
    private String str_Date;
    public static int filecount = 1;
    public static Activity Act_Autosync;
    public static Activity activity_StopStatus;

    public static int stopDataUploading = 0;

    private static TruckApplication instance;

    public static void setmActivity(Activity mActivity) {
        TruckApplication.mActivity = mActivity;
    }

    public DataBaseHandler getTruckDatabase() {
        return TruckDatabase;
    }

    public static boolean isInternetReachable = true;

    public String SENDER_ID = "819655716665";
    private static Prep_SharedPreferences prep_SharedPreferences;
    public static AndroidLogger logger;

    private static SharedPreferences langPrefs;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    public static final int DIALOG_LOADING = 1;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        logger = new AndroidLogger();
        logger.setLogDirName("itrack_PLS");
        logger.setMaxFiles(1);
        logger.setLogFileName("Error.txt");

        instance = this;

        prep_SharedPreferences = new Prep_SharedPreferences(instance, null);
        langPrefs = getApplicationContext().getSharedPreferences("langPrefs",
                MODE_WORLD_WRITEABLE);
        IntentFilter iFilter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(iReciver, iFilter);

        // IntentFilter intentFilter = new IntentFilter();
        // intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

        TruckDatabase = new DataBaseHandler(getApplicationContext(), null,
                null, 1);
        databaseFile = new DataBaseHandler(getApplicationContext(), null, null,
                1);
        TelephonyManager TelephonManager;
        NetworkStrengthListener pslistener;

        try {
            pslistener = new NetworkStrengthListener();
            TelephonManager = (TelephonyManager) instance
                    .getSystemService(Context.TELEPHONY_SERVICE);
            TelephonManager.listen(pslistener,
                    PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        } catch (Exception ex) {

            ex.printStackTrace();

        }
        /**********************************************
         * Get gcm registration id for push notification
         */

        mContext = instance.getApplicationContext();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "application class called",
                        Toast.LENGTH_LONG);
            }
        }, 100);

    }

    /**
     *
     * Remove all application data
     * */
    public static TruckApplication getInstance() {
        if (instance == null) {
            instance = new TruckApplication();

        }

        return instance;
    }

    public void clearApplicationData(Context context) {
        context.deleteDatabase(TruckDatabase.getDatabaseName());

        Log.d("Sandip",
                "db path"
                        + context.getDatabasePath(
                        TruckDatabase.getDatabaseName())
                        .getAbsolutePath());

        TruckDatabase.close();

        File root = new File("/data/data/com.itrack.itracksales/shared_prefs");

        boolean deleted = deleteDir(root);

        Log.d("Sandip", "shared pref is cleared" + deleted);

    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }

        return dir.delete();
    }

    /**
     * Start service to update location
     */

    public BroadcastReceiver iReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);

            activeNetInfo = connec.getActiveNetworkInfo();
            mobNetInfo = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (activeNetInfo != null) {

                mobileNetwork = false;
                WifiInfo scanResult = wifiManager.getConnectionInfo();
                strength = scanResult.getRssi();

            }
            if (mobNetInfo != null) {

                mobileNetwork = true;

                AndroidPhoneStateListener androidPhoneStateListener = new AndroidPhoneStateListener();
                telephonyManager.listen(androidPhoneStateListener,
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

                PhoneStateListener phoneStateListener = new PhoneStateListener() {
                    public void onCallForwardingIndicatorChanged(boolean cfi) {
                    }

                    public void onCallStateChanged(int state,
                                                   String incomingNumber) {
                    }

                    public void onCellLocationChanged(CellLocation location) {
                    }

                    public void onDataActivity(int direction) {
                    }

                    public void onDataConnectionStateChanged(int state) {

                        mobileStrength = state;
                    }

                    public void onMessageWaitingIndicatorChanged(boolean mwi) {
                    }

                    public void onServiceStateChanged(ServiceState serviceState) {

                    }

                    public void onSignalStrengthChanged(int asu) {

                    }
                };

                telephonyManager.listen(phoneStateListener,
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                                | PhoneStateListener.LISTEN_SERVICE_STATE);

            }

            if ((connec.getNetworkInfo(0) != null && (connec.getNetworkInfo(0)
                    .getState() == NetworkInfo.State.CONNECTED))
                    || (connec.getNetworkInfo(1) != null && (connec
                    .getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED))) {

                isInternetReachable = true;

            } else if ((connec.getNetworkInfo(0) != null && (connec
                    .getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED))
                    || (connec.getNetworkInfo(1) != null && (connec
                    .getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED))) {

                isInternetReachable = false;

            }

        }
    };

    public class AndroidPhoneStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            if (signalStrength.isGsm()) {
                if (signalStrength.getGsmSignalStrength() != 99)
                    signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
                else
                    signalStrengthValue = signalStrength.getGsmSignalStrength();
            } else {
                signalStrengthValue = signalStrength.getCdmaDbm();
            }

        }

    }

    /*******
     * Itrack Global Alert Dialog
     */
    public static void ShowAlert(Context mContext, String mTitle,
                                 String mMessage, boolean isCancelBtn) {
        try {
            AlertDialog.Builder mAlert = new AlertDialog.Builder(mContext);

            mAlert.setCancelable(false);
            mAlert.setTitle(mTitle);
            mAlert.setMessage(mMessage);
            if (isCancelBtn) {
                mAlert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
            }

            mAlert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

            mAlert.show();

        } catch (Exception e) {

        }
    }

    private AlertListener alertListener;
    private Context mContext;

    public void showAlertDialog(Context mContext, AlertListener listnerSender,
                                boolean status, String mTitle, String mMessage,
                                final int statusType, boolean isCancelBtn, boolean isYes) {
        this.alertListener = listnerSender;

        final boolean statusLocal = status;
        AlertDialog.Builder mAlert = new AlertDialog.Builder(mContext);
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage(mMessage);
        if (isYes) {
            mAlert.setPositiveButton(mContext.getString(R.string.Tag_Yes),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            alertListener.AlertClick(statusLocal, statusType);

                        }

                    });
        } else {
            mAlert.setPositiveButton(mContext.getString(R.string.Tag_Ok),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            alertListener.AlertClick(statusLocal, statusType);

                        }

                    });
        }
        if (isCancelBtn) {
            mAlert.setNegativeButton(mContext.getString(R.string.Tag_Cancel),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

        }

        mAlert.show();
    }



    /*
     * Itrack Global Alert Dialog
     */
    public static void ShowAlertForGPS(final Context mContext, String mTitle,
                                       String mMessage, boolean isCancelBtn) {

        AlertDialog.Builder mAlert = new AlertDialog.Builder(mContext);

        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage(mMessage);
        if (isCancelBtn) {
            mAlert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
        }

        mAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                turngpson(mContext);

                dialog.dismiss();
            }
        });

        mAlert.show();
    }

    /***********
     * Show Progress dialog
     */

    public static void showProgressDialog(Context mContext, String mTitle,
                                          String mMessage) {
        Log.d("Sandip", "show progress called");
        try {
            if (mContext != null) {
                Log.d("Sandip", "instense is not null");
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setTitle(mTitle);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(mMessage);
                mProgressDialog.setProgress(0);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog.show();
                } else {
                    mProgressDialog.show();
                }
            } else {
                Log.d("Sandip", "instense is null");
            }
        } catch (Exception e) {

        }

    }
    public static Dialog CreateDialog(Context mContext,int id) {

        switch (id) {
            case DIALOG_LOADING:
                final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //here we set layout of progress dialog
                dialog.setContentView(R.layout.custom_progress_dialog);
                dialog.setCancelable(true);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub

                    }
                });
                return dialog;

            default:
                return null;
        }
    };
    public static void showtoast(Context mContext, String Message) {
        Toast.makeText(mContext, Message, Toast.LENGTH_SHORT).show();
    }

    /********
     * hide progress dialog
     *
     * @param mContext
     */
    public static void hideProgressDialog(Context mContext) {
        try {
            if (mContext != null) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                } else {
                    mProgressDialog = new ProgressDialog(mContext);
                    mProgressDialog.dismiss();
                }
            } else {
                Log.d("Sandip", " hide instense is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeDialog(Context mContext) {
        // hideProgressDialog(mContext);
    }

    /***
     * check for internet connection
     */

    public static boolean haveNetworkConnection(Context mContext,
                                                boolean showAlert) {
        boolean isConnected = false;
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        if (mContext == null) {
            return false;
        } else {
            ConnectivityManager cm = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            isConnected = haveConnectedWifi || haveConnectedMobile;
            /*if (isConnected) {
                return isConnected;
            } else {
                if (showAlert) {
                   Toast.makeText(mContext, "Please make sure that your device is " +
                            "connected to active internet connection.",Toast.LENGTH_LONG).show();
                }
            }*/
            return isConnected;
        }
    }

    public static boolean isActiveNetwork(Context context) {
        if (context != null) {
            PackageManager mPackageManager = context.getPackageManager();
            if (mPackageManager.checkPermission(
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {

                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo mNetworkInfo = connectivityManager
                        .getActiveNetworkInfo();

                return mNetworkInfo != null && mNetworkInfo.isConnected();

            } else {

                return false;
            }
        } else {
            return false;
        }
    }

    /********
     * Convert response to string
     */

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        is.close();

        return sb.toString();
    }

    /***
     * Methode to clear cache memory
     */

    public static void clearcacheMemory(Activity act) {

        PackageManager pm = act.getPackageManager();

        Method[] methods = pm.getClass().getDeclaredMethods();
        for (Method m : methods) {

            if (m.getName().equals("freeStorageAndNotify")) {

                try {
                    long desiredFreeStorage = Long.MAX_VALUE; // Request for 8GB
                    // of free space
                    m.invoke(pm, desiredFreeStorage, null);
                } catch (Exception e) {

                    e.printStackTrace();

                }
                break;
            }
        }
    }



    public static String code(String response) {

        try {

            String code = response.toString();
            if (code.contains("0")) {

                return code;
            } else {
                return "not success";
            }
        } catch (Exception e) {

        }
        return null;

    }

    public static void turngpson(Context mContext) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);

        mContext.sendBroadcast(intent);

        // final Intent poke = new Intent();
        // poke.setClassName("com.android.settings",
        // "com.android.settings.widget.SettingsAppWidgetProvider");
        // poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
        // poke.setData(Uri.parse("3"));
        // mContext.sendBroadcast(poke);
    }

    /***
     * Function to Show simple DatePicker
     */

    /***
     * Function to Show simple DatePicker
     */

    /*public void ShowDatePickerPrepLoad(final Context mcContext,
                                       DatePickerPrepLoad_Interface mDateInterface, String findtextview) {

        UtilityDebug.Debug(1, "Show date picker called");

		*//*
		 * Set the Date in Date Picker Here
		 *//*

        count = 0;

        Calendar mCalendar = Calendar.getInstance();

        this.mDatePickerPrepLoad_Interface = mDateInterface;

        mDateSetListner = new OnDateSetListener() {
            private boolean loadFired = false;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                str_Year = String.valueOf(year);
                int mMonth = monthOfYear + 1;
                str_Month = String.valueOf(mMonth);
                str_Date = String.valueOf(dayOfMonth);

                if (str_Month.length() == 1) {
                    str_Month = "0" + str_Month;
                }

                if (str_Date.length() == 1) {
                    str_Date = "0" + str_Date;
                }

                // String str_DatePicked = str_Year + "-" + str_Month + "-"
                // + str_Date;

                String str_DatePicked = str_Month + "/" + str_Date + "/"
                        + str_Year;

                if (mDatePickerPrepLoad_Interface != null) {

                    // DateInterface.onDatepickertaskcompleted(str_DatePicked);

                    if (loadFired == true) {

                    } else {
                        mDatePickerPrepLoad_Interface
                                .onDatePickerTaskCompleted(str_DatePicked);
                        loadFired = true;
                    }

                }

                // SimpleDateFormat formatter = new
                // SimpleDateFormat("dd/MM/yyyy");

                // Log.v("mathur", "onDateSet called :");
            }

        };

        mDatePickerDialog = new DatePickerDialog(mcContext, mDateSetListner,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));

        // mDatePickerDialog.updateDate(2015, 11, 07);

        // code by bharat

       // DatePicker dp = mDatePickerDialog.getDatePicker();

        // set today is as max date in date picker
        // dp.setMaxDate(mCalendar.getTimeInMillis());

        // code by bharat end

        mDatePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Log.v("mathur", "Set button cliked :");

                        dialog.dismiss();
                    }
                });

        mDatePickerDialog.show();

    }
*/
    /**
     * Method that compare device date with selected date by the user if device
     * date is less than selected date it will return False if device date is
     * grater than selected date it will return True
     *
     * @param str_DatePicked
     * @return true if date is correct else return false
     */


    /**
     * Compare date for prep & load
     *
     * @param str_DatePicked
     * @return
     */

    /**
     * Get the bitmap from image url
     *
     * @param src
     * @return
     */
    /**
     * @param detailModel
     *            send ItemsDetail Model
     * @return 3 for NO (No one at home) 2 for RE(Rescheduled) 1 for PRE or any
     *         other or finally 0 for no code on any item
     *
     *
     * */


   /* @SuppressWarnings("deprecation")
    public static DisplayImageOptions getDisplayImageOptions(
            final int maxWidth, final int maxHeight) {
        if (displayOptions == null) {
            BitmapFactory.Options resizeOptions = new BitmapFactory.Options();

            new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub)
                    .showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory()
                    .cacheOnDisc().decodingOptions(resizeOptions)
                    .postProcessor(new BitmapProcessor() {
                        @Override
                        public Bitmap process(Bitmap bmp) {
                            return Bitmap.createScaledBitmap(bmp, maxWidth,
                                    maxHeight, false);
                        }
                    }).build();
        }

        return displayOptions;

    }
*/
    /*public static void showKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }*/

    public static String SignalStrength = "";
    public static int syncingCalled = -1;
    // public static boolean CalledFromStopAsync = false;




    public void showCustomAlertDialog(Context mContext,
                                      AlertListener listnerSender, boolean status, String mTitle,
                                      String mMessage, final int statusType, boolean isSkip) {
        this.alertListener = listnerSender;

        final boolean statusLocal = status;
        AlertDialog.Builder mAlert = new AlertDialog.Builder(mContext);
        mAlert.setCancelable(false);
        mAlert.setTitle(mTitle);
        mAlert.setMessage(mMessage);
        if (isSkip == true) {
            mAlert.setPositiveButton(mContext.getString(R.string.Tag_Skip),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            alertListener.AlertClick(statusLocal, statusType);

                        }

                    });
        }
        mAlert.setNegativeButton(mContext.getString(R.string.Tag_Ok),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        mAlert.show();
    }

    /*public void callLogin(String getPassword, String getUsername,
                          Context mContext, Login_Interface loginInterface) {
        Log.d("Sandip", "callLogin called Truck application");
        SharedPreferences pref_checkstatus = mContext.getSharedPreferences(
                "logintype", Context.MODE_WORLD_WRITEABLE);

        String str_Logintype = pref_checkstatus.getString("logintype", "");

        Login_Async loginasync = new Login_Async(mContext, getPassword,
                getUsername, Login_Activity.device, "Android",
                Login_Activity.deviceID, Login_Activity.connectionType,
                Login_Activity.RegistrationId, loginInterface, str_Logintype);

        loginasync.execute();

    }*/
}
