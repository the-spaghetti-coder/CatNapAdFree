package com.example.catnap;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;



public class MyDragShadowBuilder extends View.DragShadowBuilder{
	ImageView shadow;
	
		public MyDragShadowBuilder(View v) {
			super(v);
			shadow = (ImageView) v;
			
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

