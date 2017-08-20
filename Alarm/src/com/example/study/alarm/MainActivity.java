package com.example.study.alarm;

import java.io.File;
import java.util.ArrayList;

import com.example.global.DateTime;
import com.example.global.Monitor;
import com.example.global.Setting;
import com.example.parts.music;
import com.example.service.MainService;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Spinner hour;
	private Spinner minute;
	private Switch enable;
	private Spinner howoften;
	private Spinner musics;
	private Intent mIntent;
	private TextView tv ;
	private EditText et;
	
	ArrayList<String> titleList = new ArrayList<String>();
	ArrayList<String> dataList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hour = (Spinner)findViewById(R.id.musics);//////////////////
		minute = (Spinner)findViewById(R.id.musics);//////////////
		enable = (Switch)findViewById(R.id.open);
		howoften = (Spinner)findViewById(R.id.how_often);
		musics = (Spinner)findViewById(R.id.musics);
		tv = (TextView ) findViewById (R. id. nameset ) ;
        Setting.getSetting(this);

        tv.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		et = new EditText(MainActivity.this);
        		et.setText(tv.getText().toString()) ;
        		new AlertDialog.Builder(MainActivity.this) 
        		  .setTitle("闹钟名称：") 
        		  .setIcon(android.R.drawable.ic_dialog_info) 
        		  .setView(et ) 
        		  .setPositiveButton("保存", new DialogInterface.OnClickListener()
        	      {
        			    @Override
        			    public void onClick(DialogInterface dialog, int which) {
        			    	tv.setText(et.getText().toString()) ;
        			    }
        		 }) 
        		  .setNegativeButton("取消", null) 
        		  .show(); 
        	}
        	});
        
		getList(this);
		int musicPosition = titleList.indexOf(Setting.musics);
		ArrayAdapter<String> adaptermusic = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
		musics.setAdapter(adaptermusic);
		musics.setSelection(musicPosition, true);
        
		String[] hourArray = getResources().getStringArray(R.array.hour);
		ArrayList<String> hourItems = new ArrayList<String>();
		for (String str : hourArray) {
			hourItems.add(str);
		}
		int hourPosition = getIndex(Setting.hour,hourArray);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, hourItems);
		hour.setAdapter(adapter1);
		hour.setSelection(hourPosition, true);//set the selection item same to last setting
		
		String[] minuteArray = getResources().getStringArray(R.array.minute);
		ArrayList<String> minuteItems = new ArrayList<String>();
		for (String str : minuteArray) {
			minuteItems.add(str);
		}
		int minutePosition = getIndex(Setting.minute,minuteArray);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, minuteItems);
		minute.setAdapter(adapter2);
		minute.setSelection(minutePosition, true);
		
		enable.setChecked(Setting.enable);

		hour.setOnItemSelectedListener(new hourSeletedListener());
		minute.setOnItemSelectedListener(new minuteSeletedListener());
		musics.setOnItemSelectedListener(new musicsSeletedListener());
		enable.setOnCheckedChangeListener(enabledListener);

