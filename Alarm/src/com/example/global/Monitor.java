package com.example.global;

import java.util.ArrayList;

import com.example.parts.music;
import com.example.service.MainService;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class Monitor {
	
	public ArrayList<DateTime> DTs;
	Context mContext;
	
	public boolean onStartItem(Context context){
		mContext = context;
		DateTime DT = new DateTime();
		int shortTime = DateTime.calculateShortTime();
		Log.d("Alarm", "闹铃设置成功，将会在"+shortTime/3600+"时"+(shortTime/60-shortTime/3600*60)+"分后提醒您");
		Toast.makeText(context, "闹铃设置成功，将会在"+shortTime/3600+"时"+(shortTime/60-shortTime/3600*60)+"分后提醒您",Toast.LENGTH_LONG).show();
		bellAfter(shortTime,context);
		return true;
	}


	private boolean bellAfter(int seconds,Context context){
		 Log.d("Alarm", "MainService　bellAfter");
		 Handler bellHandler = new Handler();
		 long delayMillis = seconds*1000;
		 Log.d("Alarm", "Service bellAfter delayMillis="+delayMillis);
		 Runnable bell = new Runnable(){
				 public void run() {
					 music ms=new music();
					 ms.chooseMusic();
					 ms.onStart(mContext);
				 }
			};
		 bellHandler.postDelayed(bell, delayMillis);
		return true;
	}
	
}