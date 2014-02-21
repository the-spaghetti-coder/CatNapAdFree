package com.example.catnap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class Options extends Activity {
	private ImageSwitcher imgsw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		 imgsw = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
//		imgsw.setImageResource(R.id.happyCat);
		imgsw.setFactory(new ViewFactory() {
			
			@Override
			public View makeView() {
				ImageView happyCat = (ImageView)findViewById(R.id.happyCat);
				happyCat.setScaleType(ImageView.ScaleType.FIT_CENTER);
				happyCat.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				
				return happyCat;
			}
		});
		
	}
	
//	public void next(View view){
//	      Toast.makeText(getApplicationContext(), "Next Image", 
//	      Toast.LENGTH_LONG).show();
//	      Animation in = AnimationUtils.loadAnimation(this,
//	      android.R.anim.slide_in_left);
//	      Animation out = AnimationUtils.loadAnimation(this,
//	      android.R.anim.slide_out_right);
//	      imgsw.setInAnimation(in);
//	      imgsw.setOutAnimation(out);
//	      imgsw.setImageResource(R.drawable.ic_launcher);
//	   }
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

}
