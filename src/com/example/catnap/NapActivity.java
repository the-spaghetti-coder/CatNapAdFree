package com.example.catnap;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

public class NapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
		ImageView sleepyCat = (ImageView)findViewById(R.id.sleepyCat);
		sleepyCat.setTag("icon bitmap");
		sleepyCat.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				System.out.println("Long click works!");
//				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag()); 
//				ClipData dragData = new ClipData(v.getTag(),ClipData.MIMETYPE_TEXT_PLAIN,item);
//				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(sleepyCat);
				return false;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nap, menu);
		return true;
	}

}
