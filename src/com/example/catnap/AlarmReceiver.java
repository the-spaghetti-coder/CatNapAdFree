package com.example.catnap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent arg1) {
		
		System.out.println("receiver works");
//		Toast.makeText(context, "Alarm triggered", Toast.LENGTH_LONG).show();
		
//		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		Ringtone r = RingtoneManager.getRingtone(context, alarm);
//		r.play();
		
		Intent i = new Intent(context, AlarmDialog.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
//		try {
//			   Uri alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//			 final  MediaPlayer mMediaPlayer = new MediaPlayer();
//			  mMediaPlayer.setDataSource(arg0, alert);
//			  final AudioManager audioManager = (AudioManager) arg0.getSystemService(Context.AUDIO_SERVICE);
//			 if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
//			 mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
//			 mMediaPlayer.setLooping(true);
//			 mMediaPlayer.prepare();
//			 mMediaPlayer.start();
//			}
//			} catch(Exception e) {
//			}   
		

//		Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
//		openNewAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, 2);
//        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, 2);
//        openNewAlarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        arg0.startActivity(openNewAlarm);
	}

	public void stopAlarm() {
		
	}
	
}
