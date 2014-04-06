package bitcamp.vybe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by stephen on 4/5/14.
 */
public class VibrateStartWakefulReciever extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        Log.i("SERVICE", "Received: " + extras.toString());

        // This is the Intent to deliver to our service.
        Intent service = new Intent(context, VibrateStartWakefulService.class);
        service.putExtras(extras);

        // Start the service, keeping the device awake while it is launching.
        Log.i("SimpleWakefulReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
        startWakefulService(context, service);
    }
}

