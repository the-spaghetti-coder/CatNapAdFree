package com.example.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class NapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
		final ImageView sleepyCat = (ImageView)findViewById(R.id.sleepingCat);
		final ImageView droid = (ImageView)findViewById(R.id.catBed);
		

		
		
		
		
		sleepyCat.setTag("icon bitmap");
		sleepyCat.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				System.out.println("Long click works!");
				String[] mimetypes = new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag()); 
				ClipData dragData = new ClipData((CharSequence) v.getTag(), mimetypes, item);
				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(sleepyCat);
				Toast.makeText(NapActivity.this, "Drag started!", Toast.LENGTH_LONG).show();
//				sleepyCat.setVisibility(4);  MAKES INVISIBLE
				return v.startDrag(dragData, myShadow, null, 0);
				
			}
		});
		
		droid.setOnDragListener(new OnDragListener() {

			
;
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
//					Toast.makeText(NapActivity.this, "You dropped it! Woo!", Toast.LENGTH_SHORT).show();
					System.out.println("actiondragENDED");
					
					//add confirmation dialog. if yes, then put alarm through with skip ui

					break;
				case DragEvent.ACTION_DRAG_ENTERED:
//					Toast.makeText(NapActivity.this, "Entered the bounding zone", Toast.LENGTH_SHORT).show();
					System.out.println("actiondragENTERED");
					
					droid.setVisibility(4);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					droid.setVisibility(0);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					System.out.println("actiondragLOCATION");
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					DateFormat df = new SimpleDateFormat("H");
					DateFormat df2 = new SimpleDateFormat("mm");
					Date date = new Date();
					
					String currentHourTime = df.format(date);
					String currentMinuteTime = df2.format(date);
					ClipData clipData = event.getClipData();
					final int currentHourTimeNum = Integer.parseInt(currentHourTime);
					final int currentMinuteTimeNum = Integer.parseInt(currentMinuteTime);
					Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
			        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, currentHourTimeNum);
			        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, currentMinuteTimeNum + 30);
			        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
			        startActivity(openNewAlarm);
					return true;
				default:
					System.out.println("fail");
					return false;
				}
				;
				return false;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nap, menu);
		return true;
	}

}
