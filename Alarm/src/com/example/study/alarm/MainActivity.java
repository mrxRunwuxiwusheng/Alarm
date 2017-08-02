package com.example.study.alarm;

import java.util.ArrayList;

import com.example.global.DateTime;
import com.example.global.Setting;
import com.example.service.MainService;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity extends Activity {

	private Spinner hour;
	private Spinner minute;
	private Switch enable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hour = (Spinner)findViewById(R.id.spinnerhour);
		minute = (Spinner)findViewById(R.id.spinnerminute);
		enable = (Switch)findViewById(R.id.open);
		
		Spinner sp = (Spinner) findViewById(R.id.spinner);
		String[] stringArray = getResources()
				.getStringArray(R.array.defaultNet);
		ArrayList<String> mItems = new ArrayList<String>();
		if (Config.defaultnet != null)
			mItems.add(Config.defaultnet);
		else {
			for (String str : stringArray) {
				mItems.add(str);
			}
		}
		MyArrayAdapter adapter = new MyArrayAdapter(this,
				android.R.layout.simple_spinner_item, mItems);
		sp.setAdapter(adapter);
		Handler handler = new Handler();
		handler.postDelayed(add, 500);// delay 0.5s

		hour.setOnItemSelectedListener(new hourSeletedListener());
		minute.setOnItemSelectedListener(new minuteSeletedListener());
         enable.setOnCheckedChangeListener(enabledListener);
         
         Setting.getSetting(this);
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
				DateTime DT = new DateTime();
				startService(new Intent(MainActivity.this, MainService.class));
			}
		}
		
	};
	
}
