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
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class AlarmDialog extends Activity{

	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_alarmdialog);
		final Vibrator vibe = (Vibrator)getSystemService(AlarmDialog.this.VIBRATOR_SERVICE);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
	    ImageView endAlarm = (ImageView)findViewById(R.id.dismissbutton);
	    ImageView snooze = (ImageView)findViewById(R.id.snoozebutton);
		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		final Ringtone r = RingtoneManager.getRingtone(this, alarm);
		
		boolean hasVibe = vibe.hasVibrator();
		long[] vibePattern = {0, 1000, 500, 250, 250};
		if (hasVibe) {
			vibe.vibrate(vibePattern, 4);
		}
		
		
		r.play();
		
		endAlarm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 r.stop();
				 Intent backToMain = new Intent(AlarmDialog.this, MainActivity.class);
				 startActivity(backToMain);
				 vibe.cancel();
			}
		});
		
		snooze.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				vibe.cancel();
				r.stop();
				Calendar cal = Calendar.getInstance();
				long currentTime = cal.getTimeInMillis();
				final Intent intent = new Intent(AlarmDialog.this, AlarmReceiver.class);
				final PendingIntent pt = PendingIntent.getBroadcast(AlarmDialog.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				
				Toast.makeText(AlarmDialog.this, "Snoozing for 10 minutes", Toast.LENGTH_LONG).show();
				am.set(AlarmManager.RTC_WAKEUP, currentTime + 600000, pt);
				Intent backToMain = new Intent(AlarmDialog.this, MainActivity.class);
				 startActivity(backToMain);
				
				
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