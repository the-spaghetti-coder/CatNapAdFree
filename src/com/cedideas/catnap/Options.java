package com.cedideas.catnap;

import java.util.Calendar;

import com.cedideas.catnap.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Options extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		Calendar cal = Calendar.getInstance();
		
		int hourAhead = cal.get(Calendar.HOUR) + 1;
		System.out.println(String.valueOf(hourAhead));
		final long currentTime = cal.getTimeInMillis();
		long fourtyFiveFuture = cal.getTimeInMillis() + 2700000;
		
		Calendar future = Calendar.getInstance();
		future.setTimeInMillis(fourtyFiveFuture);
		final int futureHour = future.get(Calendar.HOUR);
		final int futureMinute = future.get(Calendar.MINUTE);
		
		Button btn = (Button)findViewById(R.id.hour);

				System.out.println("Future hour: " + String.valueOf(futureHour) + "\nFuture minute: " + String.valueOf(futureMinute));
				
//				final Intent intent = new Intent(this, AlarmReceiver.class);
//				final PendingIntent pt = PendingIntent.getBroadcast(Options.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//				final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//				
//				am.set(AlarmManager.RTC_WAKEUP, currentTime + 5000, pt);
//				Options.this.getAlarmManager().cancel(pt);
//				am.cancel(pt);
//				PendingIntent mAlarmPendingIntent = PendingIntent.getActivity(Options.this, 0, intent, 0);
////				am.cancel(mAlarmPendingIntent);
//				
//				String ptOne = pt.toString();
//				String ptTwo = mAlarmPendingIntent.toString();
//				int hashCode = pt.hashCode();
//				int hashCode2 = mAlarmPendingIntent.hashCode();
//				String hashString = String.valueOf(hashCode);
//				String hashString2 = String.valueOf(hashCode2);
//				
//				System.out.println(">>>1: " + ptOne + " \n>>>2: " + ptTwo + " \n>>>hashcode1: " + hashString + " \n>>>hashcode2: " + hashString2);
//				
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						final Intent intent = new Intent(Options.this, AlarmReceiver.class);
						final PendingIntent pt = PendingIntent.getBroadcast(Options.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
						final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
						
						am.set(AlarmManager.RTC_WAKEUP, currentTime + 3000, pt);

						
//							AlarmManager am2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//							Intent i = new Intent(Options.this, AlarmReceiver.class);
//							boolean intentTest = i.filterEquals(intent);
//							if (intentTest) { 
//								System.out.println("the intents are equal");
//								
//							} else {
//								System.out.println("the intents are NOT EQUAL");
//							}
//							PendingIntent pt2 = PendingIntent.getBroadcast(getBaseContext(), 0, i, 0);
//							am2.cancel(pt2);
//							System.out.println("Options Buttonpress");
					}
				});
		
	}

		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}



}
