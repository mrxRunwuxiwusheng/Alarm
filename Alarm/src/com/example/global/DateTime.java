package com.example.global;

import android.util.Log;

public class DateTime {
	
	public static int year;
	public static int month;
	public static int day;
	public static int hour;
	public static int minute;

	public DateTime(){
		try {
			hour = Integer.parseInt(Setting.hour);
			minute = Integer.parseInt(Setting.minute);
			Log.d("Alarm", "DateTime hour="+hour);
			Log.d("Alarm", "DateTime minute="+minute);
		} catch (NumberFormatException e) {
		}
	}
	
	public static int calculateShortTime(){
		int shortTime=-1;
		SystemTime.get();
		int shorthour = hour - SystemTime.hour;
		Log.d("Alarm", "calculateShortTime  shorthour="+shorthour);
		int shortminute = minute - SystemTime.minute;
		Log.d("Alarm", "calculateShortTime  shortminute="+shortminute);
		if(shorthour<0)
			shorthour += 24;
		else if (shorthour == 0 && shortminute < 0)
			shorthour = 24;
		shortTime = shorthour * 60 + shortminute;// unit is minute			
		Log.d("Alarm", "calculateShortTime  shortTime=" + shortTime);
		return shortTime;
	}
	
}
