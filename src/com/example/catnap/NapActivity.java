package com.example.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import android.provider.AlarmClock;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher.ViewFactory;

public class NapActivity extends Activity implements ViewFactory{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
//		final ImageView sleepyCat = (ImageView)findViewById(R.id.sleepingCat);
		
		final ImageSwitcher sleepyCat = (ImageSwitcher)findViewById(R.id.sleepingCat);
		final ImageSwitcher windowImgSw = (ImageSwitcher)findViewById(R.id.window);
		final ImageSwitcher customImgSw = (ImageSwitcher)findViewById(R.id.customTimer);
		final ImageSwitcher bootImgSw = (ImageSwitcher)findViewById(R.id.catBoot);
		final ImageSwitcher laptopSw = (ImageSwitcher)findViewById(R.id.laptop);
		final ImageSwitcher laundrySw = (ImageSwitcher)findViewById(R.id.laundry);
		
		final int[] sleepyCatImgs = {R.drawable.gimp_catnap_cat, R.drawable.gimp_emptybg, R.drawable.gimp_catnap_naptimesign};
		final int[] catBedImgs = {R.drawable.catbed, R.drawable.catsleeping };
		final int[] customImgs = {R.drawable.gimpcatnap_cardboardbox_pre, R.drawable.gimpcatnap_cardboardbox};
		final int[] catBootImgs = {R.drawable.gimp_catnap_boot_pre, R.drawable.gimp_catnap_boot};
		final int[] catLaptopImgs = {R.drawable.gimp_catnap_laptop_pre, R.drawable.gimp_catnap_laptop};
		final int[] catWindowImgs = {R.drawable.gimp_catnap_windowsill_pre, R.drawable.gimp_catnap_windowsill};
		final int[] catLaundryImgs = {R.drawable.gimp_catnap_laundry_pre, R.drawable.gimp_catnap_laundry};
		
		sleepyCat.setFactory(this);
		sleepyCat.setImageResource(sleepyCatImgs[0]);
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
					sleepyCat.setImageResource(sleepyCatImgs[0]);
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
					sleepyCat.setImageResource(sleepyCatImgs[1]);
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					//
					final Calendar cal = Calendar.getInstance();
					long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 2700000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);
					final int futureHour = future.get(Calendar.HOUR_OF_DAY);
					final int futureMinute = future.get(Calendar.MINUTE);
					
					final Date date = new Date();
					long currentDate = date.getTime();
					long futureDate = currentDate + 2700000;
					String futureHourTime = df3.format(futureDate);
					String futureMinuteTime = df2.format(futureDate);
					String currentHourTime = df.format(date);
					String currentMinuteTime = df2.format(date);
					//
					
					
					final int currentHourTimeNum = Integer.parseInt(currentHourTime);
					final int currentMinuteTimeNum = Integer.parseInt(currentMinuteTime);
					final int futureHourTimeNum = Integer.parseInt(futureHourTime);
					final int futureMinuteTimeNum = Integer.parseInt(futureMinuteTime);
					String futureAlarmTime = futureHourTime + ":" +  futureMinuteTime; 
;
					ClipData clipData = event.getClipData();
					AlertDialog.Builder dialog = new AlertDialog.Builder(NapActivity.this);
					dialog.setTitle("Confirm alarm");
					dialog.setMessage("Your alarm will be set to\n\t " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							Intent openNewAlarmWindow = new Intent(AlarmClock.ACTION_SET_ALARM);
							openNewAlarmWindow.putExtra(AlarmClock.EXTRA_MESSAGE, "CatNapp 45 min alarm");
					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
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
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					final Calendar cal = Calendar.getInstance();
					long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 1200000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);
					final int futureHour = future.get(Calendar.HOUR_OF_DAY);
					final int futureMinute = future.get(Calendar.MINUTE);
					
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
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
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
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					final Calendar cal = Calendar.getInstance();
					long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 1800000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);
					final int futureHour = future.get(Calendar.HOUR_OF_DAY);
					final int futureMinute = future.get(Calendar.MINUTE);
					
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
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
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
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					final Calendar cal = Calendar.getInstance();
					long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 3600000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);
					final int futureHour = future.get(Calendar.HOUR_OF_DAY);
					final int futureMinute = future.get(Calendar.MINUTE);
					
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
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
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
							sleepyCat.setImageResource(sleepyCatImgs[2]);
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
				};
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
		DisplayMetrics display = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(display);
	    int screenWidth = display.widthPixels;
	    int screenHeight = display.heightPixels;
	    int oneFourthWidth = screenWidth /3;
	    int oneFourthHeight = screenWidth /3;
		ImageView iView = new ImageView(this);
		iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iView.setLayoutParams(new ImageSwitcher.LayoutParams(oneFourthWidth, oneFourthHeight));
		return iView;
	}

}
