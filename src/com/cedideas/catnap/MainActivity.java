package com.cedideas.catnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
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

import com.cedideas.catnap.R;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);
//		mainLayout.setBackgroundColor(0x0000FF00); THIS PART ACTUALLY WORKS LOL WTF
		TextView button = new TextView(this);
		
		

		
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
        mAdView.setAdUnitId(getResources().getString(R.string.adunitid));
//        03-26 12:05:31.507: I/Ads(16261): Use AdRequest.Builder.addTestDevice("25943A650A9B6C01FB89029F724F4A03") to get test ads on this device.
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
				Intent i = new Intent(MainActivity.this, Options.class);
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
//		if ((intent=getIntent())!=null){
//			TextView alarmMessage = (TextView)findViewById(R.id.alarmAmountNotification);
//			String alarmMessageFromIntent = intent.getExtras().getString("customAlarmSet");
//			alarmMessage.setText(alarmMessageFromIntent);
//			
//		} else {
//			System.out.println("no intent yet");
//		}
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
