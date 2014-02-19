package com.example.catnap;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class NapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nap);
		
		final ImageView sleepyCat = (ImageView)findViewById(R.id.sleepyCat);
		final ImageView droid = (ImageView)findViewById(R.id.testArea);
		
		sleepyCat.setTag("icon bitmap");
		sleepyCat.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				System.out.println("Long click works!");
				String[] mimetypes = new String[]{"MIMETYPE_TEXT_PLAIN"};
				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag()); 
				ClipData dragData = new ClipData((CharSequence) v.getTag(), mimetypes, item);
				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(sleepyCat);
				Toast.makeText(NapActivity.this, "Drag started!", Toast.LENGTH_LONG).show();
//				sleepyCat.setVisibility(4);  MAKES INVISIBLE
				return v.startDrag(dragData, myShadow, null, 0);
				
			}
		});
		
		droid.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View arg0, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_ENDED:
					Toast.makeText(NapActivity.this, "You dropped it! Woo!", Toast.LENGTH_SHORT).show();
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					Toast.makeText(NapActivity.this, "Entered the bounding zone", Toast.LENGTH_SHORT).show();
					break;
				default:
					System.out.println("fail");
					break;
				}
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
