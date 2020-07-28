package com.itrack.itracksales;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.Toast;

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


public class Written_sales_Activity1 extends Activity implements Authenticate_Interface {

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
    ImageView mIvRefresh, mIvLogout;
    String data = "";
    String jsonData;
    int list_flag = 1;
    int PERMISSION_ALL = 1;


    /*TextView mTvPrtection, mTvOps, mTvSales, mTvMargn, MtvCount, mTvAVGsales;*/
    TotalAll MdlTotalAll;
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
        /*mTvSales = (TextView) findViewById(R.id.tv_tot_sales);
        mTvMargn = (TextView) findViewById(R.id.tv_tot_margin);
        mTvAVGsales = (TextView) findViewById(R.id.tv_tot_avg_sales);
        MtvCount = (TextView) findViewById(R.id.tv_tot_count);*/
        mIvRefresh = (ImageView) findViewById(R.id.iv_refresh);
        mIvLogout = (ImageView) findViewById(R.id.iv_log_out);
        llTotal = (LinearLayout) findViewById(R.id.lv_tot_all);
        ll = (LinearLayout) findViewById(R.id.lv_tot_left);
        tv1 = new TextView(this);
        tv = new TextView(this);
        // horizontal_view.setClickable(false);
       /* mTvPrtection = (TextView) findViewById(R.id.tv_tot_protection);
        mTvOps = (TextView) findViewById(R.id.tv_Tot_ops);*/
        mSharedPreferences = new Prep_SharedPreferences(Written_sales_Activity1.this);
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

