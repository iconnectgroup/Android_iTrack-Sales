package com.itrack.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itrack.model.AuthenticateModel;

import java.util.ArrayList;

/**
 * Created by Sandip on 26-12-2016.
 */

@SuppressWarnings("deprecation")
public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "itrackSales";

    /*private static final String TABLE_STOPS = "CUSTOMER";

    private static final String TABLE_DETAILS = "ORDERITEM";

    private static final String TABLE_OLDSTOPS = "OLDSTOP";

    private static final String TABLE_CALLNEXTSTOP = "CALLNEXTSTOP";

    private static final String TABLE_IMAGE = "IMAGE";

    private static final String TABLE_LoadIMAGE = "LoadIMAGE";

    private static final String TABLE_IMAGESYNC = "IMAGESYNC";

    private static final String TABLE_IMAGE_HOME = "IMAGEHOME";

    private static final String TABLE_IMAGE_NEW = "IMAGENEW";

    private static final String TABLE_STOPSTATUS = "STOPSTATUS";

    private static final String TABLE_COMMON = "COMMON";
    // ** TABLE IS USED FOR LINE COMMENT
    private static final String TABLE_COMMONNEW = "COMMONNEW";

    private static final String TABLE_SAVELINECOMMENT = "savelinecomment";

    private static final String TABLE_EMAIL = "EMAIL";

    private static final String TABLE_EMAILNEW = "EMAILNEW";

    private static final String TABLE_PREPSTOPS = "PREPCUSTOMER";
    private static final String TABLE_PREPDETAILS = "PREPORDERITEM";
    private static final String TABLE_LOAD = "LOAD";

    private static final String TABLE_SERVICECODE = "servicecode";

    private static final String TABLE_REASON_CODE = "reasoncode";

    private static final String TABLE_REASON_CODE_YE = "reasoncode_ye";

    private static final String TABLE_CHECKLIST = "checklist";

    private static final String TABLE_DRIVERLIST = "driverlist";
    private static final String TABLE_HELPERLIST = "helperlist";

    private static final String TABLE_SAVEDCHECKLIST = "savedchecklist";

    private static final String TABLE_StopNo = "stopno";*/

    private static final String TABLE_AUTHENTICATE = "authenticate";

    /*private static final String TABLE_WAIVERSIGNATURE = "waiversignature";

    private static final String TABLE_LOADSIGNATURE = "loadsignature";

    private static final String TABLE_LOADCHECKBOX = "loadcheckbox";

    private static final String TABLE_COMMUNICATION_COUNT = "communicationcount";

    private static final String TABLE_SAVEDCOMMNET = "savedcomment";

    private static final String TABLE_PREVIOUS_COMMENT = "previouscomment";

    private static final String TABLE_SAVEDSURVEY = "savedsurvey";

    private static final String TABLE_PREVIOUSSURVEY = "previoussurvey";

    private static final String TABLE_SURVEYQUESTIONS = "surveyquestions";

    private static final String TABLE_SURVEYQUESTIONSANSWERS = "surveyquestionsanswers";

    private static final String TABLE_SURVEYQUESTIONSANSWERSCHANGEDATE = "surveyquestionsanswerschangedate";

    private static final String TABLE_JSONSURVEY = "jsonsurvey";

    // Archana work

    *//**
     * NEWPREP_Stops Table
     *//*

    private static final String NEWPREP_TABLE_STOPS = "NEWPREP_CUSTOMER";

    *//**
     * NEWPREP_Stops item Detail Table
     *//*
    private static final String NEWPREP_TABLE_DETAILS = "NEWPREP_ITEMDETAILS";

    *//**
     * NEWPREP_Team data Table
     *//*
    private static final String NEWPREP_TABLE_TEAMS = "NEWPREP_TEAMS";

    *//**
     * NEWPREP team employees Table
     *//*
    private static final String NEWPREP_TABLE_TEAM_EMPLOYEES = "NEWPREP_TEAMEMPLOYEES";

    *//**
     * NEWPREP team selected employees Table
     *//*
    private static final String NEWPREP_TABLE_SELECTED_EMPLOYEES = "NEWPREP_SELECTEDEMPLOYEES";

    private static final String TABLE_SWP_CODES = "swp_codes";

    *//**
     * MANAGED THE SKIPPED STOPS IN SKIP_STOP TABLE
     * *//*

    private static final String TABLE_SKIP_STOP = "skip_stop";*/

    /**
     * fIELDS KEYS OF TABLE SKIP_STOP;
     * */
   /* private static final String SKIP_STOP_ID = "id";

    private static final String SKIP_TRUCK_NO = "truck_no";

    private static final String SKIP_STOP_STOP_ID = "stop_id";

    private static final String SKIP_STOP_IS_PENDING = "ispending";

    private static final String SKIP_STOP_IS_FINISHED = "isfinished";
    // **NEWPREP_TABLE_STOPS KEYS CONSTANTS
    private static final String NEWPREP_CUSTOMER_ID = "newprep_cust_id";// primary
    // key
    private static final String NEWPREP_CUST_CUSTID = "newprep_cust_custid";
    private static final String NEWPREP_CUST_NAME = "newprep_cust_name";
    private static final String NEWPREP_CUST_ADDRESS = "newprep_cust_address";
    private static final String NEWPREP_CUST_PHONE = "newprep_cust_phone";
    private static final String NEWPREP_CUST_ORDERNO = "newprep_cust_orderno";
    private static final String NEWPREP_CUST_TIMEFROM = "newprep_cust_timefrom";
    private static final String NEWPREP_CUST_TIMETO = "newprep_cust_timeto";
    private static final String NEWPREP_CUST_PIECES = "newprep_cust_pieces";
    private static final String NEWPREP_CUST_STOPNO = "newprep_cust_stopno";
    private static final String NEWPREP_CUST_TEAM = "newprep_cust_team";

    // **NEWPREP_TABLE_DETAILS KEYS CONSTANTS
    private static final String NEWPREP_ITEM_NO = "newprep_item_no";
    private static final String NEWPREP_ITEM_ITEMNO = "newprep_item_itemno";
    private static final String NEWPREP_ITEM_LINENO = "newprep_item_lineno";
    private static final String NEWPREP_ITEM_DESCRIPTION = "newprep_item_description";

    private static final String NEWPREP_ITEM_BARCODE = "newprep_item_barcode";
    private static final String NEWPREP_ITEM_NOTES = "newprep_item_notes";
    private static final String NEWPREP_ITEM_UPH = "newprep_item_UPH";
    private static final String NEWPREP_ITEM_HARDWARE = "newprep_item_hardware";
    private static final String NEWPREP_ITEM_CG = "newprep_item_CG";
    private static final String NEWPREP_ITEM__ASMBL = "newprep_item_ASMBL";
    private static final String NEWPREP_ITEM__SWP = "newprep_item_SWP";

    private static final String NEWPREP_ITEM_BED = "newprep_item_Bed";
    private static final String NEWPREP_ITEM_DAMAGED = "newprep_item_Damaged";
    private static final String NEWPREP_ITEM_CHAIRS = "newprep_item_Chairs";
    private static final String NEWPREP_ITEM_STOPNO = "newprep_item_stopno";
    private static final String NEWPREP_ITEM_REASON = "newprep_item_Reason";

    private static final String NEWPREP_ITEM_SYNCSTATUS = "newprep_item_SyncStatus";
    private static final String NEWPREP_ITEM_BEDSYNCSTATUS = "newprep_item_BedSyncStatus";
    private static final String NEWPREP_ITEM_ASMBLSYNCSTATUS = "newprep_item_ASMBLSyncStatus";
    private static final String NEWPREP_ITEM_SWPSYNCSTATUS = "newprep_item_SWPSyncStatus";
    private static final String NEWPREP_ITEM_CHAIRSSYNCSTATUS = "newprep_item_ChairSyncStatus";
    private static final String NEWPREP_ITEM_CGSYNCSTATUS = "newprep_item_CGSyncStatus";
    private static final String NEWPREP_ITEM_HARDWARESYNCSTATUS = "newprep_item_HardwareSyncStatus";
    private static final String NEWPREP_ITEM_UPHSYNCSTATUS = "newprep_item_UPHSyncStatus";

    // **NEWPREP_TABLE_TEAMS KEYS CONSTANTS

    private static final String NEWPREP_TEAM_TEAMID = "NEWPREP_team_TeamID";
    private static final String NEWPREP_TEAM_NAME = "NEWPREP_team_Name";

    // **NEWPREP_TABLE_TEAM_EMPLOYEES KEYS CONSTANTS
    private static final String NEWPREP_TEAMEMP_WHCODE = "NEWPREP_team_Code";
    private static final String NEWPREP_TEAMEMP_WHNAME = "NEWPREP_team_Name";
    private static final String NEWPREP_TEAMEMP_TEAMID = "NEWPREP_team_TeamID";
    private static final String NEWPREP_TEAMEMP_TEAMNAME = "NEWPREP_team_TeamName";
    private static final String NEWPREP_TEAMEMP_CONTACTID = "NEWPREP_team_ContactID";
    private static final String NEWPREP_TEAMEMP_SELECTED = "NEWPREP_team_IsSelected";

    // **NEWPREP_TABLE_SELECTED_EMPLOYEES KEYS CONSTANTS
    private static final String NEWPREP_SELECTEDEMP_WHCODE = "NEWPREP_selectedEmp_Code";
    private static final String NEWPREP_SELECTEDEMP_WHNAME = "NEWPREP_selectedEmp_Name";
    private static final String NEWPREP_SELECTEDEMP_TEAMID = "NEWPREP_selectedEmp_TeamID";
    private static final String NEWPREP_SELECTEDEMP_TEAMNAME = "NEWPREP_selectedEmp_TeamName";
    private static final String NEWPREP_SELECTEDEMP_CONTACTID = "NEWPREP_selectedEmp_ContactID";
    private static final String NEWPREP_SELECTEDEMP_SELECTED = "NEWPREP_selectedEmp_IsSelected";
    private static final String NEWPREP_SELECTEDEMP_STOPNO = "NEWPREP_selectedEmp_StopNO";*/

    // ///////////////////////////////////////////////////////////////////////////////////////////////

    // ** KEY CONSTANT FOR SAVED STOPNO
   /* private static final String SAVED_STOPNO = "saved_stopno";

    // ** KEY CONSTATNTS FOR TABLE JSON SURVEY

    private static final String JSONSURVEY_STOPNO = "jsonsurvey_stopno";
    private static final String JSONSURVEY_CUSTID = "jsonsurvey_custid";
    private static final String JSONSURVEY_JSON = "jsonsurvey_json";

    // ** KEY CONSTANTS FOR COMMUNICATION COUNT
    private static final String COMMUNICATION_STOPNO = "communication_stopno";
    private static final String COMMUNICATION_COUNT = "communication_count";
    private static final String COMMUNICATION_UNREAD = "communication_unread";
    private static final String COMMUNICATION_TRUCKNO = "communication_truckno";

    // ** KEY CONSTANTS FOR WAIVER SIGNATURE

    private static final String WAIVER_STOPNO = "waiver_stopno";
    private static final String WAIVER_IMAGEPATH = "waiver_imagepath";
    private static final String WAIVER_ACCEPT = "waiver_accept";
    private static final String WAIVER_FORMID = "waiver_formid";
    private static final String WAIVER_TRUCKNO = "waiver_truckno";
    private static final String WAIVER_CUSTID = "waiver_custid";
    private static final String WAIVER_ORDERNO = "waiver_orderno";
    private static final String WAIVER_DATE = "waiver_date";
    private static final String WAIVER_ISSYNC = "waiver_issync";

    // ** KEY CONSTANTS FOR LOAD SIGNATURE

    private static final String LOAD_SIGNATUREIMAGEPATH = "load_signatureimagepath";
    private static final String LOAD_SIGNATURETRUCKNO = "load_signaturetruckno";

    // ** KEY CONSTANTS FOR LOAD CHECK BOXES

    private static final String LOAD_CHECKBOXTRUCKNO = "load_checkboxtruckno";
    private static final String LOAD_CHECKBOXDATE = "load_checkboxdate";
    private static final String LOAD_CHECKBOXCHECKLIST = "load_checkboxchecklist";
    private static final String LOAD_CHECKBOXUID = "load_checkboxuid";

    // ** KEY CONSTANTS FOR SAVED COMMENT

    private static final String SAVED_COMMNET_CUSTID = "saved_comment_custid";
    private static final String SAVED_COMMNET_DATE = "saved_comment_date";
    private static final String SAVED_COMMNET_ORDERNO = "saved_comment_orderno";
    private static final String SAVED_COMMNET_TRUCKNO = "saved_comment_truckno";
    private static final String SAVED_COMMNET_TEXT = "saved_comment_text";

    // ** KEY CONSTANTS FOR SAVED SURVEY

    private static final String SAVED_SURVEY_CUSTID = "saved_survey_custid";
    private static final String SAVED_SURVEY_ANS1 = "saved_survey_ans1";

    private static final String SAVED_SURVEY_ANS2 = "saved_survey_ans2";
    private static final String SAVED_SURVEY_ANS3 = "saved_survey_ans3";
    private static final String SAVED_SURVEY_ANS4 = "saved_survey_ans4";
    private static final String SAVED_SURVEY_ANS5 = "saved_survey_ans5";
    private static final String SAVED_SURVEY_ORDERNO = "saved_survey_orderno";
    private static final String SAVED_SURVEY_TRUCKNO = "saved_survey_truckno";

    private static final String SAVED_SURVEY_SIGNATURE = "saved_survey_signature";

    private static final String SAVED_survey_TEXT = "saved_survey_text";

    // **KEY CONSTANTS FOR SURVEY QUESTIONS

    private static final String SURVEY_QUESTIONS_QID = "surveyqid";

    private static final String SURVEY_QUESTIONS_QUESTION = "surveyquestions";

    private static final String SURVEY_QUESTIONS_ORDER = "surveyorder";

    private static final String SURVEY_QUESTIONS_QTYPE = "surveyqtype";

    // **KEY CONSTANTS FOR SURVEY QUESTIONS ANSWERS

    private static final String SURVEY_ANSWERS_QID = "answerqid";

    private static final String SURVEY_ANSWERS_QUESTION = "answersquestions";

    private static final String SURVEY_ANSWERS_ANSWER = "answers";

    private static final String SURVEY_ANSWERS_STOPNO = "answersstopno";

    private static final String SURVEY_ANSWERS_CUSTOMERID = "answerscustomerid";

    private static final String SURVEY_ANSWERS_ORDERNO = "answersorderno";*/

    // /** KEY CONSTANTS FOR AUTHENTICATE TABLE

    private static final String AUTHENTICATE_USERNAME = "authenticate_username";
    private static final String AUTHENTICATE_LIVEAPI = "authenticate_liveapi";
    private static final String AUTHENTICATE_TESTAPI = "authenticate_testapi";
    private static final String AUTHENTICATE_CLIENTCODE = "authenticate_clientcode";

    // **KEY CONSTANTS FOR SERVICE CODES

   /* private static final String SERVICE_CODES = "service_codes";
    private static final String SERVICE_CAMERA = "service_camera";
    private static final String SERVICE_DESCRIPTION = "service_description";

    // **KEY CONSTANTS FOR REASON CODES

    private static final String REASON_CODES = "reason_codes";
    private static final String REASON_STATUS = "reason_status";
    private static final String REASON_DESCRIPTION = "reason_description";
    private static final String REASON_CAMERA = "reason_camera";
    // ** KEY CONSTATNT FOR DRIVER LIST

    private static final String DRIVER_CODE = "driver_codes";
    private static final String DRIVER_NAME = "driver_name";
    private static final String DRIVER_PHONE = "driver_phn";

    // ** KEY CONSTATNT FOR HELPER LIST

    private static final String HELPER_CODE = "helper_codes";
    private static final String HELPER_NAME = "helper_name";
    private static final String HELPER_PHONE = "helper_phn";

    // **KEY CONSTANTS FOR CHECK LIST

    private static final String CHECK_CODES = "check_codes";
    private static final String CHECK_STATUS = "check_status";
    private static final String CHECK_DESCRIPTION = "check_description";
    private static final String CHECK_SELECTED = "check_selected";
    private static final String CHECK_DRIVERNAME = "check_drivername";
    private static final String CHECK_DRIVERID = "check_driverid";
    private static final String CHECK_DRIVERPHN = "check_driverphn";
    private static final String CHECK_COMPANY = "check_company";
    private static final String CHECK_HELPER = "check_helper";
    private static final String CHECK_HELPERID = "check_helperid";
    private static final String CHECK_ACKNOWLEDGE = "check_acknowledge";
    private static final String CHECK_BASEURL = "check_baseurl";
    // **TABLE STOPS KEYS CONSTANTS
    private static final String CUSTOMER_ID = "cust_id";
    private static final String CUST_NAME = "cust_name";
    private static final String CUST_CUSTID = "cust_custid";
    private static final String CUST_ADDRESS = "cust_address";
    private static final String CUST_EMAILID = "cust_email";

    private static final String CUST_PHONE = "cust_phone";
    private static final String CUST_ORDERNO = "cust_orderno";
    private static final String CUST_DESCRIPTION = "cust_description";
    private static final String CUST_TIMEFROM = "cust_timefrom";
    private static final String CUST_TIMETO = "cust_timeto";
    private static final String CUST_DELIVERYCODE = "cust_deliverycode";
    private static final String CUST_STOP = "cust_stop";
    private static final String CUST_STOPLOCK = "cust_stoplock";
    private static final String CUST_STOPSTATUS = "cust_stopstatus";
    private static final String CUST_ITEM_COUNT = "cust_item_count";

    // **TABLE DETAILS KEYS CONSTANTS
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_ITEMID = "item_itemid";
    private static final String ITEM_DESCRIPTION = "item_description";
    private static final String ITEM_LINENO = "item_lineno";
    private static final String ITEM_QTY = "item_qty";
    private static final String ITEM_CUSTID = "item_custid";
    private static final String ITEM_ORDERNO = "item_orderno";
    private static final String ITEM_LOAD = "item_load";
    private static final String ITEM_RE = "item_re";
    private static final String ITEM_LOADOUTSTATUS = "item_loadstatus";
    private static final String ITEM_LOADOUTSTATUSCOMMENT = "item_loadcomment";
    private static final String ITEM_STATUS = "item_status";

    private static final String ITEM__Load_Comment = "item_loadcomment";
    private static final String ITEM__Load_REASONCODE = "item_reasoncode";
    private static final String ITEM__Load_REASONDESCRIPTION = "item_reasondescription";

    private static final String ITEM_SERVICECODE = "item_servicecode";
    private static final String ITEM_IMAGES = "item_images";
    private static final String ITEM_URL = "item_url";
    private static final String ITEM_STOPNO = "item_stopno";
    private static final String ITEM_SYNC = "item_sync";
    private static final String ITEM_DATE = "item_date";

    // **TABLE IMAGE KEYS CONSTANTS
    private static final String IMAGE_ID = "image_id";
    private static final String IMAGE_TRUCKNO = "image_truckno";
    private static final String IMAGE_CUSTID = "image_custid";
    private static final String IMAGE_ORDERNO = "image_orderno";
    private static final String IMAGE_ITEMID = "image_itemid";
    private static final String IMAGE_LINENO = "image_lineno";
    private static final String IMAGE_IMAGEPATH = "image_imagepath";
    private static final String IMAGE_DATE = "image_date";
    private static final String IMAGE_STATUS = "image_status";
    private static final String IMAGE_STOPNO = "image_stopno";

    // **Key constant for Load Image
    private static final String LOADIMAGE_ID = "loadimage_id";
    private static final String LOADIMAGE_ORDERNO = "loadimage_orderno";
    private static final String LOADIMAGE_CUSTID = "loadimage_custid";
    private static final String LOADIMAGE_ITEMID = "loadimage_itemid";
    private static final String LOADIMAGE_LINENO = "loadimage_lineno";
    private static final String LOADIMAGE_IMAGEPATH = "loadimage_imagepath";
    private static final String LOADIMAGE_DATE = "loadimage_date";

    private static final String LOADIMAGE_LAT = "loadimage_lat";
    private static final String LOADIMAGE_LONG = "loadimage_long";

    // **TABLE IMAGE HOME KEYS CONSTANTS
    private static final String IMAGEHOME_ID = "imagehome_id";
    private static final String IMAGEHOME_TRUCKNO = "imagehome_truckno";
    private static final String IMAGEHOME_CUSTID = "imagehome_custid";
    private static final String IMAGEHOME_ORDERNO = "imagehome_orderno";

    private static final String IMAGEHOME_IMAGEPATH = "imagehome_imagepath";
    private static final String IMAGEHOME_DATE = "imagehome_date";
    private static final String IMAGEHOME_SYNC = "imagehome_sync";

    private static final String IMAGEHOME_LAT = "imagehome_lat";
    private static final String IMAGEHOME_LONG = "imagehome_long";
    private static final String IMAGEHOME_TIMESTAMP = "imagehome_timestamp";
    private static final String IMAGEHOME_DELIVERYCODE = "imagehome_deliverycode";

    // / ** OLDDB KEYS CONSTANTS

    private static final String OLDSTOP_TRUCKNO = "truckno";
    private static final String OLDSTOP_ISLOCK = "islock";
    private static final String OLDSTOP_BTNINDEX = "btnindex";

    // **CALL NEXT STOP KEY CONSTANTS

    private static final String TAG_TRUCKNO = "truckno";
    private static final String TAG_CUSTID = "custid";
    private static final String TAG_ORDERNO = "orderno";
    private static final String TAG_STOPNO = "stopno";
    private static final String TAG_CALLSTATUS = "callstatus";

    // Next of stop of
    // current stop
    private static final String TAG_DELIVERYDATE = "deliverydate";
    private static final String Tag_COMMON_ID = "tagcommonid";

    *//************
     * TABLE TO HANDEL CALL NEXT STOP BUTTON
     *//*
    private static final String TABLE_CALLNEXTBUTTON = "CALLNEXTSTOPBUTTON";

    private static final String TAG_CURRENTSTOP = "currentstop";
    private static final String TAG_BTNCLICK = "btn_click";

    // **TABLE STOPSTATUS KEYS CONSTANTS
    private static final String STOPSTATUS_ID = "stopstatus_id";
    private static final String STOPSTATUS_TRUCKNO = "stopstatus_truckno";
    private static final String STOPSTATUS_STOPNO = "stopstatus_stopno";
    private static final String STOPSTATUS_STATUSSYNC = "stopstatus_statussync";
    private static final String STOPSTATUS_STATUS = "stopstatus_status";
    private static final String STOPSTATUS_DATE = "stopstatus_date";

    private static final String STOPSTATUS_STOPSTATUSVALUE = "stopstatus_stopstatusvalue";
    private static final String STOPSTATUS_TIME = "stopstatus_time";

    // **TABLE COMMON KEYS CONSTANTS
    private static final String COMMON_ID = "common_id";
    private static final String COMMON_CUSTID = "common_custid";
    private static final String COMMON_ORDERNO = "common_orderno";
    private static final String COMMON_TRUCKNO = "common_truckno";
    private static final String COMMON_DATE = "common_date";
    private static final String COMMON_TYPE = "common_type";
    private static final String COMMON_SYNC = "common_sync";

    private static final String LINECOMMENT_ITEMID = "linecommentitemid";
    private static final String LINECOMMENT_LINENO = "linecommentlineno";
    private static final String LINECOMMENT_COMMENT = "linecommentcomment";

    private static final String COMMON_URLSERVICE = "common_urlservice";

    private static final String COMMENT = "comment";
    private static final String COMMENTSYNC_STATUS = "commentsyncstatus";
    private static final String COMMENTBIG_STOPNO = "commentbigstopno";

    private static final String COMMENT_ITEMID = "comment_itemid";

    private static final String COMMENT_SERVICECODE = "comment_servicecode";
    private static final String COMMENT_LINENO = "comment_lineno";
    private static final String COMMENT_STOPNO = "comment_stopno";
    private static final String COMMENT_STATUS = "comment_status";

    private static final String SIGNATURE = "signature";

    private static final String QUESTION1 = "question1";
    private static final String ANSWER1 = "answer1";
    private static final String QUESTION2 = "question2";
    private static final String ANSWER2 = "answer2";
    private static final String QUESTION3 = "question3";
    private static final String ANSWER3 = "answer3";
    private static final String QUESTION4 = "question4";
    private static final String ANSWER4 = "answer4";
    private static final String QUESTION5 = "question5";
    private static final String ANSWER5 = "answer5";
    private static final String QUESTION6 = "question6";
    private static final String NOTE = "note";

    // Table email keys constants

    private static final String EMAIL_ID = "email_id";
    private static final String EMAIL_CUSTID = "email_custid";
    private static final String EMAIL_ORDERNO = "email_orderno";
    private static final String EMAIL_TRUCKNO = "email_truckno";
    private static final String EMAIL_DATE = "email_date";
    private static final String EMAIL_COMMON_ID = "email_commonid";
    private static final String EMAIL_TYPE = "email_type";
    private static final String EMAIL_STOPNO = "email_stopno";

    // **TABLE LOAD KEYS CONSTANTS
    private static final String LOAD_ID = "load_id";
    private static final String LOAD_TRUCKNO = "load_truckno";
    private static final String LOAD_CUSTID = "load_custid";
    private static final String LOAD_ORDERNO = "load_orderno";
    private static final String LOAD_ITEMID = "load_itemid";
    private static final String LOAD_LINENO = "load_lineno";
    private static final String LOAD_LOAD = "load_load";
    private static final String LOAD_DATE = "load_date";
    private static final String LOAD_REASON_CODE = "load_reason_code";
    private static final String LOAD_REASON_COMMENT = "load_reason_comment";
    private static final String LOAD_REASON_NO = "load_reason_no";
    private static final String LOAD_USERNAME = "load_username";
    private static final String LOAD_PASSWORD = "load_password";

    private static final String LOAD_REASON_PICTURE = "load_reason_picture";

    *//**
     * TABLE START DELIVERY
     * *//*

    private static final String TABLE_START_DELIVERY = "start_delivery";

    *//**
     * TABLE STARTDELIVERY KEYS CONSTANTS
     * *//*

    private static final String DELIVERY_TRUCK_NO = "truck_no";
    private static final String DELIVERY_COMPANY = "company";
    private static final String DELIVERY_DATE = "date";
    private static final String DELIVERY_TIME = "time";
    private static final String DELIVERY_SYNC = "isSync";
    // **KEY CONSTANTS FOR SWP CODES

    private static final String SWP_CODES = "swp_codes";
    private static final String SWP_STATUS = "swp_status";
    private static final String SWP_DESCRIPTION = "swp_description";

    private String SERVICE_RETURN = "service_return";

    *//**
     * TABLE START DELIVERY
     * *//*

    private static final String TABLE_PREP_START = "prep_start";

    *//**
     * TABLE STARTDELIVERY KEYS CONSTANTS
     * *//*

    private static final String PREP_TRUCK_NO = "truck_no";
    // private static final String PREP_COMPANY = "company";
    private static final String PREP_DATE = "date";
    private static final String PREP_START_TIME = "start_time";

    *//**
     * MANAGED THE SKIPPED STOPS IN SKIP_STOP TABLE
     * *//*

    private static final String TABLE_Notification = "notification";

    *//**
     * fIELDS KEYS OF TABLE SKIP_STOP;
     * *//*

    private static final String Notification_ID = "id";
    private static final String Notification_STOP_No = "stop_no";

    private static final String Notification_Read = "read";*/

    private static final String CUST_AUTHCODE = "auth_code";

    public DataBaseHandler(Context context, String databaseName,
                           SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.v("cgtdb", "db handle oncreate :");

        // ** create table for AUTHENTICATE USER

        String CREATE_AUTHENTICATE_TABLE = "CREATE TABLE " + TABLE_AUTHENTICATE
                + "( " + AUTHENTICATE_USERNAME + " TEXT, "
                + AUTHENTICATE_LIVEAPI + " TEXT, " + AUTHENTICATE_TESTAPI
                + " TEXT, " + AUTHENTICATE_CLIENTCODE + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_AUTHENTICATE_TABLE);
        db.execSQL(CREATE_AUTHENTICATE_TABLE);

        // ** create table for waiver signature

       /* String CREATE_WAIVER_TABLE = "CREATE TABLE " + TABLE_WAIVERSIGNATURE
                + "( " + WAIVER_IMAGEPATH + " TEXT, " + WAIVER_STOPNO
                + " TEXT, " + WAIVER_FORMID + " TEXT, " + WAIVER_ACCEPT
                + " TEXT, " + WAIVER_TRUCKNO + " TEXT, " + WAIVER_CUSTID
                + " TEXT, " + WAIVER_ORDERNO + " TEXT, " + WAIVER_DATE
                + " TEXT, " + WAIVER_ISSYNC + " TEXT " + ")";
        Log.i("cgtdb", "create waivertable : " + CREATE_WAIVER_TABLE);
        db.execSQL(CREATE_WAIVER_TABLE);

        // ** create table for LOAD signature

        String CREATE_LOAD_SIGNATURE_TABLE = "CREATE TABLE "
                + TABLE_LOADSIGNATURE + "( " + LOAD_SIGNATUREIMAGEPATH
                + " TEXT, " + LOAD_SIGNATURETRUCKNO + " TEXT " + ")";
        Log.i("cgtdb", "create waivertable : " + CREATE_LOAD_SIGNATURE_TABLE);
        db.execSQL(CREATE_LOAD_SIGNATURE_TABLE);

        // ** create table for CHECK BOXES LOAD

        String CREATE_LOAD_CHECKBOX = "CREATE TABLE " + TABLE_LOADCHECKBOX
                + "( " + LOAD_CHECKBOXCHECKLIST + " TEXT, " + LOAD_CHECKBOXDATE
                + " TEXT, " + LOAD_CHECKBOXTRUCKNO + " TEXT, "
                + LOAD_CHECKBOXUID + " TEXT " + ")";
        Log.i("cgtdb", "create saved comment : " + CREATE_LOAD_CHECKBOX);
        db.execSQL(CREATE_LOAD_CHECKBOX);

        // ** create table for SAVED COMMENT

        String CREATE_SAVED_COMMENT = "CREATE TABLE " + TABLE_SAVEDCOMMNET
                + "( " + SAVED_COMMNET_CUSTID + " TEXT, " + SAVED_COMMNET_DATE
                + " TEXT, " + SAVED_COMMNET_ORDERNO + " TEXT, "
                + SAVED_COMMNET_TEXT + " TEXT, " + SAVED_COMMNET_TRUCKNO
                + " TEXT " + ")";
        Log.i("cgtdb", "create saved comment : " + CREATE_SAVED_COMMENT);
        db.execSQL(CREATE_SAVED_COMMENT);

        // ** create table for PREVIOUS COMMENT

        String CREATE_PREVIOUS_COMMENT = "CREATE TABLE "
                + TABLE_PREVIOUS_COMMENT + "( " + SAVED_COMMNET_CUSTID
                + " TEXT, " + SAVED_COMMNET_DATE + " TEXT, "
                + SAVED_COMMNET_ORDERNO + " TEXT, " + SAVED_COMMNET_TEXT
                + " TEXT, " + SAVED_COMMNET_TRUCKNO + " TEXT " + ")";
        Log.i("cgtdb", "create saved comment : " + CREATE_PREVIOUS_COMMENT);
        db.execSQL(CREATE_PREVIOUS_COMMENT);

        // ** create table for SAVED SURVEY

        String CREATE_SAVED_SURVEY = "CREATE TABLE " + TABLE_SAVEDSURVEY + "( "
                + SAVED_SURVEY_CUSTID + " TEXT, " + SAVED_SURVEY_ANS1
                + " TEXT, " + SAVED_SURVEY_ANS2 + " TEXT, " + SAVED_SURVEY_ANS3
                + " TEXT, " + SAVED_SURVEY_ANS4 + " TEXT, " + SAVED_SURVEY_ANS5
                + " TEXT, " + SAVED_SURVEY_ORDERNO + " TEXT, "
                + SAVED_survey_TEXT + " TEXT, " + SAVED_SURVEY_SIGNATURE
                + " TEXT, " + SAVED_SURVEY_TRUCKNO + " TEXT " + ")";
        Log.i("cgtdb", "create saved comment : " + CREATE_SAVED_SURVEY);
        db.execSQL(CREATE_SAVED_SURVEY);

        // ** create table for PREVIOUS SURVEY

        String CREATE_PREVIOUS_SURVEY = "CREATE TABLE " + TABLE_PREVIOUSSURVEY
                + "( " + SAVED_SURVEY_CUSTID + " TEXT, " + SAVED_SURVEY_ANS1
                + " TEXT, " + SAVED_SURVEY_ANS2 + " TEXT, " + SAVED_SURVEY_ANS3
                + " TEXT, " + SAVED_SURVEY_ANS4 + " TEXT, " + SAVED_SURVEY_ANS5
                + " TEXT, " + SAVED_SURVEY_ORDERNO + " TEXT, "
                + SAVED_survey_TEXT + " TEXT, " + SAVED_SURVEY_SIGNATURE
                + " TEXT, " + SAVED_SURVEY_TRUCKNO + " TEXT " + ")";
        Log.i("cgtdb", "create saved comment : " + CREATE_PREVIOUS_SURVEY);
        db.execSQL(CREATE_PREVIOUS_SURVEY);

        // ** create table for service codes

        String CREATE_SERVICE_TABLE = "CREATE TABLE " + TABLE_SERVICECODE
                + "( " + SERVICE_CODES + " TEXT, " + SERVICE_CAMERA + " TEXT, "
                + SERVICE_DESCRIPTION + " TEXT , " + SERVICE_RETURN + " TEXT "
                + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_SERVICE_TABLE);
        db.execSQL(CREATE_SERVICE_TABLE);

        // **CREATE TABLE FOR SURVEY QUESTIONS

        String CREATE_SURVEYQUESTION_TABLE = "CREATE TABLE "
                + TABLE_SURVEYQUESTIONS + "( " + SURVEY_QUESTIONS_QID
                + " TEXT, " + SURVEY_QUESTIONS_ORDER + " TEXT, "
                + SURVEY_QUESTIONS_QTYPE + " TEXT, "
                + SURVEY_QUESTIONS_QUESTION + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_SURVEYQUESTION_TABLE);
        db.execSQL(CREATE_SURVEYQUESTION_TABLE);

        // **CREATE TABLE FOR SURVEY QUESTIONS ANSWERS

        String CREATE_SURVEYQUESTIONANSWERS_TABLE = "CREATE TABLE "
                + TABLE_SURVEYQUESTIONSANSWERS + "( " + SURVEY_ANSWERS_ANSWER
                + " TEXT, " + SURVEY_ANSWERS_QID + " TEXT, "
                + SURVEY_ANSWERS_QUESTION + " TEXT, "
                + SURVEY_ANSWERS_CUSTOMERID + " TEXT, "
                + SURVEY_ANSWERS_ORDERNO + " TEXT, " + SURVEY_ANSWERS_STOPNO
                + " TEXT " + ")";

        Log.i("cgtdb", "create customerdetail : "
                + CREATE_SURVEYQUESTIONANSWERS_TABLE);
        db.execSQL(CREATE_SURVEYQUESTIONANSWERS_TABLE);

        // **CREATE TABLE FOR SURVEY QUESTIONS ANSWERS

        String CREATE_SURVEYQUESTIONANSWERSCHANGEDATE_TABLE = "CREATE TABLE "
                + TABLE_SURVEYQUESTIONSANSWERSCHANGEDATE + "( "
                + SURVEY_ANSWERS_ANSWER + " TEXT, " + SURVEY_ANSWERS_QID
                + " TEXT, " + SURVEY_ANSWERS_QUESTION + " TEXT, "
                + SURVEY_ANSWERS_CUSTOMERID + " TEXT, "
                + SURVEY_ANSWERS_ORDERNO + " TEXT, " + SURVEY_ANSWERS_STOPNO
                + " TEXT " + ")";
        db.execSQL(CREATE_SURVEYQUESTIONANSWERSCHANGEDATE_TABLE);

        String CREATE_REASON_TABLE = "CREATE TABLE " + TABLE_REASON_CODE + "( "
                + REASON_CODES + " TEXT, " + REASON_STATUS + " TEXT, "
                + REASON_DESCRIPTION + " TEXT, " + REASON_CAMERA + " TEXT "
                + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_REASON_TABLE);
        db.execSQL(CREATE_REASON_TABLE);

        // ** create table for reason codes for YE

        String CREATE_REASON_YE_TABLE = "CREATE TABLE " + TABLE_REASON_CODE_YE
                + "( " + REASON_CODES + " TEXT, " + REASON_STATUS + " TEXT, "
                + REASON_DESCRIPTION + " TEXT, " + REASON_CAMERA + " TEXT "
                + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_REASON_YE_TABLE);

        // ** create table for swp codes

        String CREATE_SWP_TABLE = "CREATE TABLE " + TABLE_SWP_CODES + "( "
                + SWP_CODES + " TEXT, " + SWP_STATUS + " TEXT, "
                + SWP_DESCRIPTION + " TEXT " + ")";
        Log.i("cgtdb", "create swp table : " + CREATE_SWP_TABLE);
        db.execSQL(CREATE_SWP_TABLE);*/

        // ** create table for reason codes for YE


      /*  db.execSQL(CREATE_REASON_YE_TABLE);*/

        // ** create table for DRIVER LIST

       /* String CREATE_DRIVERLIST_TABLE = "CREATE TABLE " + TABLE_DRIVERLIST
                + "( " + DRIVER_CODE + " TEXT, " + DRIVER_PHONE + " TEXT, "
                + DRIVER_NAME + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_DRIVERLIST_TABLE);
        db.execSQL(CREATE_DRIVERLIST_TABLE);

        String CREATE_HELPERLIST_TABLE = "CREATE TABLE " + TABLE_HELPERLIST
                + "( " + HELPER_CODE + " TEXT, " + HELPER_PHONE + " TEXT, "
                + HELPER_NAME + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_HELPERLIST_TABLE);
        db.execSQL(CREATE_HELPERLIST_TABLE);

        // ** create table for Checklist

        String CREATE_CHECKLIST_TABLE = "CREATE TABLE " + TABLE_CHECKLIST
                + "( " + CHECK_CODES + " TEXT, " + CHECK_STATUS + " TEXT, "
                + CHECK_DRIVERNAME + " TEXT, " + CHECK_DRIVERID + " TEXT, "
                + CHECK_DRIVERPHN + " TEXT, " + CHECK_COMPANY + " TEXT, "
                + CHECK_HELPER + " TEXT, " + CHECK_HELPERID + " TEXT, "
                + CHECK_SELECTED + " TEXT, " + CHECK_ACKNOWLEDGE + " TEXT, "
                + CHECK_BASEURL + " TEXT, " + CHECK_DESCRIPTION + " TEXT "
                + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_CHECKLIST_TABLE);
        db.execSQL(CREATE_CHECKLIST_TABLE);

        // ** create table for Savedchecklist

        String CREATE_SAVEDCHECKLIST_TABLE = "CREATE TABLE "
                + TABLE_SAVEDCHECKLIST + "( " + CHECK_CODES + " TEXT, "
                + CHECK_STATUS + " TEXT, " + CHECK_DRIVERNAME + " TEXT, "
                + CHECK_DRIVERPHN + " TEXT, " + CHECK_COMPANY + " TEXT, "
                + CHECK_HELPER + " TEXT, " + CHECK_SELECTED + " TEXT, "
                + CHECK_ACKNOWLEDGE + " TEXT, " + CHECK_BASEURL + " TEXT, "
                + CHECK_DESCRIPTION + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_SAVEDCHECKLIST_TABLE);
        db.execSQL(CREATE_SAVEDCHECKLIST_TABLE);

        // ** create table for reason codes

        String CREATE_STOPNO = "CREATE TABLE " + TABLE_StopNo + "( "
                + SAVED_STOPNO + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_STOPNO);
        db.execSQL(CREATE_STOPNO);

        // ** create table for communication counts 19 feb 2015

        String CREATE_COMMUNICATION_TABLE = "CREATE TABLE "
                + TABLE_COMMUNICATION_COUNT + "( " + COMMUNICATION_STOPNO
                + " TEXT, " + COMMUNICATION_COUNT + " TEXT, "
                + COMMUNICATION_TRUCKNO + " TEXT, " + COMMUNICATION_UNREAD
                + " TEXT " + ")";
        Log.i("techno", "create customerdetail : " + CREATE_COMMUNICATION_TABLE);
        db.execSQL(CREATE_COMMUNICATION_TABLE);

        // ** create table for JSON SURVEY

        String CREATE_JSONSURVEY_TABLE = "CREATE TABLE " + TABLE_JSONSURVEY
                + "( " + JSONSURVEY_CUSTID + " TEXT, " + JSONSURVEY_JSON
                + " TEXT, " + JSONSURVEY_STOPNO + " TEXT " + ")";
        Log.i("techno", "create customerdetail : " + CREATE_JSONSURVEY_TABLE);
        db.execSQL(CREATE_JSONSURVEY_TABLE);

        // **CREATE CUSTOMER TABLE
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_STOPS + "( "
                + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUST_NAME + " TEXT, " + CUST_CUSTID + " TEXT, "
                + CUST_ADDRESS + " TEXT, " + CUST_EMAILID + " TEXT, "
                + CUST_PHONE + " TEXT, " + CUST_ORDERNO + " TEXT, "
                + CUST_DESCRIPTION + " TEXT, " + CUST_TIMEFROM + " TEXT, "
                + CUST_TIMETO + " TEXT, " + CUST_STOP + " TEXT, "
                + CUST_STOPLOCK + " INTEGER, " + CUST_STOPSTATUS + " TEXT ,"
                + CUST_DELIVERYCODE + " TEXT, " + CUST_AUTHCODE + " TEXT "
                + ")";
        Log.d("cgtdb", "create customerdetail : " + CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_CUSTOMER_TABLE);

        // **CREATE ORDERITEM TABLE

        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_ITEMID + " TEXT, " + ITEM_DESCRIPTION + " TEXT, "
                + ITEM_LINENO + " TEXT, " + ITEM_QTY + " TEXT, " + ITEM_CUSTID
                + " TEXT, " + ITEM_ORDERNO + " TEXT, " + ITEM_SERVICECODE
                + " TEXT, " + ITEM_IMAGES + " TEXT, " + ITEM_URL + " TEXT, "
                + ITEM_STOPNO + " TEXT, " + ITEM_DATE + " TEXT, "
                + ITEM_LOADOUTSTATUS + " TEXT, " + ITEM_LOADOUTSTATUSCOMMENT
                + " TEXT, " + ITEM_STATUS + " TEXT, " + ITEM_SYNC
                + " INTEGER ," + ITEM_RE + " INTEGER " + ")";
        Log.d("cgtdb", "order customer : " + CREATE_ITEM_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);

        // **CREATE PREPCUSTOMER TABLE
        String CREATE_PREPCUSTOMER_TABLE = "CREATE TABLE " + TABLE_PREPSTOPS
                + "( " + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUST_NAME + " TEXT, " + CUST_CUSTID + " TEXT, "
                + CUST_ADDRESS + " TEXT, " + CUST_PHONE + " TEXT, "
                + CUST_ORDERNO + " TEXT, " + CUST_DESCRIPTION + " TEXT, "
                + CUST_TIMEFROM + " TEXT, " + CUST_TIMETO + " TEXT, "
                + CUST_STOP + " TEXT, " + CUST_STOPLOCK + " INTEGER, "
                + CUST_ITEM_COUNT + " TEXT, " + CUST_STOPSTATUS + " TEXT" + ")";
        Log.d("parul", "create customer : " + CREATE_PREPCUSTOMER_TABLE);
        db.execSQL(CREATE_PREPCUSTOMER_TABLE);

        // **CREATE PREPORDERITEM TABLE
        String CREATE_PREPITEM_TABLE = "CREATE TABLE " + TABLE_PREPDETAILS
                + "(" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_ITEMID + " TEXT, " + ITEM_DESCRIPTION + " TEXT, "
                + ITEM_LINENO + " TEXT, " + ITEM_QTY + " TEXT, " + ITEM_CUSTID
                + " TEXT, " + ITEM_ORDERNO + " TEXT, " + ITEM_SERVICECODE
                + " TEXT, " + ITEM_IMAGES + " TEXT, " + ITEM_URL + " TEXT, "
                + ITEM_STOPNO + " TEXT, " + ITEM__Load_Comment + " TEXT, "
                + ITEM_LOAD + " TEXT, " + ITEM__Load_REASONCODE + " TEXT, "
                + ITEM__Load_REASONDESCRIPTION + " TEXT, " + ITEM_SYNC
                + " INTEGER " + ")";
        Log.d("truck", "order customer : " + CREATE_PREPITEM_TABLE);
        db.execSQL(CREATE_PREPITEM_TABLE);

        // **CREATE LOAD TABLE
        String CREATE_LOAD_TABLE = "CREATE TABLE " + TABLE_LOAD + "( "
                + LOAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LOAD_TRUCKNO + " TEXT, " + LOAD_CUSTID + " TEXT, "
                + LOAD_ORDERNO + " TEXT, " + LOAD_ITEMID + " TEXT, "
                + LOAD_LINENO + " TEXT, " + LOAD_REASON_CODE + " TEXT, "
                + LOAD_REASON_COMMENT + " TEXT, " + LOAD_REASON_NO + " TEXT, "
                + LOAD_REASON_PICTURE + " TEXT, " + LOAD_USERNAME + " TEXT, "
                + LOAD_PASSWORD + " TEXT, " + LOAD_LOAD + " TEXT, " + LOAD_DATE
                + " TEXT " + ")";
        Log.d("load", "create load table : " + CREATE_LOAD_TABLE);
        db.execSQL(CREATE_LOAD_TABLE);

        // *//** get olddb

        String CREATE_OldDATA_TABLE = "CREATE TABLE " + TABLE_OLDSTOPS + "( "
                + OLDSTOP_TRUCKNO + " TEXT, " + OLDSTOP_ISLOCK + " TEXT, "
                + OLDSTOP_BTNINDEX + " INTEGER " + ")";

        db.execSQL(CREATE_OldDATA_TABLE);

        *//**
         * TABLE TO CONTAIN NEXT STOP DATA
         *//*
        String CREATE_NEXTSTOPCALL_TABLE = "CREATE TABLE " + TABLE_CALLNEXTSTOP
                + "(" + Tag_COMMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TAG_TRUCKNO + " TEXT, " + TAG_CUSTID + " TEXT, "
                + TAG_ORDERNO + " TEXT, " + TAG_STOPNO + " TEXT, "
                + TAG_CURRENTSTOP + " TEXT, " + TAG_BTNCLICK + " TEXT, "
                + TAG_CALLSTATUS + " TEXT, " + TAG_DELIVERYDATE + " TEXT "
                + ")";
        Log.d("anush", "create nextcall table : " + CREATE_NEXTSTOPCALL_TABLE);
        db.execSQL(CREATE_NEXTSTOPCALL_TABLE);

        *//****************************
         * TABLE TO CAONTAIN STOPNEXT BUTTON
         *//*
        String CREATE_NEXTSTOPBUTTON_TABLE = "CREATE TABLE "
                + TABLE_CALLNEXTBUTTON + "(" + Tag_COMMON_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAG_CURRENTSTOP
                + " TEXT, " + TAG_BTNCLICK + " TEXT " + ")";
        Log.d("anush", "create nextcall table : " + CREATE_NEXTSTOPBUTTON_TABLE);
        db.execSQL(CREATE_NEXTSTOPBUTTON_TABLE);

        // **CREATE IMAGE TABLE
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "( "
                + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IMAGE_TRUCKNO + " TEXT, " + IMAGE_CUSTID + " TEXT, "
                + IMAGE_ORDERNO + " TEXT, " + IMAGE_ITEMID + " TEXT, "
                + IMAGE_LINENO + " TEXT, " + IMAGE_IMAGEPATH + " TEXT, "
                + IMAGE_STATUS + " TEXT, " + IMAGE_STOPNO + " TEXT, "
                + IMAGE_DATE + " TEXT " + ")";
        Log.d("truck", "create image : " + CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);

        // ** CREATE TABLE LOAD IMAGE

        // // **CREATE IMAGE TABLE
        String cREATE_LoadImage = "CREATE TABLE " + TABLE_LoadIMAGE + "( "
                + LOADIMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LOADIMAGE_CUSTID + " TEXT, " + LOADIMAGE_ORDERNO + " TEXT, "
                + LOADIMAGE_ITEMID + " TEXT, " + LOADIMAGE_LINENO + " TEXT, "
                + LOADIMAGE_IMAGEPATH + " TEXT, " + LOADIMAGE_LAT + " TEXT, "
                + LOADIMAGE_LONG + " TEXT, " + LOADIMAGE_DATE + " TEXT " + ")";
        Log.d("truck", "create image : " + cREATE_LoadImage);
        db.execSQL(cREATE_LoadImage);

        // **CREATE IMAGE TABLE
        String CREATE_IMAGESYNC_TABLE = "CREATE TABLE " + TABLE_IMAGESYNC
                + "( " + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IMAGE_TRUCKNO + " TEXT, " + IMAGE_CUSTID + " TEXT, "
                + IMAGE_ORDERNO + " TEXT, " + IMAGE_ITEMID + " TEXT, "
                + IMAGE_LINENO + " TEXT, " + IMAGE_IMAGEPATH + " TEXT, "
                + IMAGE_STATUS + " TEXT, " + IMAGE_STOPNO + " TEXT, "
                + IMAGE_DATE + " TEXT " + ")";
        Log.d("imagetest", "create image : " + CREATE_IMAGESYNC_TABLE);
        db.execSQL(CREATE_IMAGESYNC_TABLE);

        // **CREATE IMAGE HOME TABLE
        String CREATE_IMAGEHOME_TABLE = "CREATE TABLE " + TABLE_IMAGE_HOME
                + "( " + IMAGEHOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IMAGEHOME_TRUCKNO + " TEXT, " + IMAGEHOME_CUSTID + " TEXT, "
                + IMAGEHOME_ORDERNO + " TEXT, " + IMAGEHOME_IMAGEPATH
                + " TEXT, " + IMAGEHOME_LAT + " TEXT, " + IMAGEHOME_LONG
                + " TEXT, " + IMAGEHOME_TIMESTAMP + " TEXT, "
                + IMAGEHOME_DELIVERYCODE + " TEXT, " + IMAGEHOME_DATE
                + " TEXT , " + IMAGEHOME_SYNC + " INTEGER " + ")";
        Log.d("truck", "create image : " + CREATE_IMAGEHOME_TABLE);
        db.execSQL(CREATE_IMAGEHOME_TABLE);

        // **CREATE IMAGE NEW TABLE
        String CREATE_IMAGE_NEW_TABLE = "CREATE TABLE " + TABLE_IMAGE_NEW
                + "( " + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IMAGE_TRUCKNO + " TEXT, " + IMAGE_CUSTID + " TEXT, "
                + IMAGE_ORDERNO + " TEXT, " + IMAGE_ITEMID + " TEXT, "
                + IMAGE_LINENO + " TEXT, " + IMAGE_IMAGEPATH + " TEXT, "
                + IMAGE_DATE + " TEXT " + ")";
        Log.d("cgtdb", "create image : " + CREATE_IMAGE_NEW_TABLE);
        db.execSQL(CREATE_IMAGE_NEW_TABLE);

        // **CREATE STOPSTATUS TABLE
        String CREATE_STOPSTATUS_TABLE = "CREATE TABLE " + TABLE_STOPSTATUS
                + "( " + STOPSTATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STOPSTATUS_TRUCKNO + " TEXT, " + STOPSTATUS_STOPNO
                + " TEXT, " + STOPSTATUS_STATUS + " TEXT, " + STOPSTATUS_DATE
                + " TEXT, " + STOPSTATUS_STOPSTATUSVALUE + " TEXT, "
                + STOPSTATUS_STATUSSYNC + " TEXT, " + STOPSTATUS_TIME
                + " TEXT " + ")";
        Log.d("truck", "create stopstatus : " + CREATE_STOPSTATUS_TABLE);
        db.execSQL(CREATE_STOPSTATUS_TABLE);

        *//****
         * Create table for Line comment that shows
         *//*

        // **CREATE STOPSTATUS TABLE
        String CREATE_LINECOMMENT_TABLE = "CREATE TABLE "
                + TABLE_SAVELINECOMMENT + "( " + LINECOMMENT_ITEMID + " TEXT, "
                + LINECOMMENT_LINENO + " TEXT, " + LINECOMMENT_COMMENT
                + " TEXT " + ")";
        Log.d("truck", "create stopstatus : " + CREATE_LINECOMMENT_TABLE);
        db.execSQL(CREATE_LINECOMMENT_TABLE);

        *//*************************************************
         * This table is created to handle line comments
         *//*

        String CREATE_COMMONNEW_TABLE = "CREATE TABLE " + TABLE_COMMONNEW
                + "( " + COMMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COMMON_CUSTID + " TEXT, " + COMMON_ORDERNO + " TEXT, "
                + COMMON_TRUCKNO + " TEXT, " + COMMON_DATE + " TEXT, "
                + COMMON_TYPE + " INTEGER, " + COMMON_SYNC + " INTEGER, "
                + COMMENT + " TEXT, " + COMMENT_ITEMID + " TEXT, "
                + COMMENT_LINENO + " TEXT, " + COMMENT_SERVICECODE + " TEXT, "
                + COMMENT_STOPNO + " TEXT, " + COMMENT_STATUS + " TEXT, "
                + SIGNATURE + " TEXT, " + QUESTION1 + " TEXT, " + ANSWER1
                + " TEXT, " + QUESTION2 + " TEXT, " + ANSWER2 + " TEXT, "
                + QUESTION3 + " TEXT, " + ANSWER3 + " TEXT, " + QUESTION4
                + " TEXT, " + ANSWER4 + " TEXT, " + QUESTION5 + " TEXT, "
                + ANSWER5 + " TEXT, " + QUESTION6 + " TEXT, " + NOTE + " TEXT "
                + COMMON_URLSERVICE + "TEXT" + ")";

        Log.d("cgtdb", "create common new table : " + CREATE_COMMONNEW_TABLE);
        db.execSQL(CREATE_COMMONNEW_TABLE);

        // **CREATE COMMON TABLE
        // TYPE ==> 1 for COMMENT, 2 for SIGNATUE, 3 for SURVEY

        String CREATE_COMMON_TABLE = "CREATE TABLE " + TABLE_COMMON + "( "
                + COMMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COMMON_CUSTID + " TEXT, " + COMMON_ORDERNO + " TEXT, "
                + COMMON_TRUCKNO + " TEXT, " + COMMON_DATE + " TEXT, "
                + COMMON_TYPE + " INTEGER, " + COMMON_SYNC + " INTEGER, "
                + COMMENT + " TEXT, " + COMMENT_ITEMID + " TEXT, "
                + COMMENT_LINENO + " TEXT, " + COMMENT_SERVICECODE + " TEXT, "
                + COMMENTSYNC_STATUS + " TEXT, " + COMMENTBIG_STOPNO
                + " TEXT, " + SIGNATURE + " TEXT, " + QUESTION1 + " TEXT, "
                + ANSWER1 + " TEXT, " + QUESTION2 + " TEXT, " + ANSWER2
                + " TEXT, " + QUESTION3 + " TEXT, " + ANSWER3 + " TEXT, "
                + QUESTION4 + " TEXT, " + ANSWER4 + " TEXT, " + QUESTION5
                + " TEXT, " + ANSWER5 + " TEXT, " + QUESTION6 + " TEXT, "
                + NOTE + " TEXT " + COMMON_URLSERVICE + "TEXT" + ")";

        Log.d("comment", "create common : " + CREATE_COMMON_TABLE);
        db.execSQL(CREATE_COMMON_TABLE);

        *//*************************************************
         * This table is created to handle email data
         *//*

        String CREATE_EMAIL_TABLE = "CREATE TABLE " + TABLE_EMAIL + "( "
                + EMAIL_COMMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_CUSTID + " TEXT, " + EMAIL_ORDERNO + " TEXT, "
                + EMAIL_TYPE + " INTEGER, " + EMAIL_TRUCKNO + " TEXT, "
                + EMAIL_STOPNO + " TEXT, " + EMAIL_DATE + " TEXT, " + EMAIL_ID
                + " TEXT " + ")";

        Log.d("comment", "create common new table : " + CREATE_EMAIL_TABLE);
        db.execSQL(CREATE_EMAIL_TABLE);

        *//*************************************************
         * This table is created to handle email data for show in email textbox
         *//*

        String CREATE_EMAIL_TABLENEW = "CREATE TABLE " + TABLE_EMAILNEW + "( "
                + EMAIL_COMMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_CUSTID + " TEXT, " + EMAIL_ORDERNO + " TEXT, "
                + EMAIL_TYPE + " INTEGER, " + EMAIL_TRUCKNO + " TEXT, "
                + EMAIL_STOPNO + " TEXT, " + EMAIL_DATE + " TEXT, " + EMAIL_ID
                + " TEXT " + ")";

        Log.d("comment", "create common new table : " + CREATE_EMAIL_TABLENEW);
        db.execSQL(CREATE_EMAIL_TABLENEW);

        // Archana work

        // **CREATE NEWPREP CUSTOMER TABLE
        String CREATE_NEWPREP_CUSTOMER_TABLE = "CREATE TABLE "
                + NEWPREP_TABLE_STOPS + "( " + NEWPREP_CUSTOMER_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NEWPREP_CUST_NAME
                + " TEXT, " + NEWPREP_CUST_CUSTID + " TEXT, "
                + NEWPREP_CUST_ADDRESS + " TEXT, " + NEWPREP_CUST_PHONE
                + " TEXT, " + NEWPREP_CUST_ORDERNO + " TEXT, "
                + NEWPREP_CUST_TIMEFROM + " TEXT, " + NEWPREP_CUST_TIMETO
                + " TEXT, " + NEWPREP_CUST_TEAM + " TEXT, "
                + NEWPREP_CUST_PIECES + " TEXT, " + NEWPREP_CUST_STOPNO
                + " TEXT " + ")";
        Log.d("db", "create NEWPREP customerdetail : "
                + CREATE_NEWPREP_CUSTOMER_TABLE);
        db.execSQL(CREATE_NEWPREP_CUSTOMER_TABLE);

        // **CREATE NEWPREP ITEMDETAILS TABLE

        String CREATE_NEWPREP_ITEM_TABLE = "CREATE TABLE "
                + NEWPREP_TABLE_DETAILS + "(" + NEWPREP_ITEM_NO
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NEWPREP_ITEM_ITEMNO
                + " TEXT, " + NEWPREP_ITEM_DESCRIPTION + " TEXT, "
                + NEWPREP_ITEM_LINENO + " TEXT, " + NEWPREP_ITEM_BARCODE
                + " TEXT, " + NEWPREP_ITEM__ASMBL + " TEXT, "
                + NEWPREP_ITEM__SWP + " TEXT, " + NEWPREP_ITEM_BED + " TEXT, "
                + NEWPREP_ITEM_CG + " TEXT, " + NEWPREP_ITEM_CHAIRS + " TEXT, "
                + NEWPREP_ITEM_DAMAGED + " TEXT, " + NEWPREP_ITEM_STOPNO
                + " TEXT, " + NEWPREP_ITEM_HARDWARE + " TEXT, "
                + NEWPREP_ITEM_UPH + " TEXT, " + NEWPREP_ITEM_NOTES + " TEXT, "
                + NEWPREP_ITEM_SYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_BEDSYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_ASMBLSYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_SWPSYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_CHAIRSSYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_HARDWARESYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_CGSYNCSTATUS + " TEXT, "
                + NEWPREP_ITEM_UPHSYNCSTATUS + " TEXT, " + NEWPREP_ITEM_REASON
                + " TEXT " + ")";
        Log.d("db", "NEWPREP order customer : " + CREATE_NEWPREP_ITEM_TABLE);
        db.execSQL(CREATE_NEWPREP_ITEM_TABLE);

        // ** create NEWPREP table for teams
        String CREATE_NEWPREP_TEAM_TABLE = "CREATE TABLE "
                + NEWPREP_TABLE_TEAMS + "( " + NEWPREP_TEAM_TEAMID + " TEXT, "
                + NEWPREP_TEAM_NAME + " TEXT " + ")";
        Log.i("db", "create team table : " + CREATE_NEWPREP_TEAM_TABLE);
        db.execSQL(CREATE_NEWPREP_TEAM_TABLE);

        // ** create NEWPREP table for team employee
        String CREATE_NEWPREP_TEAMEMP_TABLE = "CREATE TABLE "
                + NEWPREP_TABLE_TEAM_EMPLOYEES + "( " + NEWPREP_TEAMEMP_WHCODE
                + " TEXT, " + NEWPREP_TEAMEMP_WHNAME + " TEXT, "
                + NEWPREP_TEAMEMP_SELECTED + " TEXT, "
                + NEWPREP_TEAMEMP_CONTACTID + " TEXT, "
                + NEWPREP_TEAMEMP_TEAMNAME + " TEXT, " + NEWPREP_TEAMEMP_TEAMID
                + " TEXT " + ")";
        Log.i("db", "create NEWPREP team employee table : "
                + CREATE_NEWPREP_TEAMEMP_TABLE);
        db.execSQL(CREATE_NEWPREP_TEAMEMP_TABLE);

        // ** create NEWPREP table for selected employee
        String CREATE_NEWPREP_SELECTEDEMP_TABLE = "CREATE TABLE "
                + NEWPREP_TABLE_SELECTED_EMPLOYEES + "( "
                + NEWPREP_SELECTEDEMP_WHCODE + " TEXT, "
                + NEWPREP_SELECTEDEMP_WHNAME + " TEXT, "
                + NEWPREP_SELECTEDEMP_SELECTED + " TEXT, "
                + NEWPREP_SELECTEDEMP_CONTACTID + " TEXT, "
                + NEWPREP_SELECTEDEMP_TEAMNAME + " TEXT, "
                + NEWPREP_SELECTEDEMP_STOPNO + " TEXT, "
                + NEWPREP_SELECTEDEMP_TEAMID + " TEXT " + ")";
        Log.i("db", "create selected employee table : "
                + CREATE_NEWPREP_SELECTEDEMP_TABLE);
        db.execSQL(CREATE_NEWPREP_SELECTEDEMP_TABLE);

        // ** create table for STARTDELIVERY

        String CREATE_STARTDELIVERY_TABLE = "CREATE TABLE "
                + TABLE_START_DELIVERY + "( " + DELIVERY_COMPANY + " TEXT, "
                + DELIVERY_TRUCK_NO + " TEXT, " + DELIVERY_DATE + " TEXT, "
                + DELIVERY_TIME + " TEXT, " + DELIVERY_SYNC + " INTEGER " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_STARTDELIVERY_TABLE);
        db.execSQL(CREATE_STARTDELIVERY_TABLE);

        // **CREATE Skip Stop TABLE
        String CREATE_SKIP_STOP_TABLE = "CREATE TABLE " + TABLE_SKIP_STOP + "("
                + SKIP_STOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SKIP_STOP_STOP_ID + " INTEGER, " + SKIP_TRUCK_NO + " TEXT, "
                + SKIP_STOP_IS_PENDING + " INTEGER, " + SKIP_STOP_IS_FINISHED
                + " INTEGER " + ")";
        Log.d("truck", "CREATE_SKIP_STOP_TABLE : " + CREATE_SKIP_STOP_TABLE);
        db.execSQL(CREATE_SKIP_STOP_TABLE);

        String CREATE_PREP_START_TABLE = "CREATE TABLE " + TABLE_PREP_START
                + "( " + PREP_TRUCK_NO + " TEXT, " + PREP_DATE + " TEXT, "
                + PREP_START_TIME + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_PREP_START_TABLE);
        db.execSQL(CREATE_PREP_START_TABLE);

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_Notification
                + "( " + Notification_ID + " TEXT, " + Notification_Read
                + " INTEGER, " + Notification_STOP_No + " TEXT " + ")";
        Log.i("cgtdb", "create customerdetail : " + CREATE_NOTIFICATION_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);*/

        // //////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_Notification);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OLDSTOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLNEXTSTOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKIP_STOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLNEXTBUTTON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LoadIMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGESYNC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE_HOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE_NEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPSTATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMONNEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVELINECOMMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMON);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILNEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREPSTOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREPDETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICECODE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REASON_CODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SWP_CODES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REASON_CODE_YE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVERLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HELPERLIST);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVEDCHECKLIST);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHENTICATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAIVERSIGNATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOADSIGNATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOADCHECKBOX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMUNICATION_COUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVEDCOMMNET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREVIOUS_COMMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVEDSURVEY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREVIOUSSURVEY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_StopNo);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEYQUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEYQUESTIONSANSWERS);

        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_SURVEYQUESTIONSANSWERSCHANGEDATE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JSONSURVEY);

        //
        db.execSQL("DROP TABLE IF EXISTS " + NEWPREP_TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + NEWPREP_TABLE_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + NEWPREP_TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + NEWPREP_TABLE_TEAM_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + NEWPREP_TABLE_SELECTED_EMPLOYEES);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_START_DELIVERY);*/
        // /////////////////////////////////////
        onCreate(db);
    }

    /***************
     * this will clear all data base from itrack db
     */
    public void ClearAllDB() {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            synchronized (db) {

               /* String delCustomer = "DELETE FROM " + TABLE_STOPS;

                String delOrder = "DELETE FROM " + TABLE_DETAILS;

                String delnextstop = "DELETE FROM " + TABLE_CALLNEXTSTOP;
                String delnextstopbtn = "DELETE FROM " + TABLE_CALLNEXTBUTTON;
                String delImage = "DELETE FROM " + TABLE_IMAGE;
                String delImagesync = "DELETE FROM " + TABLE_IMAGESYNC;
                String delImagehome = "DELETE FROM " + TABLE_IMAGE_HOME;
                String delImagenew = "DELETE FROM " + TABLE_IMAGE_NEW;
                String delStopStatus = "DELETE FROM " + TABLE_STOPSTATUS;
                String delCommonNew = "DELETE FROM " + TABLE_COMMONNEW;
                String delCommon = "DELETE FROM " + TABLE_COMMON;

                String delemail = "DELETE FROM " + TABLE_EMAIL;
                String delemailnew = "DELETE FROM " + TABLE_EMAILNEW;
                String delPrepCustomer = "DELETE FROM " + TABLE_PREPSTOPS;
                String delPrepOrder = "DELETE FROM " + TABLE_PREPDETAILS;
                String delLoad = "DELETE FROM " + TABLE_LOAD;
                String delServicecodes = "DELETE FROM " + TABLE_SERVICECODE;

                String delreasoncodes = "DELETE FROM " + TABLE_REASON_CODE;
                String delswpcodes = "DELETE FROM " + TABLE_SWP_CODES;
                String delreasoncodesYE = "DELETE FROM " + TABLE_REASON_CODE_YE;

                String delstopno = "DELETE FROM " + TABLE_StopNo;

                String delolddata = "DELETE FROM " + TABLE_OLDSTOPS;
                String delwaiver = "DELETE FROM " + TABLE_WAIVERSIGNATURE;

                String delsavedcomment = "DELETE FROM " + TABLE_SAVEDCOMMNET;
                String delpreviouscomment = "DELETE FROM "
                        + TABLE_PREVIOUS_COMMENT;
                String delsavedsurvey = "DELETE FROM " + TABLE_SAVEDSURVEY;
                String delprevioussurvey = "DELETE FROM "
                        + TABLE_PREVIOUSSURVEY;

                String delloadimage = "DELETE FROM " + TABLE_LoadIMAGE;
                String delchecklist = "DELETE FROM " + TABLE_CHECKLIST;

                String delsavedchecklist = "DELETE FROM "
                        + TABLE_SAVEDCHECKLIST;

                String delloadsignature = "DELETE FROM " + TABLE_LOADSIGNATURE;
                String delloadcheckbox = "DELETE FROM " + TABLE_LOADCHECKBOX;

                String deldriverlist = "DELETE FROM " + TABLE_DRIVERLIST;
                String delhelperlist = "DELETE FROM " + TABLE_HELPERLIST;

                String delsavelinecomment = "DELETE FROM "
                        + TABLE_SAVELINECOMMENT;

                String delsurveyquestions = "DELETE FROM "
                        + TABLE_SURVEYQUESTIONS;

                String delsurveyquestionsanswer = "DELETE FROM "
                        + TABLE_SURVEYQUESTIONSANSWERS;

                String delsurveyquestionsanswerchangedate = "DELETE FROM "
                        + TABLE_SURVEYQUESTIONSANSWERSCHANGEDATE;

                String deljson = "DELETE FROM " + TABLE_JSONSURVEY;

                String delcommunicationcount = "DELETE FROM "
                        + TABLE_COMMUNICATION_COUNT;

                //

                String NEWPREP_delCustomer = "DELETE FROM "
                        + NEWPREP_TABLE_STOPS;
                db.execSQL(NEWPREP_delCustomer);

                String NEWPREP_delDetails = "DELETE FROM "
                        + NEWPREP_TABLE_DETAILS;
                db.execSQL(NEWPREP_delDetails);

                String NEWPREP_delTeams = "DELETE FROM " + NEWPREP_TABLE_TEAMS;
                db.execSQL(NEWPREP_delTeams);

                String NEWPREP_delTeamEmp = "DELETE FROM "
                        + NEWPREP_TABLE_TEAM_EMPLOYEES;
                db.execSQL(NEWPREP_delTeamEmp);

                String NEWPREP_delSelEmp = "DELETE FROM "
                        + NEWPREP_TABLE_SELECTED_EMPLOYEES;
                db.execSQL(NEWPREP_delSelEmp);

                String del_SKIPSTOPTABLE = "DELETE FROM " + TABLE_SKIP_STOP;
                // // ///////////////////////////////////////////////////
                db.execSQL(del_SKIPSTOPTABLE);
                String delNotification = "DELETE FROM " + TABLE_Notification;
                db.execSQL(delNotification);
                db.execSQL(delswpcodes);
                db.execSQL(deljson);
                db.execSQL(delsurveyquestionsanswer);
                db.execSQL(delsurveyquestionsanswerchangedate);
                db.execSQL(delsurveyquestions);
                db.execSQL(delsavelinecomment);
                db.execSQL(deldriverlist);
                db.execSQL(delhelperlist);
                db.execSQL(delsavedchecklist);
                db.execSQL(delloadcheckbox);
                db.execSQL(delloadsignature);
                db.execSQL(delchecklist);
                db.execSQL(delloadimage);
                db.execSQL(delreasoncodesYE);
                db.execSQL(delstopno);
                db.execSQL(delprevioussurvey);
                db.execSQL(delpreviouscomment);
                db.execSQL(delsavedsurvey);
                db.execSQL(delsavedcomment);
                db.execSQL(delreasoncodes);
                db.execSQL(delCustomer);
                db.execSQL(delOrder);
                db.execSQL(delnextstop);
                db.execSQL(delnextstopbtn);
                db.execSQL(delImage);
                db.execSQL(delImagesync);
                db.execSQL(delImagenew);
                db.execSQL(delStopStatus);
                db.execSQL(delCommonNew);
                db.execSQL(delCommon);
                db.execSQL(delemail);
                db.execSQL(delemailnew);
                db.execSQL(delPrepCustomer);
                db.execSQL(delPrepOrder);
                db.execSQL(delLoad);
                db.execSQL(delServicecodes);
                db.execSQL(delolddata);
                db.execSQL(delwaiver);
                db.execSQL(delImagehome);
                db.execSQL(delcommunicationcount);*/
            }

        } finally {
            if (db != null && db.isOpen()) {
                // db.close();
            }

        }
    }

    // ** INSER VALUSE IN AUTHENTICATE TABLE

    public long insertAuthenticateuser(AuthenticateModel mAuthenticateModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues authenticateValues = new ContentValues();

        authenticateValues.put(AUTHENTICATE_USERNAME,
                mAuthenticateModel.getName());
        authenticateValues.put(AUTHENTICATE_LIVEAPI,
                mAuthenticateModel.getLiveapiurl());
        authenticateValues.put(AUTHENTICATE_TESTAPI,
                mAuthenticateModel.getTestapiurl());
        authenticateValues.put(AUTHENTICATE_CLIENTCODE,
                mAuthenticateModel.getClientcode());

        long rowId = db.insert(TABLE_AUTHENTICATE, null, authenticateValues);

        return rowId;
    }

    // ** INSER VALUSE IN WAIVER SIGNATURE TABLE

   /* public long insertWaiverSignature(String stopno, String ImagePath,
                                      String fromid, String accept, String truckno, String custid,
                                      String orderno, String date, String issync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues authenticateValues = new ContentValues();

        authenticateValues.put(WAIVER_STOPNO, stopno);
        authenticateValues.put(WAIVER_IMAGEPATH, ImagePath);
        authenticateValues.put(WAIVER_FORMID, fromid);
        authenticateValues.put(WAIVER_ACCEPT, accept);
        authenticateValues.put(WAIVER_TRUCKNO, truckno);
        authenticateValues.put(WAIVER_CUSTID, custid);
        authenticateValues.put(WAIVER_ORDERNO, orderno);
        authenticateValues.put(WAIVER_DATE, date);
        authenticateValues.put(WAIVER_ISSYNC, issync);

        long rowId = db.insert(TABLE_WAIVERSIGNATURE, null, authenticateValues);

        return rowId;
    }

    // ** INSER VALUSE IN LOAD SIGNATURE TABLE

    public long insertLOADSignature(String ImagePath, String truckno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues authenticateValues = new ContentValues();

        authenticateValues.put(LOAD_SIGNATUREIMAGEPATH, ImagePath);

        authenticateValues.put(LOAD_SIGNATURETRUCKNO, truckno);

        long rowId = db.insert(TABLE_LOADSIGNATURE, null, authenticateValues);

        return rowId;
    }

    // ** INSERT VALUSE IN LOAD CHECKBOX TABLE

    public long insertCheckbox(String date, String truckno, String checklist,
                               String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues authenticateValues = new ContentValues();

        authenticateValues.put(LOAD_CHECKBOXTRUCKNO, truckno);

        authenticateValues.put(LOAD_CHECKBOXCHECKLIST, checklist);
        authenticateValues.put(LOAD_CHECKBOXDATE, date);
        authenticateValues.put(LOAD_CHECKBOXUID, deviceid);

        long rowId = db.insert(TABLE_LOADCHECKBOX, null, authenticateValues);

        return rowId;
    }


    // ** INSER VALUSE IN SURVEY ANSWERS TABLE

    public long insertSAVEDSurveyAnswers(String custid, String stopno,
                                         String orderno, String qid, String answers, String questions) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues surveyanswervalue = new ContentValues();

        surveyanswervalue.put(SURVEY_ANSWERS_ANSWER, answers);
        surveyanswervalue.put(SURVEY_ANSWERS_CUSTOMERID, custid);
        surveyanswervalue.put(SURVEY_ANSWERS_QID, qid);
        surveyanswervalue.put(SURVEY_ANSWERS_QUESTION, questions);
        surveyanswervalue.put(SURVEY_ANSWERS_STOPNO, stopno);
        surveyanswervalue.put(SURVEY_ANSWERS_ORDERNO, orderno);

        long rowId = db.insert(TABLE_SURVEYQUESTIONSANSWERS, null,
                surveyanswervalue);

        return rowId;
    }


    // ** INSER VALUSE IN savedstopno TABLE

    public long insertsavedstopno(String Stopno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stopnovalue = new ContentValues();

        stopnovalue.put(SAVED_STOPNO, Stopno);

        long rowId = db.insert(TABLE_StopNo, null, stopnovalue);

        return rowId;
    }

    // **INSERT VALUES IN LOAD TABLE
    public long insertLoadEntry(String truckno, String custid, String itemid,
                                String lineno, String orderno, String load, String deliverydate,
                                String reasoncode, String reasoncomment, String isNoSelected,
                                String imagepath, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues loadValues = new ContentValues();
        loadValues.put(LOAD_CUSTID, custid);
        loadValues.put(LOAD_DATE, deliverydate);
        loadValues.put(LOAD_LOAD, load);
        loadValues.put(LOAD_ITEMID, itemid);
        loadValues.put(LOAD_LINENO, lineno);
        loadValues.put(LOAD_ORDERNO, orderno);
        loadValues.put(LOAD_TRUCKNO, truckno);
        loadValues.put(LOAD_USERNAME, username);
        loadValues.put(LOAD_PASSWORD, password);
        loadValues.put(LOAD_REASON_CODE, reasoncode);

        if (reasoncomment != null) {
            loadValues.put(LOAD_REASON_COMMENT, reasoncomment);
            loadValues.put(LOAD_REASON_PICTURE, imagepath);

        } else {
            loadValues.put(LOAD_REASON_COMMENT, "");
            loadValues.put(LOAD_REASON_PICTURE, "");
        }

        loadValues.put(LOAD_REASON_NO, isNoSelected);

        Log.v("technology", "data in db is :" + isNoSelected);

        long rowId = db.insert(TABLE_LOAD, null, loadValues);
        // db.close();
        return rowId;
    }

    // **Insert line comment to show

    // **INSERT VALUES IN COMMON TABLE FOR LINECOMMENT
    public long insertLineComment(String comment, String itemid, String lineno) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues commentValues = new ContentValues();
        commentValues.put(LINECOMMENT_ITEMID, itemid);
        commentValues.put(LINECOMMENT_LINENO, lineno);
        commentValues.put(LINECOMMENT_COMMENT, comment);

        Log.v("sushil", "insert comment is :" + comment);

        long rowId = db.insert(TABLE_SAVELINECOMMENT, null, commentValues);

        // db.close();
        return rowId;
    }

    // **INSERT VALUES IN COMMON TABLE FOR COMMENT, HERE TYPE IS 1

    // in this method inserting driver comments


    // **INSERT VALUES IN SAVED COMMENT TABLE
    public long insertSavedcomment(String truckno, String comment,
                                   String dates, String custID, String orderno) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues commentValues = new ContentValues();
        commentValues.put(SAVED_COMMNET_CUSTID, custID);
        commentValues.put(SAVED_COMMNET_DATE, dates);
        commentValues.put(SAVED_COMMNET_ORDERNO, orderno);

        commentValues.put(SAVED_COMMNET_TRUCKNO, truckno);

        commentValues.put(SAVED_COMMNET_TEXT, comment);

        long rowId = db.insert(TABLE_SAVEDCOMMNET, null, commentValues);

        // db.close();
        return rowId;
    }

    // **INSERT VALUES IN PREVIOUS COMMENT TABLE
    public long insertPreviouscomment(String truckno, String comment,
                                      String dates, String custID, String orderno) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues commentValues = new ContentValues();
        commentValues.put(SAVED_COMMNET_CUSTID, custID);
        commentValues.put(SAVED_COMMNET_DATE, dates);
        commentValues.put(SAVED_COMMNET_ORDERNO, orderno);

        commentValues.put(SAVED_COMMNET_TRUCKNO, truckno);

        commentValues.put(SAVED_COMMNET_TEXT, comment);

        long rowId = db.insert(TABLE_PREVIOUS_COMMENT, null, commentValues);

        // db.close();
        return rowId;
    }*/


    /***
     *
     * */



    // **GET ALL DETAILS OF AUTHENTICATION
    public ArrayList<AuthenticateModel> getAuthenticate() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<AuthenticateModel> mArrayList = new ArrayList<AuthenticateModel>();

        String query = "SELECT * FROM " + TABLE_AUTHENTICATE;

        Log.d("cgtdb", "query : " + query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                AuthenticateModel mAuthenticateModel = new AuthenticateModel();
                try {

                    mAuthenticateModel.setName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AUTHENTICATE_USERNAME)));
                    mAuthenticateModel.setLiveapiurl(cursor.getString(cursor
                            .getColumnIndexOrThrow(AUTHENTICATE_LIVEAPI)));
                    mAuthenticateModel.setTestapiurl(cursor.getString(cursor
                            .getColumnIndexOrThrow(AUTHENTICATE_TESTAPI)));
                    mAuthenticateModel.setClientcode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AUTHENTICATE_CLIENTCODE)));

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
                mArrayList.add(mAuthenticateModel);
            }
        } else {
            return null;
        }

        cursor.close();
        if (cursor.isClosed())
            cursor.deactivate();
        // db.close();
        return mArrayList;
    }


}
