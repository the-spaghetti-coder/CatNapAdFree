package com.cedideas.catnapadfree;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

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
//private AdView mAdView;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PreferenceManager.setDefaultValues(this, R.xml.preference, false);
		
		DBHelper db = new DBHelper(this);
		List<String> checkAlarmAmount = db.getActiveAlarmList();
		if (checkAlarmAmount.isEmpty()) {
			NotificationManager aNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			aNotificationManager.cancel(1);
		}
		///////////////////////////TEST ENVIRONMENT/////////////////////////////

		SharedPreferences prefs = this.getSharedPreferences(
			      "com.cedideas.catnap", Context.MODE_PRIVATE);
//		String key = "key";
//		prefs.edit().putBoolean(key, true).apply();
		String alarmPreference = "alarmChoice";
		prefs.edit().putString(alarmPreference, "Meowing").apply();
		String alarmStoredPreference = prefs.getString(alarmPreference, null);
		System.out.println("First preference read: " + alarmStoredPreference);
		prefs.edit().putString(alarmPreference, "Normal").commit();
		String alarmStoredPreferenceTwo = prefs.getString(alarmPreference, null);
		prefs.edit().apply();
		System.out.println("Second preference read:" + alarmStoredPreferenceTwo);
		
//		boolean doesItContain = prefs.contains("key");
//		if (doesItContain) {
//			System.out.println("prefs CONTAINS IT! :)");
//			}
//		else {
//			System.out.println("prefs DOESN'T CONTAIN IT! :((((");
//		}
		
//		  final TimePicker timePicker = new TimePicker(this);
//		    timePicker.setIs24HourView(false);
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
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);
	    LayoutParams params = 
	            new LinearLayout.LayoutParams(
	                    LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT);
		LinearLayout mainLinear = new LinearLayout(this);
		mainLinear.setBackgroundColor(000000);
		mainLinear.setLayoutParams(params);
		mainLinear.setOrientation(LinearLayout.HORIZONTAL);
	    
//		mAdView = new AdView(this);
//        mAdView.setAdSize(AdSize.SMART_BANNER);
//        mAdView.setAdUnitId("ca-app-pub-7330416502826862/5930895606");
////        03-26 12:05:31.507: I/Ads(16261): Use AdRequest.Builder.addTestDev	ice("25943A650A9B6C01FB89029F724F4A03") to get test ads on this device.
////        03-26 15:32:03.437: I/Ads(17764): Use AdRequest.Builder.addTestDevice("25943A650A9B6C01FB89029F724F4A03") to get test ads on this device.
//        mainLayout.addView(mAdView);
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("25943A650A9B6C01FB89029F724F4A03")
        .build();
        
//		mAdView.loadAd(adRequest);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
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
