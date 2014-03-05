package com.example.catnap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		System.out.println("receiver works?");
		Toast.makeText(arg0, "Alarm triggered", Toast.LENGTH_LONG).show();
		
	}

}
