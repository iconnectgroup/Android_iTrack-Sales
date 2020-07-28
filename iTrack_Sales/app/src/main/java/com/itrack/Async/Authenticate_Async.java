package com.itrack.Async;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.itrack.Interface.Authenticate_Interface;
import com.itrack.itracksales.TruckApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Authenticate_Async extends AsyncTask<String, Void, String> {

    public Context mContext;
    public String url_authenticate;
    public Authenticate_Interface mAuthenticate_Interface;
    public Handler loginhandler;
    public String ErrorMessage;
    public Dialog mDialog;


    public Authenticate_Async(final Context mContext, String url_authenticate,
                              Authenticate_Interface mAuthenticate_Interface) {
        super();
        this.mContext = mContext;
        this.url_authenticate = url_authenticate;
        this.mAuthenticate_Interface = mAuthenticate_Interface;

        loginhandler = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    TruckApplication.ShowAlert(mContext,
                            mContext.getString(com.itrack.itracksales.R.string.app_name),
                            mContext.getString(com.itrack.itracksales.R.string.wentwrong), false);

                }

            }
        };
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        mDialog= TruckApplication.CreateDialog(mContext,1);
        mDialog.show();
        /*TruckApplication.showProgressDialog(mContext,
                mContext.getString(com.itrack.itracksales.R.string.app_name), "Authenticating..");*/
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            StringBuffer resp = new StringBuffer("");
            URL url = new URL(url_authenticate);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(3500);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            int HttpResult = con.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK){
                InputStream inputStream = con.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    resp.append(line);
                }

                return resp.toString();
            }

        } catch (Exception e) {

            e.printStackTrace();
            TruckApplication.hideProgressDialog(mContext);
            ErrorMessage = e.toString().trim();
            loginhandler.sendEmptyMessage(1);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        mDialog.dismiss();
       // TruckApplication.hideProgressDialog(mContext);
        mAuthenticate_Interface.ontaskcompleted(result);

    }

}
