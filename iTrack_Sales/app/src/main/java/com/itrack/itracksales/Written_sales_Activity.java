package com.itrack.itracksales;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.itrack.Adapter.NLevelItem;
import com.itrack.Async.Sales_Async;
import com.itrack.ConstantClass.ItrackConstant;
import com.itrack.Interface.Authenticate_Interface;
import com.itrack.Interface.NLevelView;
import com.itrack.model.TotalAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

import static com.itrack.itracksales.R.id.listView2;


public class Written_sales_Activity extends Activity implements Authenticate_Interface {

    List<NLevelItem> list;
    List<NLevelItem> list1;
    ListView listView;
    ListView listview2;
    private Prep_SharedPreferences mSharedPreferences;
    TextView output, tv1, tv;
    View clickSource;
    View touchSource;
    int offset = 0;
    LinearLayout ll;
    LinearLayout llTotal;
    LinearLayout lvheader;
    LinearLayout lvheaderValue;
    HorizontalScrollView horizontal_view;
    private int STORAGE_PERMISSION_CODE = 23;
    ImageView mIvRefresh, mIvLogout;
    String data = "";
    static int levelNo = 0;
    final String[] Colors = {"#DCDD87", "#EBE4B8", "#D4E2D5", "#F5F5F5", "#B2BABB", "#A0DECB", "#CBF1E6", "#F2D7D5", "#CBDAF1", "#E0CBF1", "#B5EEF0"};
    String jsonData;
    int list_flag = 1;

