package com.example.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;
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
		
		happyCat.setVisibility(4);
		
		
		
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
					
					happyCat.setVisibility(0);
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
					DateFormat df = new SimpleDateFormat("H");
					DateFormat df2 = new SimpleDateFormat("mm");
					Date date = new Date();
					
					Dialog dialog = new Dialog(NapActivity.this);
					dialog.setTitle("title");
					dialog.addContentView(happyCat, null);
					dialog.show();
					
					String currentHourTime = df.format(date);
					String currentMinuteTime = df2.format(date);
					ClipData clipData = event.getClipData();
					final int currentHourTimeNum = Integer.parseInt(currentHourTime);
					final int currentMinuteTimeNum = Integer.parseInt(currentMinuteTime);
					Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
			        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, currentHourTimeNum);
			        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, currentMinuteTimeNum + 31);
			        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
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
