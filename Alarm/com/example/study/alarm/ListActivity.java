package com.example.study.alarm;

import java.util.ArrayList;

import com.example.parts.music;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity{// implements OnItemClickListener{

	ArrayList<String> titleList = new ArrayList<String>();
	ArrayList<String> dataList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Alarm", "ListActivity Create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		getList(this);
		ListView nameList = (ListView) this.findViewById(R.id.nameList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
		nameList.setAdapter(adapter);
		// 为列表添加监听事件
		// nameList.setOnItemClickListener(this);
		nameList.setOnItemClickListener(new OnItemClickListener(){
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
	                // TODO Auto-generated method stub
	            	music.myUriStr = dataList.get(arg2);
	            	finish();
	            }
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
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
