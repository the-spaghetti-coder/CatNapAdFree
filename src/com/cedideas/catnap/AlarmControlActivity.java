package com.cedideas.catnap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AlarmControlActivity extends Activity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_control);
		
		DBHelper db = new DBHelper(this);
		NotificationHelper nh = new NotificationHelper(this);
		
		List<String> checkAlarmAmount = db.getActiveAlarmList();
		
		
//		NotificationCompat.Builder mBuilder = nh.createNotification(this);
//		NotificationManager mNotificationManager =
//			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		int mId = 1;
//		mNotificationManager.notify(mId, mBuilder.build());
		
		
		TextView noAlarms = (TextView)findViewById(R.id.noAlarms);
		Button stopAlarms = (Button)findViewById(R.id.stopAlarm);
		stopAlarms.setVisibility(View.VISIBLE);
		if (checkAlarmAmount.isEmpty()) {
			noAlarms.setVisibility(View.VISIBLE);
			
			NotificationManager aNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			aNotificationManager.cancel(1);
		} else {
			
		}
		
		
		
		
		
//		RelativeLayout rl = (RelativeLayout)findViewById(R.id.alarmControlRL);
//		int childCount = listview.getChildCount();
//		System.out.println("child count: " + String.valueOf(childCount));
		
//		if (childCount!=0){
//			System.out.println("child count is not 0 therefore you are here");
//			
//		}
		
		//		final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.class, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
//		String lastEntry = db.getLastEntry();
		
//		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		final Ringtone r = RingtoneManager.getRingtone(this, alarm);
//		RingtoneManager.
		Button stopAlarm = (Button)findViewById(R.id.stopAlarm);

		stopAlarm.setOnClickListener(new OnClickListener() {
		
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent stopIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
				getApplicationContext().stopService(stopIntent);
			}
		});
		

		
		ListView listview = (ListView)findViewById(R.id.alarmList);
		List<String> alarmList = new ArrayList<String>();
		alarmList = db.getActiveAlarmList();
		
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.alarm_control_custom_list_entry, R.id.alarmName, alarmList);
		listview.setAdapter(aa);
		
		
		
	}

	public void xButtonClicked(View v){
		DBHelper db = new DBHelper(this);
		System.out.println("X Button was clicked!!");
		RelativeLayout RL = (RelativeLayout)v.getParent();
		int rlChildCount = RL.getChildCount();
//		System.out.println("RL child count: " + String.valueOf(rlChildCount));
		TextView alarmDescription = (TextView)RL.getChildAt(0);
		String strAlarmDesc = (String) alarmDescription.getText();
		
		String[] split = strAlarmDesc.split(" ");
		String firstSplit = split[0];
		System.out.println("first split is: >" + firstSplit+"<");
		
		
//		char requestCode = strAlarmDesc.charAt(0);
//		String strReq = String.valueOf(requestCode);
		int intRequestCode =  Integer.valueOf(firstSplit);
		
		
//		System.out.println("request code: " + requestCode);
		db.updateSpecificAlarmStatus(intRequestCode, 0);
		final Intent intent = new Intent(this, AlarmReceiver.class);
		final PendingIntent pt = PendingIntent.getBroadcast(this, intRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		am.cancel(pt);
		
		ListView listview = (ListView)findViewById(R.id.alarmList);
		List<String> alarmList = new ArrayList<String>();
		alarmList = db.getActiveAlarmList();
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.alarm_control_custom_list_entry, R.id.alarmName, alarmList);
		listview.setAdapter(aa);
//		alarmList = db.getActiveAlarmList();
//		listview.setAdapter(adapter)
//		System.out.println("alarm description: " + strAlarmDesc);
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_control, menu);
		return false;
	}

	@Override
	public void onBackPressed()
	{
	    super.onBackPressed(); 
	    startActivity(new Intent(AlarmControlActivity.this, MainActivity.class));
	    finish();

	}
	
}
