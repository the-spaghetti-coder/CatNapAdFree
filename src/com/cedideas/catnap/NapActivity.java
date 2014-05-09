package com.cedideas.catnap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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
		
		final NotificationHelper nh = new NotificationHelper(this);
		final DBHelper db = new DBHelper(this);

		final ImageSwitcher topText = (ImageSwitcher)findViewById(R.id.napTopText);
		final ImageSwitcher sleepyCat = (ImageSwitcher)findViewById(R.id.sleepingCat);
		final ImageSwitcher windowImgSw = (ImageSwitcher)findViewById(R.id.window);
		final ImageSwitcher customImgSw = (ImageSwitcher)findViewById(R.id.customTimer);
		final ImageSwitcher bootImgSw = (ImageSwitcher)findViewById(R.id.catBoot);
		final ImageSwitcher laptopSw = (ImageSwitcher)findViewById(R.id.laptop);
		final ImageSwitcher laundrySw = (ImageSwitcher)findViewById(R.id.laundry);
		
		final int[] topTextImgs = {R.drawable.hdpi_dragcat, R.drawable.hdpi_20min, R.drawable.hdpi_30min, R.drawable.hdpi_45min, R.drawable.hdpi_1hour, R.drawable.hdpi_customnap, R.drawable.gimp_emptybg};
		final int[] sleepyCatImgs = {R.drawable.hdpi_cat, R.drawable.gimp_emptybg, R.drawable.hdpi_naptimesign};
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
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");

					final Calendar cal = Calendar.getInstance();
					final long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 2700000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);

					final DateFormat df4 = new SimpleDateFormat("M/d/yyyy");
					final Date date = new Date();
					String currentMinuteTime = df2.format(date);
					String currentHourTimeTwo = df3.format(date);
					final String currentFormattedDate = df4.format(date);
					final String currentAlarmTime = currentHourTimeTwo + ":" + currentMinuteTime;
					

					long currentDate = date.getTime();
					long futureDate = currentDate + 2700000;
					String futureHourTime = df3.format(futureDate);
					String futureMinuteTime = df2.format(futureDate);

					final String futureAlarmTime = futureHourTime + ":" +  futureMinuteTime; 

					AlertDialog.Builder dialog = new AlertDialog.Builder(NapActivity.this);
					dialog.setTitle("Confirm alarm");
					dialog.setMessage("Your alarm will be set to\n\t " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							
							String alarmname = "45 min alarm";
							cal.setTimeInMillis(calCurrentTime+2700000);
							Date futureCalTime = cal.getTime();
							
							String futureSetHour = df3.format(futureCalTime);
							String futureSetMinute = df2.format(futureCalTime);
							String setAlarmEnd = futureSetHour + ":" + futureSetMinute;
							
							db.insertEntry(currentAlarmTime, setAlarmEnd, alarmname, currentFormattedDate, 1);
							
							NotificationCompat.Builder mBuilder = nh.createNotification(NapActivity.this);
							NotificationManager mNotificationManager =
								    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							int mId = 1;
							mNotificationManager.notify(mId, mBuilder.build());
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							int lastIdRequestCode = db.getLastEntryId();
							intent.putExtra("requestCode", lastIdRequestCode);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, lastIdRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 45 minute nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 2700000, pt);
							System.out.println("45 min nap int test: " + String.valueOf(lastIdRequestCode));
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							startActivity(backToMain);

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
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					final Calendar cal = Calendar.getInstance();
					final long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 1200000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);
					
					final Date date = new Date();
					long currentDate = date.getTime();
					long futureDate = currentDate + 1200000;
					String futureHourTime = df3.format(futureDate);
					String futureMinuteTime = df2.format(futureDate);
					String futureAlarmTime = futureHourTime + ":" +  futureMinuteTime; 

					final DateFormat df4 = new SimpleDateFormat("M/d/yyyy");
					String currentMinuteTime = df2.format(date);
					String currentHourTimeTwo = df3.format(date);
					final String currentFormattedDate = df4.format(date);
					final String currentAlarmTime = currentHourTimeTwo + ":" + currentMinuteTime;

					AlertDialog.Builder dialog = new AlertDialog.Builder(NapActivity.this);
					dialog.setTitle("Confirm alarm");
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							
							NotificationCompat.Builder mBuilder = nh.createNotification(NapActivity.this);
							NotificationManager mNotificationManager =
								    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							int mId = 1;
							mNotificationManager.notify(mId, mBuilder.build());
							
							String alarmname = "20 min alarm";
							cal.setTimeInMillis(calCurrentTime+1200000);
							Date futureCalTime = cal.getTime();
							
							String futureSetHour = df3.format(futureCalTime);
							String futureSetMinute = df2.format(futureCalTime);
							String setAlarmEnd = futureSetHour + ":" + futureSetMinute;
							
							db.insertEntry(currentAlarmTime, setAlarmEnd, alarmname, currentFormattedDate, 1);
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							int lastIdRequestCode = db.getLastEntryId();
							intent.putExtra("requestCode", lastIdRequestCode);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, lastIdRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 20 minute nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 1200000, pt);
							System.out.println("20 min nap int test: " + String.valueOf(lastIdRequestCode));
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							startActivity(backToMain);
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
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					final Calendar cal = Calendar.getInstance();
					final long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 1800000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);

					final DateFormat df4 = new SimpleDateFormat("M/d/yyyy");
					final Date date = new Date();
					String currentMinuteTime = df2.format(date);
					String currentHourTimeTwo = df3.format(date);
					final String currentFormattedDate = df4.format(date);
					final String currentAlarmTime = currentHourTimeTwo + ":" + currentMinuteTime;
					long currentDate = date.getTime();
					long futureDate = currentDate + 1800000;
					String futureHourTime = df3.format(futureDate);
					String futureMinuteTime = df2.format(futureDate);
					String futureAlarmTime = futureHourTime + ":" +  futureMinuteTime; 
