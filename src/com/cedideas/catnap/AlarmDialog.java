package com.cedideas.catnap;

import java.util.Calendar;

import com.cedideas.catnap.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class AlarmDialog extends Activity{

	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_alarmdialog);
		
		final DBHelper db = new DBHelper(this);
		Intent intent = getIntent();
		final int requestCode = intent.getExtras().getInt("requestCode");
		System.out.println("alarm dialog request code is: " + requestCode);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    ImageView endAlarm = (ImageView)findViewById(R.id.dismissbutton);
	    ImageView snooze = (ImageView)findViewById(R.id.snoozebutton);
		Intent serviceIntent = new Intent(this, RingtonePlayingService.class);
		startService(serviceIntent);
		endAlarm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				db.updateSpecificAlarmStatus(requestCode, 0);
				Intent stopIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
				getApplicationContext().stopService(stopIntent);
				Intent backToMain = new Intent(AlarmDialog.this, MainActivity.class);
				startActivity(backToMain);
			}
		});
		
		snooze.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent stopIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
				getApplicationContext().stopService(stopIntent);
				Calendar cal = Calendar.getInstance();
				long currentTime = cal.getTimeInMillis();
				final Intent intent = new Intent(AlarmDialog.this, AlarmReceiver.class);
				final PendingIntent pt = PendingIntent.getBroadcast(AlarmDialog.this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				Toast.makeText(AlarmDialog.this, "Snoozing for 10 minutes", Toast.LENGTH_LONG).show();
				am.set(AlarmManager.RTC_WAKEUP, currentTime + 600000, pt);
				Intent backToMain = new Intent(AlarmDialog.this, MainActivity.class);
				startActivity(backToMain);
			}
		});
	}
	
public void onBackPressed() {
	Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	final Ringtone r = RingtoneManager.getRingtone(this, alarm);
	r.stop();
}
}