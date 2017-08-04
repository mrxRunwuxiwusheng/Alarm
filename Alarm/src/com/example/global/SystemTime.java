package com.example.global;

import android.text.format.Time;
import android.util.Log;

public class SystemTime {

	public static int year;
	public static int month;
	public static int day;
	public static int hour;
	public static int minute;
	public static int second;
	
	
	public static void get(){
		 Time time = new Time();
		time.setToNow();
		year = time.year;
		month = time.month;
		day = time.monthDay;
		hour = time.hour;
		Log.d("Alarm", "SystemTime hour="+hour);
		minute = time.minute;
		Log.d("Alarm", "SystemTime minute="+minute);
		second = time.second;
		Log.d("Alarm", "SystemTime second="+second);
	}
	
}
