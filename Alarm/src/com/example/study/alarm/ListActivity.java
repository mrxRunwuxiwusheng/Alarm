package com.example.study.alarm;

import java.util.ArrayList;

import com.example.parts.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ListActivity extends Activity{// implements OnItemClickListener{
/*
	ArrayList<String> titleList = new ArrayList<String>();
	ArrayList<String> dataList = new ArrayList<String>();*/
	private ScrollView sv01, sv02;
	private TextView tv01, tv02;
	private TextView arrow_up;
	private LinearLayout llTimeWheel;
	private int lastY;
	private int flag;// 标记时分
	private int itemHeight;// 每一行的高度
	private int pHour, pMinute;// 初始化时显示的时分时间
	private int setHour, setMinute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Alarm", "ListActivity Create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
	  //   customView = LayoutInflater.from(this).inflate(R.layout.time_wheel, null);  
		 tv01 = (TextView) findViewById(R.id.tv01);  
		 tv02 = (TextView) findViewById(R.id.tv02);  
		 sv01 = (ScrollView) findViewById(R.id.sv01);  
		 sv02 = (ScrollView) findViewById(R.id.sv02); 
		 arrow_up = (TextView) findViewById(R.id.arrow_up);  
		 llTimeWheel = (LinearLayout) findViewById(R.id.ll_time_wheel);  

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
					sv01.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
						(itemHeight * 3) + arrow_up.getHeight() * 2);
				llTimeWheel.setLayoutParams(params);
				sv01.setLayoutParams(new LinearLayout.LayoutParams(tv01.getWidth(), (itemHeight * 3)));
				sv02.setLayoutParams(new LinearLayout.LayoutParams(tv02.getWidth(), (itemHeight * 3)));
				sv01.scrollTo(0, (pHour + 23) * itemHeight);
				sv02.scrollTo(0, (pMinute + 59) * itemHeight);
			}
		});
	}

	private void setHourDial(TextView tv) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 24; j++) {
				if (j <= 9) {
					buff.append("0" + j);
				} else {
					buff.append(j + "");
				}
			}
		}
		Log.d("Alarm", "buff=="+buff);
		tv.setText(buff);
	}

	private void setMinuteDial(TextView tv) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 60; j++) {
				if (j <= 9) {
					buff.append("0" + j);
				} else {
					buff.append(j + "");
				}
			}
		}
		tv.setText(buff);
	}
	
	private OnTouchListener tListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			Log.d("Alarm", "OnTouchListener");
			if (v == sv01) {
				flag = 1;
			} else {
				flag = 2;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				final ScrollView sv = (ScrollView) v;
				lastY = sv.getScrollY();
				handler.sendMessageDelayed(handler.obtainMessage(0, v), 50);
			}
			Log.d("Alarm", "OnTouchListener return"+"  v="+v+"  event="+event);
			return false;
		}
	};
	

	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			Log.d("Alarm", "handler handlemessage");
			ScrollView sv = (ScrollView) msg.obj;
			if (msg.what == 0) {
				if (lastY == sv.getScrollY()) {
					Log.d("Alarm", "(lastY == sv.getScrollY())");
					Log.d("Alarm", "lastY="+lastY+"   itemHeight="+itemHeight);
					int num = lastY / itemHeight;
					int over = lastY % itemHeight;
					Log.d("Alarm", "111num="+num+"   over="+over);
					if (over > itemHeight / 2) {// 超过一半滚到下一格
						Log.d("Alarm", "111num="+num+"   over="+over);
						locationTo((num + 1) * itemHeight, sv, flag);
					} else {// 不到一半滚回上一格
						Log.d("Alarm", "222num="+num+"   over="+over);
						locationTo(num * itemHeight, sv, flag);
					}
				} else {
					Log.d("Alarm", "else");
					lastY = sv.getScrollY();
					handler.sendMessageDelayed(handler.obtainMessage(0, sv), 50);// 滚动还没停止隔50毫秒再判断
				}
			}
			Log.d("Alarm", "handle message end");
		};
	};
	
	private void locationTo(int position, ScrollView scrollview, int flag) {
		Log.d("Alarm", "locationto");
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
			Log.d("Alarm", "setHour="+setHour);
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
			Log.d("Alarm", "setMinute="+setMinute);
			break;
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
