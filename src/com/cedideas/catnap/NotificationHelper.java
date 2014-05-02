package com.cedideas.catnap;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class NotificationHelper {
	
	public NotificationHelper(Context context) {
		
	}

	public NotificationCompat.Builder createNotification(Context context){
		long when = 0;
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.notification_catnap)
		        .setContentTitle("You have CatNap alarms pending!")
		        .setContentText("Tap to view more details.")
		        .setWhen(when).setUsesChronometer(true);
		
		Intent resultIntent = new Intent(context, AlarmControlActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(AlarmControlActivity.class);
		
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		return mBuilder.setContentIntent(resultPendingIntent);
		
	}

}