/*		if (monitor == null) {
			monitor = new Monitor();
			mIntent = new Intent(MainActivity.this, MainService.class);
		//	startService(mIntent);
		}*/
	}

	public void getList(Context context) {
		Log.d("Alarm", "getList");
		Context mContext = context;
		Cursor cursor;
		Uri albumUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;// INTERNAL_CONTENT_URI
		String[] projection = { "_data", "_display_name", "_size", "mime_type", "title", "duration" };
		cursor = mContext.getContentResolver().query(albumUri, projection, null, null, null);
		cursor.moveToFirst(); 
		String str;
		String substr;
		String title;
		do {
			str = cursor.getString(cursor.getColumnIndex("_display_name"));
			substr = str.substring(str.length() - 3, str.length());
			if (substr.equals("mp3")) {
				title = cursor.getString(cursor.getColumnIndex("title"));
			    titleList.add(title);
			    dataList.add(cursor.getString(cursor.getColumnIndex("_data")));
			}
		} while (cursor.moveToNext()); 
	}
	
	int getIndex(String s,String[] ss){
		int index = 0;
		for(String c:ss){
			if(s.equals(c))
				break;
			index++;
		}
		return index;
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);   
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(Menu.NONE,  Menu.FIRST+1 , 0, "设置");   
		return true;
	}
	
	@Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item); 
        switch(item.getItemId()) //得到被点击的item的itemId 
        { 
        case  Menu.FIRST+1 :  //对应的ID就是在add方法中所设定的Id 
			mIntent = new Intent(MainActivity.this, ListActivity.class);
			startActivity(mIntent);
            break; 
        }
        return true; 
    }

	class hourSeletedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			Setting.hour = parent.getItemAtPosition(position).toString();
			Setting.saveSetting(MainActivity.this);
			Log.d("Alarm", "Setting.hour="+Setting.hour);
		//	DT.sethour(parent.getItemAtPosition(position).toString());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}
		
	}
	
	class minuteSeletedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			Setting.minute = parent.getItemAtPosition(position).toString();
			Setting.saveSetting(MainActivity.this);
			Log.d("Alarm", "Setting.minute="+Setting.minute);
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}
	}
	
	class musicsSeletedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			Setting.musics = parent.getItemAtPosition(position).toString();
			Setting.uri = dataList.get(position);
			Setting.saveSetting(MainActivity.this);
			Log.d("Alarm", "Setting.musics="+Setting.musics);
			Log.d("Alarm", "Setting.uri  ="+Setting.uri );
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}
	}
	
	private OnCheckedChangeListener enabledListener = new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
	/*		mIntent = new Intent(MainActivity.this, ListActivity.class);
			startActivity(mIntent);*/
			if(isChecked!=Setting.enable)
			{
				Setting.enable=isChecked;
				Setting.saveSetting(MainActivity.this);
			}
			if (isChecked) {
				Log.d("Alarm", "isChecked = true");
				mIntent = new Intent(MainActivity.this, MainService.class);
				startService(mIntent);
			}
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

