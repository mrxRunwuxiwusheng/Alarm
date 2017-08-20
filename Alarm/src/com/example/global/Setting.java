package com.example.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Setting {
	
	public static final String PREFERENCE_NAME = "AlarmSetting";
	public static final String FLAG_HOUR = "hour";
	public static final String FLAG_MINUTE = "minute";
	public static final String FLAG_ENABLE = "enable";
	public static final String FLAG_MUSICS = "musics";
	public static final String FLAG_URI = "uri";
	
	public static String hour;
	public static String minute;
	public static String musics;
	public static String uri;
	public static boolean enable;
	
	public static void getSetting(Context context){
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
		hour = sp.getString(FLAG_HOUR,"0");
		minute = sp.getString(FLAG_MINUTE,"0");
		musics = sp.getString(FLAG_MUSICS, null);
		uri = sp.getString(FLAG_URI, null);
		enable = sp.getBoolean(FLAG_ENABLE, false);
	}
	
	public static void saveSetting(Context context){
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(FLAG_HOUR, hour);
		edit.putString(FLAG_MINUTE, minute);
		edit.putString(FLAG_MUSICS, musics);
		edit.putString(FLAG_URI, uri);
		edit.putBoolean(FLAG_ENABLE, enable);
		edit.apply();
	}

}
