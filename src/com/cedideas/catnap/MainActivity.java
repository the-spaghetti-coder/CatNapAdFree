package com.cedideas.catnap;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.catnap.util.SystemUiHider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
AnimationDrawable bgAnim;
private AdView mAdView;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DBHelper db = new DBHelper(this);
		NotificationHelper nh = new NotificationHelper(this);
		List<String> checkAlarmAmount = db.getActiveAlarmList();
		if (checkAlarmAmount.isEmpty()) {
			
			
			NotificationManager aNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			aNotificationManager.cancel(1);
		}
		///////////////////////////TEST ENVIRONMENT/////////////////////////////
//		
//		  final TimePicker timePicker = new TimePicker(this);
//		    timePicker.setIs24HourView(false);
//		    
//
//
//		    AlertDialog.Builder ad = new AlertDialog.Builder(this);
//		    ad.setView(timePicker);
//		    ad.setCancelable(true);
//		    ad.setMessage("Choose custom naptime!");
//		    ad.setNeutralButton("Set", new DialogInterface.OnClickListener() {
//		    	  @Override
//		    	  public void onClick(DialogInterface dialog, int which) {
//		    	// TODO Auto-generated method stub
//		          int currentHour = timePicker.getCurrentHour();
//		    	  Toast.makeText(getApplicationContext(), "Current hour is: " + String.valueOf(currentHour), Toast.LENGTH_LONG).show();
//		    	  }
//		    	  });
//		    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//		    ad.show();
		            
		
		
//		int lastIdRequestCode = db.getLastEntryId();
//		System.out.println(String.valueOf("main activity int test: " + lastIdRequestCode));
//		db.getLastEntryId();
		
//		db.updateSpecificAlarmStatus(2, 0);

//		final Intent intent = new Intent(this, AlarmReceiver.class);
//		final PendingIntent pt = PendingIntent.getBroadcast(this, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		final AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//		System.out.println("^mainactivity cancel");
//		am.cancel(pt);
		
		
		
		///////////////////////////TEST ENVIRONMENT/////////////////////////////
		
		
//		Intent serviceIntent = new Intent(this, RingtonePlayingService.class);
//		startService(serviceIntent);
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);
//		mainLayout.setBackgroundColor(0x0000FF00); THIS PART ACTUALLY WORKS LOL WTF
		TextView button = new TextView(this);
		long when = 0;
//		NotificationCompat.Builder mBuilder =
//		        new NotificationCompat.Builder(this)
//		        .setSmallIcon(R.drawable.notification_catnap)
//		        .setContentTitle("You have CatNap alarms pending!")
//		        .setContentText("Tap to view more details.")
//		        .setWhen(when).setUsesChronometer(true);
//		
//		
//		// Creates an explicit intent for an Activity in your app
//		Intent resultIntent = new Intent(this, AlarmControlActivity.class);
////
////		// The stack builder object will contain an artificial back stack for the
////		// started Activity.
////		// This ensures that navigating backward from the Activity leads out of
////		// your application to the Home screen.
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//		// Adds the back stack for the Intent (but not the Intent itself)
//		stackBuilder.addParentStack(AlarmControlActivity.class);
//		// Adds the Intent that starts the Activity to the top of the stack
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent =
//		        stackBuilder.getPendingIntent(
//		            0,
//		            PendingIntent.FLAG_UPDATE_CURRENT
//		        );
//		mBuilder.setContentIntent(resultPendingIntent);
//		NotificationManager mNotificationManager =
//		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////		// mId allows you to update the notification later on.
//		int mId = 1;
//		mNotificationManager.notify(mId, mBuilder.build());
//	    
//		
//		
//		RemoteViews contentView=new RemoteViews(this.getPackageName(), R.layout.notificationlayout);
//
//	    
//
//	    mBuilder.setContent(contentView);
	    
		
		button.setText("test");
		button.setTextSize(100);
	    LayoutParams params = 
	            new LinearLayout.LayoutParams(
	                    LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT);
	    
		LinearLayout mainLinear = new LinearLayout(this);
		mainLinear.setBackgroundColor(000000);
		mainLinear.setLayoutParams(params);
		mainLinear.setOrientation(LinearLayout.HORIZONTAL);
	    button.setLayoutParams(params);
	    
		mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("ca-app-pub-7330416502826862/5930895606");
//        03-26 12:05:31.507: I/Ads(16261): Use AdRequest.Builder.addTestDev	ice("25943A650A9B6C01FB89029F724F4A03") to get test ads on this device.
//        03-26 15:32:03.437: I/Ads(17764): Use AdRequest.Builder.addTestDevice("25943A650A9B6C01FB89029F724F4A03") to get test ads on this device.
//        mainLayout.setBackgroundColor(0x0000FF01);
        mainLayout.addView(mAdView);
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("25943A650A9B6C01FB89029F724F4A03")
        .build();
        
		mAdView.loadAd(adRequest);
        
	
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		Button chooseNaptime = (Button)findViewById(R.id.chooseNaptime);
		Button options = (Button)findViewById(R.id.options);
		final ImageView catBoot = (ImageView)findViewById(R.id.imageView1);

		bgAnim = (AnimationDrawable)catBoot.getBackground();
		
		options.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this, AlarmControlActivity.class);
				startActivity(i);
				
			}
		});
		
		chooseNaptime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent i = new Intent(MainActivity.this, NapActivity.class);
				 
				 startActivity(i);
				
			}
		});
		
		
		
	}


	 public void onBackPressed() {
	     super.onBackPressed();
		 Intent startMain = new Intent(Intent.ACTION_MAIN);
	        startMain.addCategory(Intent.CATEGORY_HOME);
	        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(startMain);

	}



}
