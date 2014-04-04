package com.cedideas.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cedideas.catnap.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class NapActivity extends Activity implements ViewFactory{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
//		final ImageView sleepyCat = (ImageView)findViewById(R.id.sleepingCat);
		
		final ImageSwitcher topText = (ImageSwitcher)findViewById(R.id.napTopText);
		final ImageSwitcher sleepyCat = (ImageSwitcher)findViewById(R.id.sleepingCat);
		final ImageSwitcher windowImgSw = (ImageSwitcher)findViewById(R.id.window);
		final ImageSwitcher customImgSw = (ImageSwitcher)findViewById(R.id.customTimer);
		final ImageSwitcher bootImgSw = (ImageSwitcher)findViewById(R.id.catBoot);
		final ImageSwitcher laptopSw = (ImageSwitcher)findViewById(R.id.laptop);
		final ImageSwitcher laundrySw = (ImageSwitcher)findViewById(R.id.laundry);
		
		final int[] topTextImgs = {R.drawable.hdpi_dragcat, R.drawable.hdpi_20min, R.drawable.hdpi_30min, R.drawable.hdpi_45min, R.drawable.hdpi_1hour, R.drawable.hdpi_customnap, R.drawable.gimp_emptybg};
		final int[] sleepyCatImgs = {R.drawable.hdpi_cat, R.drawable.gimp_emptybg, R.drawable.hdpi_naptimesign};
		final int[] catBedImgs = {R.drawable.catbed, R.drawable.catsleeping };
		final int[] customImgs = {R.drawable.hdpi_box_empty, R.drawable.hdpi_box};
		final int[] catBootImgs = {R.drawable.hdpi_boot_empty, R.drawable.hdpi_boot};
		final int[] catLaptopImgs = {R.drawable.hdpi_laptop_empty, R.drawable.hdpi_laptop};
		final int[] catWindowImgs = {R.drawable.hdpi_windowsill_pre, R.drawable.hdpi_windowsill};
		final int[] catLaundryImgs = {R.drawable.hdpi_laundry_pre, R.drawable.hdpi_laundry};
		
		topText.setFactory(this);
		topText.setImageResource(topTextImgs[0]);
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
					topText.setImageResource(topTextImgs[0]);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("actiondragENTERED");
					topText.setImageResource(topTextImgs[3]);
					windowImgSw.setImageResource(catWindowImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					windowImgSw.setImageResource(catWindowImgs[0]);
					topText.setImageResource(topTextImgs[6]);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					break;
				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("actiondragSTARTED");
					topText.setImageResource(topTextImgs[6]);
					sleepyCat.setImageResource(sleepyCatImgs[1]);
					
					return true;
				case DragEvent.ACTION_DROP:
					System.out.println("actionDROP");
					final DateFormat df = new SimpleDateFormat("H");
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					//
					final Calendar cal = Calendar.getInstance();
					final long calCurrentTime = cal.getTimeInMillis();
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
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 45 minute nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 2700000, pt);
							
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							 startActivity(backToMain);
							
//							Intent openNewAlarmWindow = new Intent(AlarmClock.ACTION_SET_ALARM);
//							openNewAlarmWindow.putExtra(AlarmClock.EXTRA_MESSAGE, "CatNapp 45 min alarm");
//					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
//					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
//					        openNewAlarmWindow.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//					        startActivity(openNewAlarmWindow);
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
					topText.setImageResource(topTextImgs[1]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					bootImgSw.setImageResource(catBootImgs[0]);
					topText.setImageResource(topTextImgs[6]);
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
					final long calCurrentTime = cal.getTimeInMillis();
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
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 20 minute nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 1200000, pt);
							
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							 startActivity(backToMain);
							
//							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//					        startActivity(openNewAlarm);
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
					topText.setImageResource(topTextImgs[2]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					laptopSw.setImageResource(catLaptopImgs[0]);
					topText.setImageResource(topTextImgs[6]);
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
					final long calCurrentTime = cal.getTimeInMillis();
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
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 30 minute nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 1800000, pt);
							
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							 startActivity(backToMain);
							
//							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//					        startActivity(openNewAlarm);
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
					topText.setImageResource(topTextImgs[4]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					laundrySw.setImageResource(catLaundryImgs[0]);
					topText.setImageResource(topTextImgs[6]);
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
					final long calCurrentTime = cal.getTimeInMillis();
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
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 1 hour nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 3600000, pt);
							
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							 startActivity(backToMain);
							
//							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, futureHour);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, futureMinute);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//					        startActivity(openNewAlarm);
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
					topText.setImageResource(topTextImgs[5]);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("actiondragEXITED");
					customImgSw.setImageResource(customImgs[0]);
					topText.setImageResource(topTextImgs[6]);
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
							
							Calendar cal = Calendar.getInstance();
							Calendar calCurrent = Calendar.getInstance();
							long currentTime = calCurrent.getTimeInMillis();
							cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
							cal.set(Calendar.MINUTE, minute);
							long setTime = cal.getTimeInMillis();
							long difference = setTime - currentTime;
							String differenceToast = String.valueOf(difference/60000);
							System.out.println("current time: " + currentTime + " \nsetTime: " + setTime + " \ndifference:" + difference);
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							
							am.set(AlarmManager.RTC_WAKEUP, currentTime + difference, pt);
							
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							backToMain.putExtra("customAlarmSet", "Custom alarm set for a " + differenceToast + " minute nap.");
							startActivity(backToMain);
							
							Toast.makeText(NapActivity.this, "Alarm set for a " + differenceToast + " minute nap. Enjoy!", Toast.LENGTH_LONG).show();
//							Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, hourOfDay);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, minute);
//					        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//					        startActivity(openNewAlarm);
							
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
