package com.example.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.AlarmClock;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher.ViewFactory;

public class NapActivity extends Activity implements ViewFactory{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
		final ImageView sleepyCat = (ImageView)findViewById(R.id.sleepingCat);

		final ImageSwitcher windowImgSw = (ImageSwitcher)findViewById(R.id.window);
		final ImageSwitcher customImgSw = (ImageSwitcher)findViewById(R.id.customTimer);
		final ImageSwitcher bootImgSw = (ImageSwitcher)findViewById(R.id.catBoot);
		final ImageSwitcher laptopSw = (ImageSwitcher)findViewById(R.id.laptop);
		final ImageSwitcher laundrySw = (ImageSwitcher)findViewById(R.id.laundry);
		
		final int[] catBedImgs = {R.drawable.catbed, R.drawable.catsleeping };
		final int[] customImgs = {R.drawable.vcatnap_cardboard, R.drawable.v2catnap_cardboard};
		final int[] catBootImgs = {R.drawable.vcatnap_boot, R.drawable.v2catnap_boot};
		final int[] catLaptopImgs = {R.drawable.vcatnap_laptop, R.drawable.catsleeping};
		final int[] catWindowImgs = {R.drawable.vcatnap_window, R.drawable.v2catnap_boot};
		final int[] catLaundryImgs = {R.drawable.vcatnap_laundry, R.drawable.v2catnap_boot};
		
		windowImgSw.setFactory(this);
		windowImgSw.setImageResource(catWindowImgs[0]);
		customImgSw.setFactory(this);
		customImgSw.setImageResource(customImgs[0]);
		bootImgSw.setFactory(this);
		bootImgSw.setImageResource(catBootImgs[0]);
		laptopSw.setFactory(this);
		laptopSw.setImageResource(catLaptopImgs[0]);
		laundrySw.setFactory(this);
		laundrySw.setImageResource(catLaundryImgs[0]);
		
		
		sleepyCat.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String[] mimetypes = new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag()); 
				ClipData dragData = new ClipData((CharSequence) v.getTag(), mimetypes, item);
				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(sleepyCat);
				return v.startDrag(dragData, myShadow, null, 0);
				
			}
		});
		
		sleepyCat.setTag("icon bitmap");

		
		windowImgSw.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
					System.out.println("actiondragENDED");
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("actiondragENTERED");
					AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
					alpha.setDuration(500); // Make animation instant
					alpha.setFillAfter(false); // Tell it to persist after the animation ends
					windowImgSw.setImageResource(catWindowImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					windowImgSw.setImageResource(catWindowImgs[0]);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					AlphaAnimation alpha3 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
					alpha3.setDuration(0); // Make animation instant
					sleepyCat.startAnimation(alpha3);
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					final Date date = new Date();
					long currentDate = date.getTime();
					long futureDate = currentDate + 2700000;
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
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");

							Intent openNewAlarmWindow = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_HOUR, currentHourTimeNum);
					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_MINUTES, currentMinuteTimeNum + 46);
					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					        startActivity(openNewAlarmWindow);
						}
					});
					
					dialog.setNegativeButton("Cancel", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							 windowImgSw.setImageResource(catWindowImgs[0]);
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
		
		bootImgSw.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
					System.out.println("actiondragENDED");
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("actiondragENTERED");
					AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
					alpha.setDuration(500); // Make animation instant
					alpha.setFillAfter(false); // Tell it to persist after the animation ends
					bootImgSw.setImageResource(catBootImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					bootImgSw.setImageResource(catBootImgs[0]);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					AlphaAnimation alpha3 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
					alpha3.setDuration(0); // Make animation instant
					sleepyCat.startAnimation(alpha3);
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					final Date date = new Date();
					long currentDate = date.getTime();
					long futureDate = currentDate + 1200000;
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
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");

							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, currentHourTimeNum);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, currentMinuteTimeNum + 21);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					        startActivity(openNewAlarm);
						}
					});
					
					dialog.setNegativeButton("Cancel", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							bootImgSw.setImageResource(catBootImgs[0]);
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

		laptopSw.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
					System.out.println("actiondragENDED");
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("actiondragENTERED");
					AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
					alpha.setDuration(500); // Make animation instant
					alpha.setFillAfter(false); // Tell it to persist after the animation ends
					laptopSw.setImageResource(catLaptopImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					laptopSw.setImageResource(catLaptopImgs[0]);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					AlphaAnimation alpha3 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
					alpha3.setDuration(0); // Make animation instant
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
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
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
							laptopSw.setImageResource(catLaptopImgs[0]);
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

		laundrySw.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
					System.out.println("actiondragENDED");
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("actiondragENTERED");
					AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
					alpha.setDuration(500); // Make animation instant
					alpha.setFillAfter(false); // Tell it to persist after the animation ends
					laundrySw.setImageResource(catLaundryImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					laundrySw.setImageResource(catLaundryImgs[0]);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					AlphaAnimation alpha3 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
					alpha3.setDuration(0); // Make animation instant
					sleepyCat.startAnimation(alpha3);
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					final Date date = new Date();
					long currentDate = date.getTime();
					long futureDate = currentDate + 3600000;
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
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");

							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, currentHourTimeNum + 1);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, currentMinuteTimeNum);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					        startActivity(openNewAlarm);
						}
					});
					
					dialog.setNegativeButton("Cancel", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							laundrySw.setImageResource(catLaundryImgs[0]);
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
		
		customImgSw.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
					System.out.println("actiondragENDED");
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("actiondragENTERED");
					AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
					alpha.setDuration(500); // Make animation instant
					alpha.setFillAfter(false); // Tell it to persist after the animation ends
					customImgSw.setImageResource(customImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					customImgSw.setImageResource(customImgs[0]);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					AlphaAnimation alpha3 = new AlphaAnimation(0.0F, 0.0F); // change values as you want
					alpha3.setDuration(0); // Make animation instant
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
					
					TimePickerDialog.OnTimeSetListener tpListener = new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							System.out.println("tpLister");
							
							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, hourOfDay);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, minute);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					        startActivity(openNewAlarm);
							
						}
					};

					
					TimePickerDialog tpDialog = new TimePickerDialog(NapActivity.this, tpListener, currentHourTimeNum, currentMinuteTimeNum, false);
					tpDialog.setMessage("Set custom naptime!");
					tpDialog.setCancelable(true);
					
					tpDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("Test");
							
						}
					});
					tpDialog.show();
					
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
		getMenuInflater().inflate(R.menu.nap, menu);
		return true;
	}

	@Override
	public View makeView() {
		ImageView iView = new ImageView(this);
		iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iView.setLayoutParams(new ImageSwitcher.LayoutParams(100,100));
		return iView;
	}

}