/*	public class CustomerDateDialog extends Dialog {
		private View customView;
		private Button setBtn;
		private Button cancleBtn;
		private TextView arrow_up;
		private TextView tv01, tv02;
		private ScrollView sv01, sv02;
		private LinearLayout llTimeWheel;
		private DateDialogListener listener;
		private int lastY;
		private int flag;// 标记时分
		private int itemHeight;// 每一行的高度
		private int pHour, pMinute;// 初始化时显示的时分时间
		private int setHour, setMinute;

		public CustomerDateDialog(Context context, int hour, int minute) {
			super(context, R.style.CustomerDateDialog);
			customView = LayoutInflater.from(context).inflate(R.layout.time_wheel,
					null);
			init(context, hour, minute);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.setContentView(customView);
		}

		private void init(Context context, final int hour, final int minute) {
			tv01 = (TextView) customView.findViewById(R.id.tv01);
			tv02 = (TextView) customView.findViewById(R.id.tv02);
			sv01 = (ScrollView) customView.findViewById(R.id.sv01);
			sv02 = (ScrollView) customView.findViewById(R.id.sv02);
			setBtn = (Button) customView.findViewById(R.id.setBtn);
			cancleBtn = (Button) customView.findViewById(R.id.cancleBtn);
			arrow_up = (TextView) customView.findViewById(R.id.arrow_up);
			this.pHour = hour;
			this.pMinute = minute;
			setHour = pHour;
			setMinute = pMinute;

			llTimeWheel = (LinearLayout) customView
					.findViewById(R.id.ll_time_wheel);
			setHourDial(tv01);
			setMinuteDial(tv02);

			sv01.setOnTouchListener(tListener);
			sv02.setOnTouchListener(tListener);

			final ViewTreeObserver observer = sv01.getViewTreeObserver();// observer
																			// 作用当视图完全加载进来的时候再取控件的高度，否则取得值是0
			observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@SuppressWarnings("deprecation")
				public void onGlobalLayout() {
					int tvHeight = tv02.getHeight();
					itemHeight = tvHeight / 180;
					if (sv01.getViewTreeObserver().isAlive()) {
						sv01.getViewTreeObserver().removeGlobalOnLayoutListener(
								this);
					}
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.FILL_PARENT, (itemHeight * 3)
									+ arrow_up.getHeight() * 2);
					llTimeWheel.setLayoutParams(params);
					sv01.setLayoutParams(new LinearLayout.LayoutParams(tv02
							.getWidth(), (itemHeight * 3)));
					sv02.setLayoutParams(new LinearLayout.LayoutParams(tv02
							.getWidth(), (itemHeight * 3)));
					sv01.scrollTo(0, (pHour + 23) * itemHeight);
					sv02.scrollTo(0, (pMinute + 59) * itemHeight);

				}
			});

			setBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					getSettingDate();
					CustomerDateDialog.this.cancel();
				}
			});

			cancleBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CustomerDateDialog.this.cancel();
				}
			});
		}

		private OnTouchListener tListener = new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (v == sv01) {
					flag = 1;
				} else {
					flag = 2;
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					final ScrollView sv = (ScrollView) v;
					lastY = sv.getScrollY();
					System.out.println("lastY" + lastY);
					handler.sendMessageDelayed(handler.obtainMessage(0, v), 50);
				}
				return false;
			}
		};

		private Handler handler = new Handler() {
			@SuppressLint("HandlerLeak")
			public void handleMessage(android.os.Message msg) {
				ScrollView sv = (ScrollView) msg.obj;

				if (msg.what == 0) {
					if (lastY == sv.getScrollY()) {

						int num = lastY / itemHeight;
						int over = lastY % itemHeight;
						if (over > itemHeight / 2) {// 超过一半滚到下一格
							locationTo((num + 1) * itemHeight, sv, flag);
						} else {// 不到一半滚回上一格
							locationTo(num * itemHeight, sv, flag);
						}
					} else {
						lastY = sv.getScrollY();
						handler.sendMessageDelayed(handler.obtainMessage(0, sv), 50);// 滚动还没停止隔50毫秒再判断
					}
				}

			};
		};

		private void locationTo(int position, ScrollView scrollview, int flag) {
			switch (flag) {
			case 1:
				int mPosition = 0;
				if (position <= 23 * itemHeight) {
					mPosition = position + 24 * itemHeight;
					scrollview.scrollTo(0, mPosition);
				} else if (position >= 48 * itemHeight) {
					mPosition = position - 24 * itemHeight;
					scrollview.scrollTo(0, mPosition);
				} else {
					mPosition = position;
					scrollview.smoothScrollTo(0, position);
				}
				setHour = (mPosition / itemHeight - 23) % 24;
				break;

			case 2:
				int hPosition = 0;
				if (position <= 57 * itemHeight) {
					hPosition = position + 60 * itemHeight;
					scrollview.scrollTo(0, hPosition);
				} else if (position >= 120 * itemHeight) {
					hPosition = position - 60 * itemHeight;
					scrollview.scrollTo(0, hPosition);
				} else {
					hPosition = position;
					scrollview.smoothScrollTo(0, position);
				}
				setMinute = (hPosition / itemHeight) % 60 + 1;
				break;
			}

		}

		public void setpHour(int pHour) {
			this.pHour = pHour;
		}

		public void setpMinute(int pMinute) {
			this.pMinute = pMinute;
		}

		public void setOnDateDialogListener(DateDialogListener listener) {
			this.listener = listener;
		}

		public interface DateDialogListener {
			void getDate();
		}

		public void getSettingDate() {
			if (listener != null) {
				listener.getDate();
			}
		}

		public int getSettingHour() {
			return setHour;
		}

		public int getSettingMinute() {
			return setMinute;
		}

	}

	
	*/
	
	
	
	
	
	
	
	
}
