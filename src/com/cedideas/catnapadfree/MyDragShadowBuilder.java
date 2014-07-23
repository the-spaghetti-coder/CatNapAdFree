package com.cedideas.catnapadfree;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageSwitcher;



public class MyDragShadowBuilder extends View.DragShadowBuilder{
	ImageSwitcher shadow;
	
		public MyDragShadowBuilder(View v) {
			super(v);
			shadow = (ImageSwitcher) v;
			
		}
		
		public void onProvideShadowMetrics (Point size, Point touch) {
        // Defines local variables
			int width, height;
			width = getView().getWidth() ;
			height = getView().getHeight() ;
			
			size.set(width, height);
			touch.set(width /2 , height/2 );
		}	

		public void onDrawShadow(Canvas canvas) {
			shadow.draw(canvas);
		}
		
}

