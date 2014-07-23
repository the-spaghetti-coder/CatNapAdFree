package com.cedideas.catnapadfree;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		
//		final SharedPreferences prefs = getApplicationContext().getSharedPreferences(
//			      "com.cedideas.catnap", Context.MODE_PRIVATE);
		
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		final String alarmPreference = "alarmChoice";
		
		
		
//		RadioButton catAlarm =(RadioButton)findViewById(R.id.catAlarmRadioButton);
//		RadioButton regularAlarm =(RadioButton)findViewById(R.id.regularAlarmRadioButton);
		ImageView alarmChoice = (ImageView)findViewById(R.id.alarmChoice);
		
		
		
		stopAlarms.setVisibility(View.VISIBLE);
		if (checkAlarmAmount.isEmpty()) {
			noAlarms.setVisibility(View.VISIBLE);
			
			NotificationManager aNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			aNotificationManager.cancel(1);
		}
		
		alarmChoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(AlarmControlActivity.this);
				final RadioGroup rg = new RadioGroup(AlarmControlActivity.this);
				final RadioButton rOne = new RadioButton(AlarmControlActivity.this);
				final RadioButton rTwo = new RadioButton(AlarmControlActivity.this);
				rOne.setText("Cats Meowing");
				rTwo.setText("Normal Alarm");
				rg.addView(rOne);
				rg.addView(rTwo);
				dialog.setView(rg);
				dialog.setTitle("Choose your alarm sound");
				dialog.setCancelable(true);
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int rOneId = rOne.getId();
						int rTwoId = rTwo.getId();
						CharSequence rOneCharS = rOne.getText();
						CharSequence rTwoCharS = rTwo.getText();
						
						int rbId = rg.getCheckedRadioButtonId();
						if (rbId == rOneId){
							prefs.edit().putString(alarmPreference, "Meowing").commit();
							prefs.edit().apply();
							Toast.makeText(AlarmControlActivity.this, "Alarm sound changed to '"+ rOneCharS + "'", Toast.LENGTH_LONG).show();
							String alarmStoredPreference = prefs.getString(alarmPreference, null);
							System.out.println("Alarm preference read: " + prefs.getString(alarmPreference, null));
						}
						else {
							prefs.edit().putString(alarmPreference, "Normal").commit();
							prefs.edit().apply();
							String alarmStoredPreferenceTwo = prefs.getString(alarmPreference, null);
							Toast.makeText(AlarmControlActivity.this, "Alarm sound changed to '"+ rTwoCharS + "'", Toast.LENGTH_LONG).show();
							System.out.println("Alarm preference read: " + prefs.getString(alarmPreference, null));
						}
					}
				});
				dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
					}
				});
				dialog.show();
			}
		});
		
//		catAlarm.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Toast.makeText(AlarmControlActivity.this, "Cat alarm chosen as alarm sound!", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
//		
//		regularAlarm.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Toast.makeText(AlarmControlActivity.this, "Regular alarm chosen as alarm sound!", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
		
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
