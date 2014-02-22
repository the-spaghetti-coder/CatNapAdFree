package com.example.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class NapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
		final ImageView sleepyCat = (ImageView)findViewById(R.id.sleepingCat);
		final ImageView catBed = (ImageView)findViewById(R.id.catBed);
		final ImageView happyCat = (ImageView)findViewById(R.id.happyCat);
		final ViewSwitcher vw = (ViewSwitcher)findViewById(R.id.catViewSwitcher);
//		ViewGroup vg = new ViewGroup(this);
		final View checkBoxView = View.inflate(this, R.layout.alarmdialog, null);
		
		
		
		
		sleepyCat.setTag("icon bitmap");
		sleepyCat.setOnLongClickListener(new OnLongClickListener() {
		
			
			
			@Override
			public boolean onLongClick(View v) {
				System.out.println("Long click works!");
				String[] mimetypes = new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag()); 
				ClipData dragData = new ClipData((CharSequence) v.getTag(), mimetypes, item);
				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(sleepyCat);
//				Toast.makeText(NapActivity.this, "Drag started!", Toast.LENGTH_LONG).show();
//				sleepyCat.setVisibility(4);  MAKES INVISIBLE
				return v.startDrag(dragData, myShadow, null, 0);
				
			}
		});
		
		catBed.setOnDragListener(new OnDragListener() {

			
;
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
//					Toast.makeText(NapActivity.this, "You dropped it! Woo!", Toast.LENGTH_SHORT).show();
					System.out.println("actiondragENDED");
//					sleepyCat.setVisibility(0);
					//add confirmation dialog. if yes, then put alarm through with skip ui
					
					
//					AlphaAnimation alpha4 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
//					alpha4.setDuration(0); // Make animation instant
//					 // Tell it to persist after the animation ends
//					// And then on your imageview
//					sleepyCat.startAnimation(alpha4);
					
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
//					Toast.makeText(NapActivity.this, "Entered the bounding zone", Toast.LENGTH_SHORT).show();
					System.out.println("actiondragENTERED");
//					vw.showNext();
					AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
					alpha.setDuration(500); // Make animation instant
					alpha.setFillAfter(false); // Tell it to persist after the animation ends
					// And then on your imageview
					catBed.startAnimation(alpha);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
//					AlphaAnimation alpha2 = new AlphaAnimation(0.5F, 1.0F); // change values as you want
//					alpha2.setDuration(0); // Make animation instant
//					alpha2.setFillAfter(true); // Tell it to persist after the animation ends
//					// And then on your imageview
//					catBed.startAnimation(alpha2);
					
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
//					System.out.println("actiondragLOCATION");
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
//					sleepyCat.setVisibility(4);
					AlphaAnimation alpha3 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
					alpha3.setDuration(0); // Make animation instant
//					alpha3.setFillAfter(true); // Tell it to persist after the animation ends
					// And then on your imageview
					sleepyCat.startAnimation(alpha3);
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					final Date date = new Date();
					long currentDate = date.getTime();
					long futureDate = currentDate + 1800000;
					String futureHourTime = df3.format(futureDate);
					String futureMinuteTime = df2.format(futureDate);
					String currentHourTime = df.format(date);
					String currentMinuteTime = df2.format(date);
					
					final int currentHourTimeNum = Integer.parseInt(currentHourTime);
					final int currentMinuteTimeNum = Integer.parseInt(currentMinuteTime);
					final int futureHourTimeNum = Integer.parseInt(futureHourTime);
					final int futureMinuteTimeNum = Integer.parseInt(futureMinuteTime);
					String futureAlarmTime = futureHourTime + ":" +  futureMinuteTime; 
;
					ClipData clipData = event.getClipData();
					AlertDialog.Builder dialog = new AlertDialog.Builder(NapActivity.this);
					dialog.setTitle("Confirm alarm");
					dialog.setMessage("Your alarm will be set to: " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");

							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, currentHourTimeNum);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, currentMinuteTimeNum + 31);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					        startActivity(openNewAlarm);
						}
					});
					
					dialog.setNegativeButton("Cancel", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							 System.out.println("Cancel");
							
						}
					});
					dialog.setInverseBackgroundForced(true);
					dialog.show();
					
					
					
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
