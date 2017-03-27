package com.rihab.notificationact.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by faten on 23/03/2017.
 */

public class SessionManager {
	public static final String MyPREFERENCES = "MyPrefs" ;
	public void setPreferences(Context context, String key, String value) {

		SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();

	}



	public String getPreferences(Context context, String key) {

		SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES,	Context.MODE_PRIVATE);
		String position = prefs.getString(key, "");
		return position;
	}
}