                ListRefresh();
            }

            private void ListRefresh() {
               /* list.clear();
                list1.clear();
                listview2.removeAllViews();
                listView.removeAllViews();*/
                list.clear();
                ll.removeAllViews();
                llTotal.removeAllViews();
                lvheader.removeAllViews();
                lvheaderValue.removeAllViews();
                //  Toast.makeText(Written_sales_Activity1.this, "" + list.size(), Toast.LENGTH_SHORT).show();
                Sales_Async mSales_Async = new Sales_Async(
                        Written_sales_Activity1.this,
                        ItrackConstant.GetDailySalesDataReport,
                        mAuthenticate_Interface);
                mSales_Async.execute();
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
                int id1=parent.getId();
                if(parent==clickSource) {
                    /*if(((NLevelAdapter) listView.getAdapter()).getItem(position+1).isExpanded()&& ((NLevelAdapter) listView.getAdapter()).getItem(position+1).getParent()!=null)
                    {
                        ((NLevelAdapter) listView.getAdapter()).toggle(position+1);
                    }*/
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
                int id1=parent1.getId();
                if(parent1==clickSource) {
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
        if (TruckApplication.isActiveNetwork(Written_sales_Activity1.this)) {
            Sales_Async mSales_Async = new Sales_Async(
                    this,
                    ItrackConstant.GetDailySalesDataReport,
                    mAuthenticate_Interface);
            mSales_Async.execute();
        } else {
            TruckApplication
                    .ShowAlert(
                            Written_sales_Activity1.this,
                            getString(R.string.app_name),
                            "Internet connection not found! Please try again later.",
                            false);
        }
    }

    @Override
    public void ontaskcompleted(String result) {


        if (result != null && !result.equalsIgnoreCase("")) {
            // UtilityDebug.Debug(1, "Mobile user login result" + result);
            String ObjData="";
            try {
                JSONObject mObj = new JSONObject(result);
                if (mObj.has("Data"))
                {
                    ObjData= mObj.getString("Data");
                }else
                {
                    ObjData=mObj.getString("Meassage");
                    TruckApplication.ShowAlert(Written_sales_Activity1.this,
                            getString(R.string.app_name),ObjData , false);
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

                        CreateView(lvheader, HeaderLength, HeaderName, Color.parseColor("#FFFFFF"));
                        CreateView(llTotal, 1, HeaderName, Color.parseColor("#000FFF"));
                        // linearLayout =
                        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);

                        JSONObject c = jsonArray.getJSONObject(0);
                        TotalAllValues = new String[HeaderLength];
                        for (int t = 0; t < HeaderLength; t++) {
                            TotalAllValues[t] = c.getString(DataProperty[t]);
                        }
                        lvheaderValue = (LinearLayout) findViewById(R.id.lvdemo1);
                        CreateView(lvheaderValue, HeaderLength, c, Color.parseColor("#BBBBBB"));
                        CreateView(ll, 1, c, Color.parseColor("#BBBBBB"));
                        tv.setText(HeaderName[0]);
                        //tv.setLayoutParams(lp);
                        tv.setWidth((int) getResources().getDimension(R.dimen.txt_name_width));
                        tv.setPadding(5, 7, 5, 7);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setTypeface(null, Typeface.BOLD);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                        ll.setPadding(10, 7, 5, 7);
                        ll.addView(tv);
                        ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        // LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        tv1.setText(c.getString(DataProperty[0]));
                        //tv1.setLayoutParams(lp1);
                        tv1.setWidth((int) getResources().getDimension(R.dimen.txt_name_width));
                        tv1.setTextColor(Color.parseColor("#000000"));
                        tv1.setGravity(Gravity.CENTER);
                        tv1.setTypeface(null, Typeface.BOLD);
                        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                        tv1.setPadding(5, 7, 5, 7);

                        llTotal.setPadding(10, 7, 5, 7);
                        llTotal.addView(tv1);
                        llTotal.setBackgroundColor(Color.parseColor("#BBBBBB"));

                        JSONArray jsonArray1 = c.getJSONArray("DataRows");
                        int length1 = jsonArray1.length();
                        // Company Data
                        for (int j = 0; j < length1; j++) {
                            //  final Float
                            final JSONObject c1 = jsonArray1.getJSONObject(j);
                            NLevelItem grandParent = new NLevelItem(new SomeObject("GrandParent " + j), null, new NLevelView() {
                                @Override
                                public View getView(NLevelItem item) {
                                    // View view = inflater.inflate(R.layout.row, null);
                                    LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                    return CreateView(lv, HeaderLength, c1, Color.parseColor("#BCDFF6"));
                                }
                            });
                            NLevelItem grandParentName = new NLevelItem(new SomeObject("GrandParent " + j), null, new NLevelView() {
                                @Override
                                public View getView(NLevelItem item) {
                                    // View view = inflater.inflate(R.layout.row, null);
                                    LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                    return CreateView(lv, 1, c1, Color.parseColor("#BCDFF6"));
                                }
                            });
                            list.add(grandParent);
                            list1.add(grandParentName);

                            JSONArray jsonArray2 = c1.getJSONArray("DataRows");
                            int length2 = jsonArray2.length();
                            //Store Data
                            for (int k = 0; k < length2; k++) {
                                final JSONObject c2 = jsonArray2.getJSONObject(k);
                                NLevelItem parent = new NLevelItem(new SomeObject("Parent " + j), grandParent, new NLevelView() {

                                    @Override
                                    public View getView(NLevelItem item) {
                                        LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                        return CreateView(lv, HeaderLength, c2, Color.parseColor("#EBE4B8"));
                                    }
                                });
                                NLevelItem parentName = new NLevelItem(new SomeObject("Parent " + j), grandParentName, new NLevelView() {

                                    @Override
                                    public View getView(NLevelItem item) {
                                        LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                        return CreateView(lv, 1, c2, Color.parseColor("#EBE4B8"));
                                    }
                                });
                                list.add(parent);
                                list1.add(parentName);
                                jsonData = c2.getString("DataRows");
                                if (jsonData == "null" || jsonData.length() < 1) {
                                    Toast.makeText(getApplication(), "" + jsonData, Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                JSONArray jsonArray3 = c2.getJSONArray("DataRows");
                                int length3 = jsonArray3.length();
                                    //Team Data
                                for (int l = 0; l < length3; l++) {
                                    try {
                                        final JSONObject c3 = jsonArray3.getJSONObject(l);
                                        NLevelItem child = new NLevelItem(new SomeObject("child " + k), parent, new NLevelView() {

                                            @Override
                                            public View getView(NLevelItem item) {
                                                LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                                return CreateView(lv, HeaderLength, c3, Color.parseColor("#D4E2D5"));
                                            }
                                        });
                                        NLevelItem child_Name = new NLevelItem(new SomeObject("child " + k), parentName, new NLevelView() {

                                            @Override
                                            public View getView(NLevelItem item) {
                                                LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                                return CreateView(lv, 1, c3, Color.parseColor("#D4E2D5"));
                                            }
                                        });
                                        list.add(child);
                                        list1.add(child_Name);
                                        jsonData = c3.getString("DataRows");
                                        if (jsonData == "null" && jsonData.length() < 1) {

                                        } else {
                                            JSONArray jsonArray4 = c3.getJSONArray("DataRows");
                                            int length4 = jsonArray4.length();
                                            // Member Data
                                            for (int m = 0; m < length4; m++) {
                                                try {
                                                    final JSONObject c4 = jsonArray4.getJSONObject(m);
                                                    NLevelItem child1 = new NLevelItem(new SomeObject("sub2 " + m), child, new NLevelView() {

                                                        @Override
                                                        public View getView(NLevelItem item) {
                                                            LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                                            return CreateView(lv, HeaderLength, c4, Color.parseColor("#F5F5F5"));
                                                        }
                                                    });
                                                    NLevelItem child1name = new NLevelItem(new SomeObject("sub2 " + m), child_Name, new NLevelView() {

                                                        @Override
                                                        public View getView(NLevelItem item) {
                                                            LinearLayout lv = new LinearLayout(Written_sales_Activity1.this);
                                                            return CreateView(lv, 1, c4, Color.parseColor("#F5F5F5"));
                                                        }
                                                    });
                                                    list.add(child1);
                                                    list1.add(child1name);
                                                    jsonData = c4.getString("DataRows");
                                                    if (jsonData == "null") {

                                                    } else {
                                                        JSONArray jsonArray5 = c4.getJSONArray("DataRows");
                                                        int length5 = jsonArray5.length();
                                                    }
                                                } catch (Exception e) {
                                                    //  TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        //TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
                                        e.printStackTrace();
                                    }
                                }
                            }
                            }
                        }
                        try {
                            NLevelAdapter adapter = new NLevelAdapter(list);
                            NLevelAdapter adapter1 = new NLevelAdapter(list1);
                            listView.setAdapter(adapter);
                            listview2.setAdapter(adapter1);

                        } catch (Exception e) {
                           // TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                       // TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
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
                    /*LinearLayout lvheaderValue = (LinearLayout)findViewById(R.id.lvdemo1);
                    CreateView(lvheaderValue, HeaderLength,TotalAllValues, Color.parseColor("#BBBBBB"));*/
                   /* LinearLayout ll = (LinearLayout)findViewById(R.id.lv_tot_left);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    TextView tv = new TextView(this);
                    tv.setTextSize(15);
                    tv.setText(HeaderName[0]);
                    tv.setLayoutParams(lp);
                    tv.setWidth(400);
                    tv.setPadding(10, 7, 7, 7);
                    tv.setGravity(Gravity.CENTER);
                    ll.setPadding(10, 7, 7, 7);
                    ll.addView(tv);
                    ll.setBackgroundColor(Color.parseColor("#FFFFFF"));*/


                    // TruckApplication.ShowAlert(Written_sales_Activity1.this,getString(R.string.app_name),"Data Not Found",false);
                }


               /* mTvAVGsales.setText("$" + MdlTotalAll.getAvgSale());
                mTvMargn.setText(MdlTotalAll.getMargin());
                mTvSales.setText("$" + MdlTotalAll.getAmount());
                MtvCount.setText(MdlTotalAll.getCount());*/
            } catch (Exception e) {
                TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
                e.printStackTrace();
            }

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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (size == 1) {
            for (int i = 0; i < size; i++) {
                TextView tv = new TextView(Written_sales_Activity1.this);
                try {
                    tv.setText(json.getString(DataProperty[0]));
                    tv.setWidth((int) getResources().getDimension(R.dimen.txt_name_width));
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setGravity(Gravity.CENTER);
                } catch (JSONException e) {
                    TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
                    e.printStackTrace();
                    e.printStackTrace();
                }
                tv.setPadding(10, 7, 7, 7);
                tv.setTextColor(Color.parseColor("#000000"));
                lv.setPadding(10, 7, 7, 7);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                lv.addView(tv);
            }
        } else {
            for (int i = 1; i < size; i++) {
                TextView tv = new TextView(Written_sales_Activity1.this);
                try {
                    if (DataProperty[i].equals("Amount") || DataProperty[i].equals("AvgSale")) {
                        String s = json.getString(DataProperty[i]);
                        s = doubleToStringNoDecimal(json.getString(DataProperty[i]));
                        tv.setText("$" + doubleToStringNoDecimal(json.getString(DataProperty[i])));
                        tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                        tv.setPadding(10, 7, 7, 7);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTypeface(null, Typeface.BOLD);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    } else {
                        tv.setText(" " + json.getString(DataProperty[i]));
                        tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                        tv.setPadding(10, 7, 7, 7);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTypeface(null, Typeface.BOLD);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    }
                } catch (JSONException e) {
                    TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), e.toString(), false);
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
        //   String[] jsonName = {"Amount", "Count", "AvgSale", "Margin", "MFSSales", "UPS"};
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        String regex = "[0-9]+";

        if (size == 1) {
            TextView tv = new TextView(Written_sales_Activity1.this);
            tv.setText(Header[0]);
            tv.setWidth((int) getResources().getDimension(R.dimen.txt_name_width));
            tv.setPadding(10, 7, 7, 7);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setGravity(Gravity.CENTER);
            lv.addView(tv);
        } else {
            for (int i = 1; i < size; i++) {
                TextView tv = new TextView(Written_sales_Activity1.this);
                if (Header[i].equals("Amount") || Header.equals("AvgSale")) {
                    tv.setText("$" + doubleToStringNoDecimal(Header[i]));
                    tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                    tv.setPadding(10, 7, 7, 7);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setText(Header[i]);
                    tv.setWidth((int) getResources().getDimension(R.dimen.txt_sale_width));
                    tv.setPadding(10, 7, 7, 7);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextColor(Color.parseColor("#000000"));
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
                    TruckApplication.ShowAlert(Written_sales_Activity1.this, getString(R.string.app_name), ex.toString(), false);
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
