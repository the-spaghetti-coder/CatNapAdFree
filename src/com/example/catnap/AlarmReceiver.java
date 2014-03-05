package com.example.catnap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		System.out.println("receiver works?");
		Toast.makeText(arg0, "Alarm triggered", Toast.LENGTH_LONG).show();
		Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
		openNewAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, 2);
        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, 2);
        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        arg0.startActivity(openNewAlarm);
	}

}
