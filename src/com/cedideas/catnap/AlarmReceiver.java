package com.cedideas.catnap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		
		System.out.println("receiver works");
		Intent intent = new Intent();
		Bundle bundle = arg1.getExtras();
		if (bundle==null){
			System.out.println("you're screwed");
		} else {
			System.out.println("i guess it isn't null");
		}
		boolean one = bundle.containsKey("requestCode");
		int requestCode = arg1.getExtras().getInt("requestCode");
		String testValue = arg1.getExtras().getString("testvalue");
		String strRequestCode = String.valueOf(requestCode);
		boolean two = bundle.containsKey("testvalue");
		if (one) {
			System.out.println("requestCode received: " + strRequestCode);

		} else {
			System.out.println("super fail");
		}
		
	 if (two) {
		System.out.println("testvalue is : " + testValue);
	 }
//		String testValue = bundle.getString("test");
//		if (testValue!=null) {
//			System.out.println(testValue);
//		} else {
//			System.out.println("that shit is null");
//		}
		
		Intent i = new Intent(context, AlarmDialog.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //add pendingintents to database. clear database everytime the app is closed, therefore temporary database.
	}

	public void stopAlarm() {
		
	}
	
}
