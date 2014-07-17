package com.cedideas.catnap;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

public class RingtonePlayingService extends Service
{
    private Ringtone ringtone;
    private Vibrator vibe;
    MediaPlayer mp;
	AudioManager mAudioManager;
	
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		
		
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
		int i = audioManager.getRingerMode();
		System.out.println(i);
		switch( audioManager.getRingerMode() ){
		case AudioManager.RINGER_MODE_NORMAL:
			System.out.println("ringer is normal");
			break;
		case AudioManager.RINGER_MODE_SILENT:
			System.out.println("ringer is silent");
			break;
		case AudioManager.RINGER_MODE_VIBRATE:
			System.out.println("ringer is vibrate");
			break;
		}
		
		audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		

		mp = MediaPlayer.create(getApplicationContext(), R.raw.firstdraft);

		mp.setLooping(true);
		mp.start();
    	
//		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        this.ringtone = RingtoneManager.getRingtone(this, alarm);
//        ringtone.play();
    	
    	
    	vibe = (Vibrator)getSystemService(this.VIBRATOR_SERVICE);
    	
		boolean hasVibe = vibe.hasVibrator();
		long[] vibePattern = {0, 100, 500, 250, 250};
		if (hasVibe) {
			vibe.vibrate(vibePattern, 3);
		}
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy()
    {
//        ringtone.stop();
    	mp.stop();
        vibe.cancel();
    }
}