package com.itrack.Async;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.itrack.ConstantClass.ItrackConstant;
import com.itrack.ConstantClass.Prep_SharedPreferences;
import com.itrack.Interface.Logout_Interface;
import com.itrack.itracksales.R;
import com.itrack.itracksales.TruckApplication;

public class Logout_Async extends AsyncTask<String, Void, String> {

	public Context mcontext;
	public String musername;
	public String mpassword;
	public String mdevice;
	public String mplatform;
	public String mdeviceid;
	public String mconnectiontype;
	public String mregistrationid;
	public Logout_Interface mlogoutinterface;
	public String str_Appversion;
	private Prep_SharedPreferences prep_SharedPreferences;
	private TruckApplication mApplication;

	public Logout_Async(Context mcontext, String musername, String mpassword,
			String mdevice, String mplatform, String mdeviceid,
			String mconnectiontype, String mregistrationid,
			Logout_Interface mlogoutinterface) {

		super();

		this.mcontext = mcontext;
		this.musername = musername;
		this.mpassword = mpassword;
		this.mdevice = mdevice;
		this.mplatform = mplatform;
		this.mdeviceid = mdeviceid;
		this.mconnectiontype = mconnectiontype;
		this.mregistrationid = mregistrationid;
		this.mlogoutinterface = mlogoutinterface;
		prep_SharedPreferences = new Prep_SharedPreferences(mcontext, null);
		NotificationManager nMgr = (NotificationManager) mcontext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nMgr.cancelAll();
		mApplication = ((TruckApplication) mcontext.getApplicationContext());
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		TruckApplication.showProgressDialog(mcontext,
				mcontext.getString(R.string.app_name), "Logging Out...");

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
	protected String doInBackground(String... params) {

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(ItrackConstant.baseUrl
					+ ItrackConstant.logoutUrl);
			Log.d("sushil", ItrackConstant.baseUrl + ItrackConstant.logoutUrl);
			httpPost.setHeader("Content-type",
					"application/json; charset=UTF-8");

			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			JSONObject jsObje = new JSONObject();
			Log.d("sushil", "device is :" + mdevice);

			try {
				jsObje.put("UserId", musername);
				jsObje.put("Password", mpassword);
				jsObje.put("Device", mdevice);
				jsObje.put("Platform", mplatform);
				jsObje.put("Uuid", mdeviceid);
				jsObje.put("Connection", mconnectiontype);
				jsObje.put("Gcm", mregistrationid);
				jsObje.put("AppVersion", str_Appversion);
				jsObje.put("LoginType", ItrackConstant.str_Logintype);

			} catch (JSONException e) {

				e.printStackTrace();
			}

			Log.d("sushil", "logout json" + jsObje.toString());

			httpPost.setEntity(new StringEntity(jsObje.toString()));
			HttpResponse response = httpClient.execute(httpPost, localContext);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String logoutResponse = reader.readLine();
			Log.d("sushil", "Response of userlogout= " + logoutResponse);

			return logoutResponse;

		} catch (Exception e) {
			// TODO Auto-generated catch block

			TruckApplication.hideProgressDialog(mcontext);
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// prep_SharedPreferences.putStringValue(ItrackConstant.WaiverContent,
		// "");
		/*if (prep_SharedPreferences.getStringValue(ItrackConstant.fileName, "") != null
				&& prep_SharedPreferences
						.getStringValue(ItrackConstant.fileName, "").toString()
						.trim().length() != 0) {
			prep_SharedPreferences.putStringValue(
					ItrackConstant.fileName,
					prep_SharedPreferences.getStringValue(
							ItrackConstant.fileName, "")
							+ prep_SharedPreferences.getIntValue(
									ItrackConstant.fileNo, 0));
			prep_SharedPreferences.putIntValue(ItrackConstant.fileNo,
					prep_SharedPreferences
							.getIntValue(ItrackConstant.fileNo, 0) + 1);
		} else {

		}*/

		/*TruckApplication.copyDatabase(mcontext, "itrackDB");
		StopDetail_Activity.isStopDeliverCodeChange = -1;
		TruckApplication.hideProgressDialog(mcontext);
		mlogoutinterface.onlogoutAsynccomplete(result);
		Intent intent = new Intent(mcontext, CallNewStop_Service.class);
		mcontext.stopService(intent);
		TruckApplication.syncingCalled = -1;
		// TruckApplication.CalledFromStopAsync = false;
		TruckApplication.pendingStopModel = null;
		TruckApplication.mArraylist_unreadStopno = new ArrayList<NotificationModel>();

		prep_SharedPreferences.putStringValue(ItrackConstant.SelectedStopsDate,
				"");
		UtilityDebug.Debug(1, "logout date"
				+ mApplication.getApiDateTime().getDate());
		prep_SharedPreferences.putStringValue(ItrackConstant.SelectedStopsDate,
				mApplication.getApiDateTime().getDate());*/
	}
}
