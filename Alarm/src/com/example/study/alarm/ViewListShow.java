package com.example.study.alarm;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class ViewListShow {

	public void getList(Context context) {
		Context mContext = context;
		String state;
		Cursor cursor;
		state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
		}

		Uri albumUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;// EXTERNAL_CONTENT_URI
																	// INTERNAL_CONTENT_URI
		String[] projection = { "_data", "_display_name", "_size", "mime_type", "title", "duration" };
		cursor = mContext.getContentResolver().query(albumUri, projection, null, null, null);
		cursor.moveToFirst(); // 将游标移动到初始位置
		String str;
		String substr;
		do {
			str = cursor.getString(cursor.getColumnIndex("_display_name"));
			substr = str.substring(str.length() - 3, str.length());
			if (substr.equals("mp3")) {
				Log.d("Alarm", ("_data         = " + cursor.getString(cursor.getColumnIndex("_data"))));
				Log.d("Alarm", ("_display_name = " + cursor.getString(cursor.getColumnIndex("_display_name"))));
				Log.d("Alarm", ("title = " + cursor.getString(cursor.getColumnIndex("title"))));
			}
		} while (cursor.moveToNext()); // 将游标移到下一行
	}

}
