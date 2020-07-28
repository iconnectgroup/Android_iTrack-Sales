package com.itrack.ConstantClass;

import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Sandip on 24-12-2016.
 */

public class ItrackConstant {

    public static final String login = "autologin";
    public static final String CheckLoadStatus = "CheckLoadStatus";
    public static final String WipedDataStatus = "wipedddata";
    public static final String WipedDate = "wipeDate";
    public static final String StopAuthCode = "stopAuthCode";
    public static String str_clientAuth = "";
    public static String str_Logintype = "D";
    public static final String SharedPreflang = "langPrefs";
    public static final String SharedPreflangPassword = "password";
    public static String ClientCode = "ClientCode";

    public static String baseUrl;
    public static String baseUrl2;
    public static String Str_companyname;
    public static String CompanyID;
    // public static String loginUrl = baseUrl
    // + "TruckTrackService.svc/MobileUserLogIn";

    public static String loginUrl = "login";

	/*
	 * Logout URL
	 */

    // public static String logoutUrl = baseUrl
    // + "TruckTrackService.svc/MobileUserLogOut";

    public static String logoutUrl = "MobileUserLogOut";

    /**
     * Service code url
     */
    // public static String getservicecodeurl = baseUrl
    // + "TruckTrackService.svc/GetServiceCodes?TruckNo=";

    public static String getservicecodeurl = "GetServiceCodes?TruckNo=";

    /******
     * Authenticate api url
     */

    // public static String AuthenticateUrl =
    // "http://iprofitclientsapi.iconnectgroup.com/API/Client/GetClientList";
    public static String AuthenticateUrlNew = "http://iprofitclientsapi.iconnectgroup.com/api/DailyData/CheckClient?clientcode=";

    /*
    * GetDailySalesDataReport
    * */
    //public static String GetDailySalesDataReport = "http://dailydata.afhstx.iconnectgroup.com/API/LiveData/GetDailySalesDataReport?CompanyID=&StoreID=&UserID=";
    public static String GetDailySalesDataReport = "GetDailySalesDataReport?CompanyID=&StoreID=&UserID=";

    /****
     * Pushnotification url
     */
    public static String AuthCompany = "Company";
    public static IntentFilter s_intentFilter;
    static {
        s_intentFilter = new IntentFilter();

        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

    }
}
