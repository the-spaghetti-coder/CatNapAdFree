package com.cedideas.catnap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

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
		
//		Intent serviceIntent = new Intent(this, RingtonePlayingService.class);
//		startService(serviceIntent);
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);
//		mainLayout.setBackgroundColor(0x0000FF00); THIS PART ACTUALLY WORKS LOL WTF
		TextView button = new TextView(this);
		long when = 0;
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.notification_catnap)
		        .setContentTitle("You have CatNap alarms pending!")
		        .setContentText("Tap to view more details.")
		        .setWhen(when).setUsesChronometer(true);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, AlarmControlActivity.class);
//
//		// The stack builder object will contain an artificial back stack for the
//		// started Activity.
//		// This ensures that navigating backward from the Activity leads out of
//		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(AlarmControlActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		// mId allows you to update the notification later on.
		int mId = 1;
		mNotificationManager.notify(mId, mBuilder.build());
	    
		
		
		RemoteViews contentView=new RemoteViews(this.getPackageName(), R.layout.notificationlayout);

	    

	    mBuilder.setContent(contentView);
	    
		
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

	protected void onRestart() {
		super.onRestart();
		System.out.println("activity restarted");
	}
	
	protected void onResume() {
		super.onResume();
		System.out.println("activity RESUMED");
		Intent intent;

	}
	
	protected void onStart() {
		super.onStart();
		System.out.println("activity STARTED");
	}
	
	public void onWindowFocusChanged() { 
		bgAnim.start();
	}


	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */

}
