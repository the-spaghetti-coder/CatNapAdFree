package com.cedideas.catnap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		
		System.out.println("receiver works");

		Intent i = new Intent(context, AlarmDialog.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //add pendingintents to database. clear database everytime the app is closed, therefore temporary database.
	}

	public void stopAlarm() {
		
	}
	
}
