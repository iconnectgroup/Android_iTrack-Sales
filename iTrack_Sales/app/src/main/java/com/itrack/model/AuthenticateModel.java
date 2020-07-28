package com.itrack.model;

import android.util.Log;

import com.itrack.Database.DataBaseHandler;

/**
 * Created by Sandip on 23-12-2016.
 */

public class AuthenticateModel {

    public String name;
    public String liveapiurl;
    public String testapiurl;
    public String clientcode;
    public String errormessage;

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiveapiurl() {
        return liveapiurl;
    }

    public void setLiveapiurl(String liveapiurl) {
        this.liveapiurl = liveapiurl;
    }

    public String getTestapiurl() {
        return testapiurl;
    }

    public void setTestapiurl(String testapiurl) {
        this.testapiurl = testapiurl;
    }

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }

    private long insertRowId;

    public void saveAuthenticationDatabase(
            DataBaseHandler dbhandler) {

        try {

            insertRowId = dbhandler.insertAuthenticateuser(this);

            Log.d("cgtdb", "insertRowId for authenticate: " + insertRowId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}