;
					AlertDialog.Builder dialog = new AlertDialog.Builder(NapActivity.this);
					dialog.setTitle("Confirm alarm");
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							
							String alarmname = "30 min alarm";
							cal.setTimeInMillis(calCurrentTime+1800000);
							Date futureCalTime = cal.getTime();
							
							String futureSetHour = df3.format(futureCalTime);
							String futureSetMinute = df2.format(futureCalTime);
							String setAlarmEnd = futureSetHour + ":" + futureSetMinute;
							
							db.insertEntry(currentAlarmTime, setAlarmEnd, alarmname, currentFormattedDate, 1);
							
							NotificationCompat.Builder mBuilder = nh.createNotification(NapActivity.this);
							NotificationManager mNotificationManager =
								    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							int mId = 1;
							mNotificationManager.notify(mId, mBuilder.build());
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							int lastIdRequestCode = db.getLastEntryId();
							intent.putExtra("requestCode", lastIdRequestCode);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, lastIdRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 30 minute nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime+ 1800000, pt);
							System.out.println("30 min nap int test: " + String.valueOf(lastIdRequestCode));
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							startActivity(backToMain);

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

					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					
					final Calendar cal = Calendar.getInstance();
					final long calCurrentTime = cal.getTimeInMillis();
					long fourtyFiveFuture = cal.getTimeInMillis() + 3600000;
					Calendar future = Calendar.getInstance();
					future.setTimeInMillis(fourtyFiveFuture);

					final DateFormat df4 = new SimpleDateFormat("M/d/yyyy");
					final Date date = new Date();
					String currentMinuteTime = df2.format(date);
					String currentHourTimeTwo = df3.format(date);
					final String currentFormattedDate = df4.format(date);
					final String currentAlarmTime = currentHourTimeTwo + ":" + currentMinuteTime;
					long currentDate = date.getTime();
					long futureDate = currentDate + 3600000;
					String futureHourTime = df3.format(futureDate);
					String futureMinuteTime = df2.format(futureDate);
					String futureAlarmTime = futureHourTime + ":" +  futureMinuteTime; 
					AlertDialog.Builder dialog = new AlertDialog.Builder(NapActivity.this);
					dialog.setTitle("Confirm alarm");
					dialog.setMessage("Your alarm will be set to\n " + futureAlarmTime);
					dialog.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("positive button");
							sleepyCat.setImageResource(sleepyCatImgs[2]);
							
							String alarmname = "1 hr alarm";
							cal.setTimeInMillis(calCurrentTime+3600000);
							Date futureCalTime = cal.getTime();
							
							String futureSetHour = df3.format(futureCalTime);
							String futureSetMinute = df2.format(futureCalTime);
							String setAlarmEnd = futureSetHour + ":" + futureSetMinute;
							
							db.insertEntry(currentAlarmTime, setAlarmEnd, alarmname, currentFormattedDate, 1);
							
							NotificationCompat.Builder mBuilder = nh.createNotification(NapActivity.this);
							NotificationManager mNotificationManager =
								    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							int mId = 1;
							mNotificationManager.notify(mId, mBuilder.build());
							
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							int lastIdRequestCode = db.getLastEntryId();
							intent.putExtra("requestCode", lastIdRequestCode);
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, lastIdRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							Toast.makeText(NapActivity.this, "Alarm set for 1 hour nap. Enjoy!", Toast.LENGTH_LONG).show();
							am.set(AlarmManager.RTC_WAKEUP, calCurrentTime + 3600000, pt);
							System.out.println("1 hr min nap int test: " + String.valueOf(lastIdRequestCode));
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							startActivity(backToMain);
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
					final DateFormat df3 = new SimpleDateFormat("h");
					final DateFormat df2 = new SimpleDateFormat("mm");
					final DateFormat df4 = new SimpleDateFormat("M/d/yyyy");
					final Date date = new Date();
					
					String currentMinuteTime = df2.format(date);
					String currentHourTimeTwo = df3.format(date);
					final String currentFormattedDate = df4.format(date);
					final String currentAlarmTime = currentHourTimeTwo + ":" + currentMinuteTime;
					final TimePicker timePicker = new TimePicker(NapActivity.this);
				    timePicker.setIs24HourView(false);

				    AlertDialog.Builder ad = new AlertDialog.Builder(NapActivity.this);
				    ad.setView(timePicker);
				    ad.setCancelable(true);
				    ad.setMessage("Choose custom naptime!");
				    ad.setNeutralButton("Set", new DialogInterface.OnClickListener() {
				    	  @Override
				    	  public void onClick(DialogInterface dialog, int which) {

				          int currentHour = timePicker.getCurrentHour();
				    	  int currentMinute = timePicker.getCurrentMinute();
				    	  
				    	  NotificationCompat.Builder mBuilder = nh.createNotification(NapActivity.this);
							NotificationManager mNotificationManager =
								    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							int mId = 1;
							mNotificationManager.notify(mId, mBuilder.build());
							
							Calendar cal = Calendar.getInstance();
							Calendar calCurrent = Calendar.getInstance();
							long currentTime = calCurrent.getTimeInMillis();
							
							cal.set(Calendar.HOUR_OF_DAY, currentHour);
							cal.set(Calendar.MINUTE, currentMinute);
							long setTime = cal.getTimeInMillis();
							long difference = setTime - currentTime;

							Date futureCalTime = cal.getTime();
							
							String futureSetHour = df3.format(futureCalTime);
							String futureSetMinute = df2.format(futureCalTime);
							String setAlarmEnd = futureSetHour + ":" + futureSetMinute;
							String alarmname = "custom alarm";
							System.out.println("set hour: " + futureSetHour + "set minute: " + futureSetMinute + "complete: " + setAlarmEnd);
							
							String differenceToast = String.valueOf(difference/60000);
							System.out.println("current time: " + currentTime + " \nsetTime: " + setTime + " \ndifference:" + difference);
							
							db.insertEntry(currentAlarmTime, setAlarmEnd, alarmname, currentFormattedDate, 1);
							final Intent intent = new Intent(NapActivity.this, AlarmReceiver.class);
							int lastIdRequestCode = db.getLastEntryId();
							
							intent.putExtra("requestCode", lastIdRequestCode);
							System.out.println("last id: " + String.valueOf(lastIdRequestCode));
							final PendingIntent pt = PendingIntent.getBroadcast(NapActivity.this, lastIdRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
							System.out.println("20 min nap int test after pt declaration: " + String.valueOf(lastIdRequestCode));
							am.set(AlarmManager.RTC_WAKEUP, currentTime + difference, pt);
							
							Intent backToMain = new Intent(NapActivity.this, MainActivity.class);
							backToMain.putExtra("customAlarmSet", "Custom alarm set for a " + differenceToast + " minute nap.");
							startActivity(backToMain);
							
							Toast.makeText(NapActivity.this, "Alarm set for a " + differenceToast + " minute nap. Enjoy!", Toast.LENGTH_LONG).show();
				    	  }
				    	  });
				    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println("cancel was clicked");
							
						}
					});
				    ad.show();
					
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
		return false;
	}

	@Override
	public View makeView() {
		DisplayMetrics display = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(display);
	    int screenWidth = display.widthPixels;
	    int oneFourthWidth = screenWidth /3;
	    int oneFourthHeight = screenWidth /3;
		ImageView iView = new ImageView(this);
		iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iView.setLayoutParams(new ImageSwitcher.LayoutParams(oneFourthWidth, oneFourthHeight));
		return iView;
	}

}
