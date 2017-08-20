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

	private MediaPlayer mMediaPlayer;// = new MediaPlayer();
	public static String myUriStr = null;
	private Intent mIntent;
	private Context mContext;

	public void chooseMusic() {
		Log.d("Alarm", "music   chooseMusic");

		//myUriStr = "/sdcard/Music/alarms/Honor.mp3";sss.mp3
		myUriStr = "/storage/emulated/0/Music/alarms/sss.mp3";
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
		builder.setTitle("闹钟").setMessage("起床了！").setPositiveButton("再睡10分钟！",
				new DialogInterface.OnClickListener() {
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
	
	












/*	

public class Activity01 extends Activity  
{  
   3个按钮   
  private Button mButtonRingtone;  
 private Button mButtonAlarm;  
   private Button mButtonNotification;  

   public static final int ButtonRingtone          = 0;  
   public static final int ButtonAlarm             = 1;  
	20.    public static final int ButtonNotification      = 2;  
	21.     铃声文件夹   
	22.    private String strRingtoneFolder = "/sdcard/music/ringtones";  
	23.    private String strAlarmFolder = "/sdcard/music/alarms";  
	24.    private String strNotificationFolder = "/sdcard/music/notifications";  
	25.  
	26.  
	27.    *//** Called when the activity is first created. *//*  
	28.    @Override  
	29.    public void onCreate(Bundle savedInstanceState)  
	30.    {  
	31.        super.onCreate(savedInstanceState);  
	32.        setContentView(R.layout.main);  
	33.      
	34.        mButtonRingtone = (Button) findViewById(R.id.ButtonRingtone);  
	35.        mButtonAlarm = (Button) findViewById(R.id.ButtonAlarm);  
	36.        mButtonNotification = (Button) findViewById(R.id.ButtonNotification);  
	37.         设置来电铃声   
	38.        mButtonRingtone.setOnClickListener(new Button.OnClickListener()   
	39.        {  
	40.            @Override  
	41.            public void onClick(View arg0)  
	42.            {  
	43.                if (bFolder(strRingtoneFolder))  
	44.                {  
	45.                    //打开系统铃声设置  
	46.                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);  
	47.                    //类型为来电RINGTONE  
	48.                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);  
	49.                    //设置显示的title  
	50.                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电铃声");  
	51.                    //当设置完成之后返回到当前的Activity  
	52.                    startActivityForResult(intent, ButtonRingtone);  
	53.                }  
	54.            }  
	55.        });  
	56.         设置闹钟铃声   
	57.        mButtonAlarm.setOnClickListener(new Button.OnClickListener()   
	58.        {  
	59.            @Override  
	60.            public void onClick(View arg0)  
	61.            {  
	62.                if (bFolder(strAlarmFolder))  
	63.                {  
	64.                    //打开系统铃声设置  
	65.                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);  
	66.                    //设置铃声类型和title  
	67.                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);  
	68.                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹铃铃声");  
	69.                    //当设置完成之后返回到当前的Activity  
	70.                    startActivityForResult(intent, ButtonAlarm);  
	71.                }  
	72.            }  
	73.        });  
	74.         设置通知铃声   
	75.        mButtonNotification.setOnClickListener(new Button.OnClickListener()   
	76.        {  
	77.            @Override  
	78.            public void onClick(View arg0)  
	79.            {  
	80.                if (bFolder(strNotificationFolder))  
	81.                {  
	82.                    //打开系统铃声设置  
	83.                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);  
	84.                    //设置铃声类型和title  
	85.                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);  
	86.                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置通知铃声");  
	87.                    //当设置完成之后返回到当前的Activity  
	88.                    startActivityForResult(intent, ButtonNotification);  
	89.                }  
	90.            }  
	91.        });  
	92.    }  
	93.     当设置铃声之后的回调函数   
	94.    @Override  
	95.    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
	96.    {  
	97.        // TODO Auto-generated method stub  
	98.        if (resultCode != RESULT_OK)  
	99.        {  
	100.            return;  
	101.        }  
	102.        switch (requestCode)  
	103.        {  
	104.            case ButtonRingtone:  
	105.                try  
	106.                {  
	107.                    //得到我们选择的铃声  
	108.                    Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);  
	109.                    //将我们选择的铃声设置成为默认  
	110.                    if (pickedUri != null)  
	111.                    {  
	112.                        RingtoneManager.setActualDefaultRingtoneUri(Activity01.this, RingtoneManager.TYPE_RINGTONE, pickedUri);  
	113.                    }  
	114.                }  
	115.                catch (Exception e)  
	116.                {  
	117.                }  
	118.                break;  
	119.            case ButtonAlarm:  
	120.                try  
	121.                {  
	122.                    //得到我们选择的铃声  
	123.                    Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);  
	124.                    //将我们选择的铃声设置成为默认  
	125.                    if (pickedUri != null)  
	126.                    {  
	127.                        RingtoneManager.setActualDefaultRingtoneUri(Activity01.this, RingtoneManager.TYPE_ALARM, pickedUri);  
	128.                    }  
	129.                }  
	130.                catch (Exception e)  
	131.                {  
	132.                }  
	133.                break;  
	134.            case ButtonNotification:  
	135.                try  
	136.                {  
	137.                    //得到我们选择的铃声  
	138.                    Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);  
	139.                    //将我们选择的铃声设置成为默认  
	140.                    if (pickedUri != null)  
	141.                    {  
	142.                        RingtoneManager.setActualDefaultRingtoneUri(Activity01.this, RingtoneManager.TYPE_NOTIFICATION, pickedUri);  
	143.                    }  
	144.                }  
	145.                catch (Exception e)  
	146.                {  
	147.                }  
	148.                break;  
	149.        }  
	150.        super.onActivityResult(requestCode, resultCode, data);  
	151.    }  
	152.    //检测是否存在指定的文件夹   
	153.    //如果不存在则创建  
	154.    private boolean bFolder(String strFolder)  
	155.    {  
	156.        boolean btmp = false;  
	157.        File f = new File(strFolder);  
	158.        if (!f.exists())  
	159.        {  
	160.            if (f.mkdirs())  
	161.            {  
	162.                btmp = true;  
	163.            }  
	164.            else  
	165.            {  
	166.                btmp = false;  
	167.            }  
	168.        }  
	169.        else  
	170.        {  
	171.            btmp = true;  
	172.        }  
	173.        return btmp;  
	174.    }  
	175.}  

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}*/
