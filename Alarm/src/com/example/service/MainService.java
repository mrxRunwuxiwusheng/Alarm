package com.example.service;

import com.example.global.DateTime;
import com.example.global.Monitor;
import com.example.parts.music;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class MainService extends Service{

    Intent mIntent;
	private Monitor monitor = new Monitor();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		mIntent = intent;
		return null;
	}
	
	@Override
    public void onCreate() {
		 Log.d("Alarm", "MainService　　onCreate");
    }
	
	@Override
	public void onStart(Intent intent, int startId) {
		mIntent = intent;
		Log.d("Alarm", "MainService　　onStart");
		monitor.onStartItem(MainService.this);
    }
	
	@Override
    public void onDestroy() {
		 Log.d("Alarm", "MainService　　onDestroy");
    }
	
}
