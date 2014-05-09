package com.cedideas.catnap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		List<String> checkAlarmAmount = db.getActiveAlarmList();
		TextView noAlarms = (TextView)findViewById(R.id.noAlarms);
		Button stopAlarms = (Button)findViewById(R.id.stopAlarm);
		ListView listview = (ListView)findViewById(R.id.alarmList);
		List<String> alarmList = new ArrayList<String>();
		
		stopAlarms.setVisibility(View.VISIBLE);
		if (checkAlarmAmount.isEmpty()) {
			noAlarms.setVisibility(View.VISIBLE);
			
			NotificationManager aNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			aNotificationManager.cancel(1);
		}
		
		stopAlarms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent stopIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
				getApplicationContext().stopService(stopIntent);
			}
		});
				
		alarmList = db.getActiveAlarmList();
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.alarm_control_custom_list_entry, R.id.alarmName, alarmList);
		listview.setAdapter(aa);
	}

	public void xButtonClicked(View v){
		DBHelper db = new DBHelper(this);
		RelativeLayout RL = (RelativeLayout)v.getParent();
		TextView alarmDescription = (TextView)RL.getChildAt(0);
		String strAlarmDesc = (String) alarmDescription.getText();
		String[] split = strAlarmDesc.split(" ");
		String firstSplit = split[0];
		int intRequestCode =  Integer.valueOf(firstSplit);
		
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
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
