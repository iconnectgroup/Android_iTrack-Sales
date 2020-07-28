package com.itrack.Async;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.itrack.ConstantClass.ItrackConstant;
import com.itrack.ConstantClass.Prep_SharedPreferences;
import com.itrack.Interface.Login_Interface;
import com.itrack.itracksales.R;
import com.itrack.itracksales.TruckApplication;


public class Login_Async extends AsyncTask<String, Void, String> {

	public Context mcontext;
	public String musername;
	public String mpassword;
	public String mdevice;
	public String mplatform;
	public String mdeviceid;
	public String mconnectiontype;
	public String mregistrationid;
	public Login_Interface mlogininterface;
	public Handler loginhandler;
	public String ErrorMessage;
	public String str_Appversion;
	private Prep_SharedPreferences prep_SharedPreferences;
	private String logintype;
	public Dialog mDialog;


	// constructor

	public Login_Async(final Context mcontext, String musername,
					   String mpassword, String mdevice, String mplatform,
					   String mdeviceid, String mconnectiontype, String mregistrationid,
					   Login_Interface logininterface, String logintype) {
		super();
		this.mcontext = mcontext;
		this.musername = musername;
		this.mpassword = mpassword;
		this.mdevice = mdevice;
		this.mplatform = mplatform;
		this.mdeviceid = mdeviceid;
		this.mconnectiontype = mconnectiontype;
		this.mregistrationid = mregistrationid;
		this.mlogininterface = logininterface;
		this.logintype = logintype;
		prep_SharedPreferences = new Prep_SharedPreferences(mcontext, null);
		loginhandler = new Handler() {
			public void handleMessage(Message msg) {

				if (msg.what == 1) {
					TruckApplication.ShowAlert(mcontext,
							mcontext.getString(R.string.app_name),
							"Something went wrong, please try again!", false);

				} else if (msg.what == 2) {
					TruckApplication.ShowAlert(mcontext,
							mcontext.getString(R.string.app_name),
							ErrorMessage, false);
				}

			}
		};
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog= TruckApplication.CreateDialog(mcontext,1);
		mDialog.show();
		/*TruckApplication.showProgressDialog(mcontext,
				mcontext.getString(R.string.app_name), "Logging In...");*/
		try {
			PackageInfo pInfo = mcontext.getPackageManager().getPackageInfo(
					mcontext.getPackageName(), 0);
			str_Appversion = pInfo.versionName;
			Log.v("mathur", "current app version is :" + str_Appversion);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub

		try {
			StringBuffer resp = new StringBuffer("");
			URL url = new URL(ItrackConstant.baseUrl
					+ ItrackConstant.loginUrl);
			Log.e("hereurl","hereurl"+url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000);
			con.setConnectTimeout(15000);
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con	.setDoOutput(true);
			con.setUseCaches (false);
			con.setRequestProperty("Content-Type","application/json");
			//con.setRequestProperty("Host", "android.schoolportal.gr");
			//con.connect();
			JSONObject jsObje = new JSONObject();
			Log.d("cgtlog", "device is :" + mdevice);

			try {
				//{"UserId":"a51nha","Device":"iPhone","Connection":"WIFI","Gcm":"","Uuid":"","AppVersion":"1.4","Password":"!sinha123","Platform":"iPhone"}
				//{"Uuid":"352423061367213","Gcm":"","Device":"goya3g","AppVersion":"1.0","Password":"a51nha","UserId":"!sinha123","Connection":"WIFI","Platform":"Android"}
				jsObje.put("UserId", musername);
				jsObje.put("Device", mdevice);
				jsObje.put("Connection", mconnectiontype);
				jsObje.put("Gcm", mregistrationid);
				jsObje.put("Uuid", mdeviceid);
				jsObje.put("AppVersion", str_Appversion);
				jsObje.put("Password", mpassword);
				jsObje.put("Platform", mplatform);
				//jsObje.put("AppVersion", str_Appversion);
				//jsObje.put("LoginType", logintype);
				/*if (logintype.equalsIgnoreCase("A")) {
					jsObje.put("CheckLoadStatus", prep_SharedPreferences
							.getStringValue(ItrackConstant.CheckLoadStatus, ""));
				}*/
				Log.v("sushil", "login data is :" + jsObje.toString());

			} catch (JSONException e) {

				e.printStackTrace();
			}

			OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
			wr.write(jsObje.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line+"");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
			return sb.toString();
		}

		catch (ConnectTimeoutException e) {
			// TODO Auto-generated catch block
			TruckApplication.hideProgressDialog(mcontext);
			e.printStackTrace();
			ErrorMessage = e.toString().trim();
			loginhandler.sendEmptyMessage(1);
		}

		catch (SocketTimeoutException e) {
			// TODO: handle exception
			TruckApplication.hideProgressDialog(mcontext);
			e.printStackTrace();
			ErrorMessage = e.toString().trim();
			loginhandler.sendEmptyMessage(1);
		} catch (Exception e) {
			// TODO: handle exception
			TruckApplication.hideProgressDialog(mcontext);
			e.printStackTrace();
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
		//TruckApplication.hideProgressDialog(mcontext);
		mlogininterface.onloginAsynccomplete(result);

		Log.v("technology", "result is :" + result);
	}
}
