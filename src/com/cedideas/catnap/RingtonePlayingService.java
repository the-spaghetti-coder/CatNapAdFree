package com.cedideas.catnap;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

public class RingtonePlayingService extends Service
{
    private Ringtone ringtone;
    private Vibrator vibe;
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
    	Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    	vibe = (Vibrator)getSystemService(this.VIBRATOR_SERVICE);
        this.ringtone = RingtoneManager.getRingtone(this, alarm);
        ringtone.play();
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
        ringtone.stop();
        vibe.cancel();
    }
}