    /*TextView mTvPrtection, mTvOps, mTvSales, mTvMargn, MtvCount, mTvAVGsales;*/
    TotalAll MdlTotalAll;
    Typeface face;
    Authenticate_Interface mAuthenticate_Interface;
    String[] HeaderName, TotalAllValues, DataProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_written_sales);

        listView = (ListView) findViewById(R.id.listView1);
        listview2 = (ListView) findViewById(listView2);
        horizontal_view = (HorizontalScrollView) findViewById(R.id.horizontal_view);
        lvheader = (LinearLayout) findViewById(R.id.lvdemo);
        mIvRefresh = (ImageView) findViewById(R.id.iv_refresh);
        mIvLogout = (ImageView) findViewById(R.id.iv_log_out);
        llTotal = (LinearLayout) findViewById(R.id.lv_tot_all);
        ll = (LinearLayout) findViewById(R.id.lv_tot_left);
        tv1 = new TextView(this);
        tv = new TextView(this);
        face = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        mSharedPreferences = new Prep_SharedPreferences(Written_sales_Activity.this);
        // Toast.makeText(getApplicationContext(), "User Login Status: " + mSharedPreferences.isLoggedIn(), Toast.LENGTH_LONG).show();
        mIvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences.logoutUser();
            }
        });

        mAuthenticate_Interface = this;

        mIvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If permission is already having then showing the toast
                ListRefresh();

            }

            private void ListRefresh() {
                try {
                    list.clear();
                    ll.removeAllViews();
                    llTotal.removeAllViews();
                    lvheader.removeAllViews();
                    lvheaderValue.removeAllViews();
                    //  Toast.makeText(Written_sales_Activity.this, "" + list.size(), Toast.LENGTH_SHORT).show();
                    Sales_Async mSales_Async = new Sales_Async(
                            Written_sales_Activity.this,
                            ItrackConstant.baseUrl + ItrackConstant.GetDailySalesDataReport,
                            mAuthenticate_Interface);
                    mSales_Async.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (touchSource == null)
                    touchSource = v;

                if (v == touchSource) {
                    listview2.dispatchTouchEvent(event);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        clickSource = v;
                        touchSource = null;
                    }
                }
                return false;
            }
        });
        listview2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (touchSource == null)
                    touchSource = v;

                if (v == touchSource) {
                    listView.dispatchTouchEvent(event);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        clickSource = v;
                        touchSource = null;
                    }
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Do something with the ListView was clicked
                int id1 = parent.getId();
                if (parent == clickSource) {
                    ((NLevelAdapter) listView.getAdapter()).toggle(position);
                    ((NLevelAdapter) listView.getAdapter()).getFilter().filter();
                    ((NLevelAdapter) listview2.getAdapter()).toggle(position);
                    ((NLevelAdapter) listview2.getAdapter()).getFilter().filter();
                    touchSource = null;
                }
            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent1, View view, int position, long id) {

                // Do something with the ListView was clicked
                int id1 = parent1.getId();
                if (parent1 == clickSource) {
                    ((NLevelAdapter) listView.getAdapter()).toggle(position);
                    ((NLevelAdapter) listView.getAdapter()).getFilter().filter();
                    ((NLevelAdapter) listview2.getAdapter()).toggle(position);
                    ((NLevelAdapter) listview2.getAdapter()).getFilter().filter();
                    touchSource = null;
                }
            }
        });
        horizontal_view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                touchSource = null;
                return false;
            }
        });

        // get the listview
        if (TruckApplication.isActiveNetwork(Written_sales_Activity.this)) {
            Sales_Async mSales_Async = new Sales_Async(
                    this,
                    ItrackConstant.baseUrl + ItrackConstant.GetDailySalesDataReport,
                    mAuthenticate_Interface);
            mSales_Async.execute();
        } else {
            TruckApplication
                    .ShowAlert(
                            Written_sales_Activity.this,
                            getString(R.string.app_name),
                            "Internet connection not found! Please try again later.",
                            false);
        }
    }

    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                // Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                // Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void ontaskcompleted(String result) {

        if (result != null && !result.equalsIgnoreCase("")) {
            // UtilityDebug.Debug(1, "Mobile user login result" + result);
            String ObjData = "";
            try {
                JSONObject mObj = new JSONObject(result);
                if (mObj.has("Data")) {
                    ObjData = mObj.getString("Data");
                } else {
                    ObjData = mObj.getString("Meassage");
                    TruckApplication.ShowAlert(Written_sales_Activity.this,
                            getString(R.string.app_name), ObjData, false);
                }
                if (ObjData.length() > 0) {
                    JSONArray jsonArray = mObj.getJSONArray("Data");
                    JSONArray jsonArrayHeader = mObj.getJSONArray("Header");
                    MdlTotalAll = new TotalAll();

                    try {
                        list = new ArrayList<NLevelItem>();
                        list1 = new ArrayList<NLevelItem>();
                        final LayoutInflater inflater = LayoutInflater.from(this);
                        final int HeaderLength = jsonArrayHeader.length();
                        HeaderName = new String[HeaderLength];
                        DataProperty = new String[HeaderLength];
                        for (int h = 0; h < HeaderLength; h++) {
                            JSONObject objJsonHeader = jsonArrayHeader.getJSONObject(h);
                            HeaderName[h] = objJsonHeader.getString("Name");
                            DataProperty[h] = objJsonHeader.getString("DataProperty");
                        }
                        CreateView(ll, 1, HeaderName, Color.parseColor("#FFFFFF"));
                        CreateView(lvheader, HeaderLength, HeaderName, Color.parseColor("#FFFFFF"));

                        JSONObject c = jsonArray.getJSONObject(0);
                        TotalAllValues = new String[HeaderLength];
                        for (int t = 0; t < HeaderLength; t++) {
                            TotalAllValues[t] = c.getString(DataProperty[t]);
                        }
                        lvheaderValue = (LinearLayout) findViewById(R.id.lvdemo1);
                        CreateView(llTotal, 1, c, Color.parseColor("#8599ac"));
                        CreateView(lvheaderValue, HeaderLength, c, Color.parseColor("#8599ac"));
                        JSONArray jsonArray1 = c.getJSONArray("DataRows");
                        int length1 = jsonArray1.length();
                        for (int j = 0; j < length1; j++) {

                            //  final Float
                            final JSONObject c1 = jsonArray1.getJSONObject(j);
                            NLevelItem grandParent = new NLevelItem(new SomeObject("GrandParent " + j), null, new NLevelView() {
                                @Override
                                public View getView(NLevelItem item) {
                                    // View view = inflater.inflate(R.layout.row, null);
                                    LinearLayout lv = new LinearLayout(Written_sales_Activity.this);
                                    return CreateView(lv, HeaderLength, c1, Color.parseColor("#BCDFF6"));
                                }
                            });
                            NLevelItem grandParentName = new NLevelItem(new SomeObject("GrandParent " + j), null, new NLevelView() {
                                @Override
                                public View getView(NLevelItem item) {
                                    // View view = inflater.inflate(R.layout.row, null);
                                    LinearLayout lv = new LinearLayout(Written_sales_Activity.this);
                                    return CreateView(lv, 1, c1, Color.parseColor("#BCDFF6"));
                                }
                            });
                            list.add(grandParent);
                            list1.add(grandParentName);
                            nLevel(c1, grandParent, grandParentName, HeaderLength);

                        }
                        try {
                            NLevelAdapter adapter = new NLevelAdapter(list);
                            NLevelAdapter adapter1 = new NLevelAdapter(list1);
                            listView.setAdapter(adapter);
                            listview2.setAdapter(adapter1);

                        } catch (Exception e) {
                            // TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), e.toString(), false);
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        // TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), e.toString(), false);
                        e.printStackTrace();
                    }
                } else {
                    JSONArray jsonArrayHeader = mObj.getJSONArray("Header");
                    final int HeaderLength = jsonArrayHeader.length();
                    HeaderName = new String[HeaderLength];
                    DataProperty = new String[HeaderLength];
                    for (int h = 0; h < HeaderLength; h++) {
                        JSONObject objJsonHeader = jsonArrayHeader.getJSONObject(h);
                        HeaderName[h] = objJsonHeader.getString("Name");
                        DataProperty[h] = objJsonHeader.getString("DataProperty");
                    }
                    LinearLayout lvheader = (LinearLayout) findViewById(R.id.lvdemo);
                    CreateView(lvheader, HeaderLength, HeaderName, Color.parseColor("#FFFFFF"));
                }

            } catch (Exception e) {
                TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), e.toString(), false);
                e.printStackTrace();
            }
        }
    }

    public void nLevel(JSONObject c1, NLevelItem grandParent, NLevelItem grandParentname, final int HeaderLength) {
        JSONArray jsonArray2 = null;
        String color;
        String ContactID, StoreId, TeamID;
        levelNo++;
        try {
            jsonArray2 = c1.getJSONArray("DataRows");

            int length2 = jsonArray2.length();
            for (int k = 0; k < length2; k++) {
                final JSONObject c2 = jsonArray2.getJSONObject(k);
                jsonData = c2.getString("DataRows");
              /*  ContactID = c2.getString("ContactID");
                StoreId = c2.getString("StoreID");
                color = c2.getString("Level");*/
                final int level = levelNo;

                /*if (ContactID != "null")
                    color = "#F5F5F5"; // Member
                else if (TeamID != "null")
                    color = "#D4E2D5"; // Team
                else if (StoreId != "null")
                    color = "#EBE4B8";
                else
                    color = "#A0DECB";
                final String viewColor = color;*/

                NLevelItem parent = new NLevelItem(new SomeObject("Parent "), grandParent, new NLevelView() {

                    @Override
                    public View getView(NLevelItem item) {
                        LinearLayout lv = new LinearLayout(Written_sales_Activity.this);
                        return CreateView(lv, HeaderLength, c2, Color.parseColor(Colors[level]));
                    }
                });
                NLevelItem parentName = new NLevelItem(new SomeObject("Parent "), grandParentname, new NLevelView() {

                    @Override
                    public View getView(NLevelItem item) {
                        LinearLayout lv = new LinearLayout(Written_sales_Activity.this);
                        return CreateView(lv, 1, c2, Color.parseColor(Colors[level]));
                    }
                });
                list.add(parent);
                list1.add(parentName);
                if (jsonData != "null" || jsonData == "[]") {
                    nLevel(c2, parent, parentName, HeaderLength);
                }
            }
            levelNo--;
            // Toast. makeText(getApplication()," "+levelNo, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), e.toString(), false);
            e.printStackTrace();
        }
    }

    public static String doubleToStringNoDecimal(String d) {
        double d1;
        String formatedSting;
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###");
        if (d.equals(null) || d.equals("") || d.length() < 1) {
            return "0";
        } else
            d1 = Double.valueOf(d);
        formatedSting = formatter.format(d1);
        return formatedSting;
    }

    public View CreateView(LinearLayout lv, int size, JSONObject json, int color) {
        //String[] jsonName = {"Amount", "Count", "AvgSale", "Margin", "MFSSales", "UPS"};
        if (size == 1) {
            for (int i = 0; i < size; i++) {
                TextView tv = new TextView(Written_sales_Activity.this);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                try {
                    tv.setText(json.getString(DataProperty[0]));
                    tv.setWidth((int) getResources().getDimension(R.dimen.txt_name_width));
                    tv.setTypeface(face, Typeface.BOLD);
                    tv.setSingleLine(true);
                    tv.setMaxLines(1);
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(10, 7, 7, 7);
                    tv.setTextColor(Color.parseColor("#372E2E"));
                    lv.setPadding(10, 7, 7, 7);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    lv.addView(tv);
                } catch (JSONException e) {
                    TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), e.toString(), false);
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 1; i < size; i++) {
                TextView tv = new TextView(Written_sales_Activity.this);
                try {
                    if (DataProperty[i].equals("Amount") || DataProperty[i].equals("AvgSale")) {
                        String s = json.getString(DataProperty[i]);
                        s = doubleToStringNoDecimal(json.getString(DataProperty[i]));
                        tv.setText("$" + doubleToStringNoDecimal(json.getString(DataProperty[i])));
                        tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                        tv.setPadding(10, 7, 7, 7);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTypeface(face, Typeface.NORMAL);
                        tv.setSingleLine(true);
                        tv.setMaxLines(1);
                        tv.setTextColor(Color.parseColor("#372E2E"));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    } else {
                        tv.setText(" " + json.getString(DataProperty[i]));
                        tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                        tv.setPadding(10, 7, 7, 7);
                        tv.setGravity(Gravity.CENTER);
                        tv.setSingleLine(true);
                        tv.setMaxLines(1);
                        tv.setTypeface(face, Typeface.NORMAL);
                        tv.setTextColor(Color.parseColor("#372E2E"));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    }
                } catch (JSONException e) {
                    TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), e.toString(), false);
                    e.printStackTrace();
                    e.printStackTrace();
                }
                lv.setPadding(10, 7, 7, 7);
                lv.addView(tv);
            }
        }
        lv.setOrientation(LinearLayout.HORIZONTAL);
        lv.setBackgroundColor(color);
        return lv;
    }

    public void CreateView(LinearLayout lv, int size, String[] Header, int color) {

        if (size == 1) {
            TextView tv = new TextView(Written_sales_Activity.this);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setText(Header[0]);
            tv.setWidth((int) getResources().getDimension(R.dimen.txt_name_width));
            tv.setPadding(10, 7, 7, 7);
            tv.setSingleLine(true);
            tv.setMaxLines(1);
            tv.setTypeface(face, Typeface.BOLD);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setTextColor(Color.parseColor("#E74C3C"));
            tv.setGravity(Gravity.CENTER);

            lv.addView(tv);
        } else {
            for (int i = 1; i < size; i++) {
                TextView tv = new TextView(Written_sales_Activity.this);
                if (Header[i].equals("Amount") || Header.equals("AvgSale")) {
                    tv.setText("$" + doubleToStringNoDecimal(Header[i]));
                    tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                    tv.setPadding(10, 7, 7, 7);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tv.setTypeface(face, Typeface.BOLD);
                    tv.setTextColor(Color.parseColor("#E74C3C"));
                    tv.setSingleLine(true);
                    tv.setMaxLines(1);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setText(Header[i]);
                    tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                    tv.setPadding(10, 7, 7, 7);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tv.setSingleLine(true);
                    tv.setMaxLines(1);
                    tv.setTypeface(face);
                    tv.setTextColor(Color.parseColor("#E74C3C"));
                    tv.setGravity(Gravity.CENTER);
                }
                lv.addView(tv);
            }
        }
        lv.setPadding(10, 7, 7, 7);
        lv.setOrientation(LinearLayout.HORIZONTAL);
        lv.setBackgroundColor(color);
        //return lv;
    }

    private Thread.UncaughtExceptionHandler handleAppCrash =
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.e("error", ex.toString());
                    //send email here
                    TruckApplication.ShowAlert(Written_sales_Activity.this, getString(R.string.app_name), ex.toString(), false);
                }
            };

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class SomeObject {
        public String name;

        public SomeObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                }).create().show();
    }

}
