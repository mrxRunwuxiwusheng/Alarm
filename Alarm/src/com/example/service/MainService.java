package com.example.service;

import com.example.global.DateTime;
import com.example.parts.music;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MainService extends Service{

    Intent mIntent;
	
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

	private boolean bellAfter(int minutes){
		 Log.d("Alarm", "MainService　bellAfter");
		 Handler bellHandler = new Handler();
		 long delayMillis = minutes*60*1000;
		 Log.d("Alarm", "Service bellAfter delayMillis="+delayMillis);
		 Runnable bell = new Runnable(){
				 public void run() {
					 music.bell(MainService.this,mIntent);
				 }
			};
		 bellHandler.postDelayed(bell, delayMillis);
		return true;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		mIntent = intent;
		Log.d("Alarm", "MainService　　onStart");
		int shortTime = DateTime.calculateShortTime();
		Toast.makeText(this, "闹铃设置成功，将会在"+shortTime/60+"小时"+(shortTime-shortTime/60*60)+"分钟后提醒您",Toast.LENGTH_LONG).show();
		bellAfter(shortTime);
    }
	
	@Override
    public void onDestroy() {
		 Log.d("Alarm", "MainService　　onDestroy");
    }
	
}
