package com.example.catnap;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmDialog extends Activity{

	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_alarmdialog);
		Button stopRingtone = (Button)findViewById(R.id.stopRingtone);
		
		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		final Ringtone r = RingtoneManager.getRingtone(this, alarm);
		r.play();
		
		stopRingtone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 r.stop();
				 Intent backToCats = new Intent(AlarmDialog.this, NapActivity.class);
				 startActivity(backToCats);
				
			}
		});
//		displayAlert();
		
	}
	
//	public void displayAlert()
//    {
//		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		final Ringtone r = RingtoneManager.getRingtone(this, alarm);
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to exit?").setCancelable(
//                false).setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    	r.play();
//                    	
//                    }
//                }).setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    	r.stop();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}