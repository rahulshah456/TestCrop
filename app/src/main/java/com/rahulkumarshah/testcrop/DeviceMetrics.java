package com.rahulkumarshah.testcrop;

import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceMetrics {


	//Obtain DisplayMetrics
	public static DisplayMetrics getDisplayMetrics(Context context) {
		if (context == null) {
			return null;
		}
		return context.getResources().getDisplayMetrics();
	}

	//Get display width
	public static int getDisplayWidth(Context context) {
		return getDisplayMetrics(context).widthPixels;
	}


	//Get display height
	public static int getDisplayHeight(Context context) {
		return getDisplayMetrics(context).heightPixels;
	}
}
