package com.example.parts;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.example.service.MainService;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class music {

    public  MediaPlayer mMediaPlayer = new MediaPlayer();
	public  int bell(Context context, Intent intent){
/*       try {  
           mMediaPlayer.setDataSource("http://music.baidu.com/song/266322598");
         //  mMediaPlayer.setLooping(false);  
           mMediaPlayer.prepare();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }*/
     String myUriStr="/sdcard/sss.mp3";
           try {
           mMediaPlayer.setDataSource(myUriStr);
           } catch (IOException e) {
               e.printStackTrace();  
           }
           if(mMediaPlayer == null){
				Log.d("Alarm", "该铃声不存在，请重新选择");
               return -1;  
               }  
       mMediaPlayer.setLooping(false);  
       try {
           mMediaPlayer.prepare();  
       } catch (IllegalStateException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
  
       mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {  
           @Override  
           public void onPrepared(MediaPlayer mediaPlayer) {  
               mMediaPlayer.start();  
           }  
       });  
       return 1;
	}
}




       
       /*        
        public static void bell(Context context, Intent intent){
        	final File file=new File("/system/media/audio/ui/Dock.ogg");
   		    Log.d("Alarm", "ring　file"+file.getAbsolutePath());
        	Uri newUri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALL, newUri);
            Ringtone rt = RingtoneManager.getRingtone(context, newUri);  
            rt.play();     
            
            
            
        }
}*/





/*NotificationManager mgr = (NotificationManager) context 
		.getSystemService(Context.NOTIFICATION_SERVICE);
Notification nt = new Notification();
nt.defaults = Notification.DEFAULT_SOUND;
Log.d("Alarm", "MainService　bell" + nt.defaults);
int soundId = new Random(System.currentTimeMillis())
		.nextInt(Integer.MAX_VALUE);
mgr.notify(soundId, nt);
// stopService(MainService.class);

 * context.stopService(intent); context.startService(new Intent(context,
 * MainService.class));
 
Log.d("Alarm", "soundId  ＝  " + soundId);
return soundId;*/