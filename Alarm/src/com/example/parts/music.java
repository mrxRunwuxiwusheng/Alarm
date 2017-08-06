package com.example.parts;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.example.service.MainService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class music {

    private  MediaPlayer mMediaPlayer;// = new MediaPlayer();
    private String myUriStr = null;
	private Intent mIntent;
	private Context mContext;
    
    public void chooseMusic(){
		Log.d("Alarm", "music   chooseMusic");
    	//myUriStr = null;
    	myUriStr="/sdcard/sss.mp3";
    	Uri uri = Uri.parse("file://" + myUriStr);
    	mMediaPlayer = MediaPlayer.create(mContext, uri);
    }

	public boolean onStart(Context context) {
		Log.d("Alarm", "music   onStart");
		mContext = context;
		try {
			mMediaPlayer.setLooping(false);
			mMediaPlayer.start();
			Log.d("Alarm", "mMediaPlayer.start();");
			showNote(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
    
	private void onStop() {
		Log.d("Alarm", "music   onStop");
		mMediaPlayer.stop();
		try {
			mMediaPlayer.prepare();
			mMediaPlayer.seekTo(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	private void showNote(Context context) {
		Log.d("Alarm", "music   showNote");
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		AlertDialog alert = builder.create(); 
		  alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		  alert.show();
				builder.setPositiveButton("再睡10分钟！", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d("Alarm", "music   再睡10分钟！");
						onStop();
						dialog.dismiss();
						long delayMillis = 10 * 60 * 1000;
						Handler handler = new Handler();
						Runnable ringAgain = new Runnable() {
							public void run() {
								onStart(mContext);
							}
						};
						handler.postDelayed(ringAgain, delayMillis);
					}
				}).setNegativeButton("关闭闹钟！", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d("Alarm", "music   关闭闹钟！");
						onStop();
						dialog.dismiss();   
					}
				});
				
			    final AlertDialog dialog = builder.create();  
			    dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));  
				Handler handler = new Handler();
				Log.d("Alarm", "music   new Handler()");
				Runnable rshow = new Runnable() {
					public void run() {
						Log.d("Alarm", "music   show");
	                    dialog.show();  
					}
				};
				handler.post(rshow);

	}
    
}

