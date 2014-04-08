package com.cedideas.catnap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AlarmControlActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_control);
		
		ListView listview = (ListView)findViewById(R.id.alarmList);
		List<String> alarmList = new ArrayList<String>();
		String yes = "yes";
		alarmList.add(yes);
		ArrayAdapter aa = new ArrayAdapter<String>(this, R.layout.alarmcontrol_listview, alarmList);
		
		listview.setAdapter(aa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_control, menu);
		return true;
	}

}
