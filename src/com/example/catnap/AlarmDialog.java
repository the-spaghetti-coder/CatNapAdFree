package com.example.catnap;

import java.util.Calendar;

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
import android.widget.Button;
import android.widget.Toast;

public class AlarmDialog extends Activity{

	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_alarmdialog);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
	    Button endAlarm = (Button)findViewById(R.id.endAlarm);
		Button snooze = (Button)findViewById(R.id.snooze);
		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		final Ringtone r = RingtoneManager.getRingtone(this, alarm);

		r.play();
		
		endAlarm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 r.stop();
				 Intent backToCats = new Intent(AlarmDialog.this, NapActivity.class);
				 startActivity(backToCats);
				
			}
		});
		
		snooze.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				r.stop();
				Calendar cal = Calendar.getInstance();
				long currentTime = cal.getTimeInMillis();
				final Intent intent = new Intent(AlarmDialog.this, AlarmReceiver.class);
				final PendingIntent pt = PendingIntent.getBroadcast(AlarmDialog.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				
				Toast.makeText(AlarmDialog.this, "Snoozing for 10 minutes", Toast.LENGTH_LONG).show();
				am.set(AlarmManager.RTC_WAKEUP, currentTime + 600000, pt);
				Intent backToCats = new Intent(AlarmDialog.this, NapActivity.class);
				startActivity(backToCats);
				
				
			}
		});
		
//		displayAlert();
		
	}
	
public void onBackPressed() {
	Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	final Ringtone r = RingtoneManager.getRingtone(this, alarm);
	System.out.println("back pressed");
	r.stop();
}
	
//	public void displayAlert()
//    {
//		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		final Ringtone r = RingtoneManager.getRingtone(this, alarm);
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to exit?").setCancelable(
//                false).setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    	r.play();
//                    	
//                    }
//                }).setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    	r.stop();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}