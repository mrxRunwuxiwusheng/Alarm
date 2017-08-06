package com.example.study.alarm;

import java.util.ArrayList;

import com.example.global.DateTime;
import com.example.global.Monitor;
import com.example.global.Setting;
import com.example.service.MainService;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity extends Activity {

	private Spinner hour;
	private Spinner minute;
	private Switch enable;
	private Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hour = (Spinner)findViewById(R.id.spinnerhour);
		minute = (Spinner)findViewById(R.id.spinnerminute);
		enable = (Switch)findViewById(R.id.open);
        Setting.getSetting(this);

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
		enable.setOnCheckedChangeListener(enabledListener);

/*		if (monitor == null) {
			monitor = new Monitor();
			mIntent = new Intent(MainActivity.this, MainService.class);
		//	startService(mIntent);
		}*/
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
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	private OnCheckedChangeListener enabledListener = new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				Log.d("Alarm", "isChecked = true");
				mIntent = new Intent(MainActivity.this, MainService.class);
				startService(mIntent);
			}
		}
	};
	
}
