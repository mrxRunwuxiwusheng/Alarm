package com.example.parts;

import java.util.Random;

import com.example.service.MainService;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class music {
	
	public static int bell(Context context, Intent intent){
        NotificationManager mgr = (NotificationManager) context 
                .getSystemService(Context.NOTIFICATION_SERVICE); 
        Notification nt = new Notification(); 
        nt.defaults = Notification.DEFAULT_SOUND; 
		 Log.d("Alarm", "MainService　bell"+nt.defaults);
        int soundId = new Random(System.currentTimeMillis()) 
                .nextInt(Integer.MAX_VALUE); 
        mgr.notify(soundId, nt); 
       // stopService(MainService.class);
/*        context.stopService(intent);
        context.startService(new Intent(context, MainService.class));*/
        return soundId; 
	}
    
}
