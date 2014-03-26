package com.example.catnap;

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

import com.example.catnap.util.SystemUiHider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import 	com.google.android.gms.ads.AdView;

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

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("myAdUnitId");
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
        .build();
        
		mAdView.loadAd(adRequest);
        
		setContentView(R.layout.activity_main);
		
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

	public void onWindowFocusChanged() { 
		bgAnim.start();
	}


	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */

}
