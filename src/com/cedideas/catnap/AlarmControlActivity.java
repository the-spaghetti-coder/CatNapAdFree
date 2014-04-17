package com.cedideas.catnap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AlarmControlActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_control);
		
		DBHelper db = new DBHelper(this);
		
		String lastEntry = db.getLastEntry();
		
		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		final Ringtone r = RingtoneManager.getRingtone(this, alarm);
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
		String yes = "yes";
		alarmList.add(lastEntry);
		ArrayAdapter aa = new ArrayAdapter<String>(this, R.layout.alarm_control_custom_list_entry, R.id.alarmName, alarmList);
		
		listview.setAdapter(aa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_control, menu);
		return true;
	}

	@Override
	public void onBackPressed()
	{
	    super.onBackPressed(); 
	    startActivity(new Intent(AlarmControlActivity.this, MainActivity.class));
	    finish();

	}
	
}
