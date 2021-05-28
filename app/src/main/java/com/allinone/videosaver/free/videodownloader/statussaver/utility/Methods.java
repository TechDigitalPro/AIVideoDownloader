package com.allinone.videosaver.free.videodownloader.statussaver.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.Window;

import videodownload.com.newmusically.R;


public class Methods {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;


    public Methods(Context context){
        this._context = context;
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }




}
