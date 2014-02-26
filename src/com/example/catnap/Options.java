package com.example.catnap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class Options extends Activity implements ViewFactory {
	private ImageSwitcher imgsw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		final int imgs[] = {
			R.drawable.catbed,
			R.drawable.caticon
		};
		
		Button switchViews = (Button)findViewById(R.id.switchViews);
		Button switchViewsAgain = (Button)findViewById(R.id.switchViewsAgain);
		
		imgsw = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		
		Animation slide_in_left = AnimationUtils.loadAnimation(this,
				    android.R.anim.fade_in);
		Animation slide_out_right = AnimationUtils.loadAnimation(this,
				    android.R.anim.fade_out);
//		imgsw.setInAnimation(slide_in_left);
//		imgsw.setOutAnimation(slide_out_right);

		imgsw.setFactory(this);
		imgsw.setImageResource(imgs[0]);
		switchViews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 imgsw.setImageResource(imgs[1]);
				
			}
		});
		
		switchViewsAgain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 imgsw.setImageResource(imgs[0]);
				
			}
		});
		imgsw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 System.out.println("imgswitcher clicked");
				
			}
		});
	}

		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

@Override
public View makeView() {
	ImageView iView = new ImageView(this);
//	iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	iView.setLayoutParams(new ImageSwitcher.LayoutParams
		(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	
	return iView;
}

}
