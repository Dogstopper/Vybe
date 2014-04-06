package bitcamp.vybe;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by stephen on 4/5/14.
 */
public class VibrateStartWakefulService extends IntentService {
    private NotificationManager mNotificationManager;

    public VibrateStartWakefulService() {
        super("Vibrate Start Wakeful Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            Log.d("SERVICE", "EXTRAS NOT EMPTY");
            for (String s: extras.keySet()) {
                Log.d("SERVICE EXTRA", "EXTRA: (" + s + ", " + extras.get(s) + ")");
            }

            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            String instruction = extras.getString("instruction");
            String phoneNum = extras.getString("sourceNumber");

            String name = getContactNameFromNumber(phoneNum);
            // Make a notification
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(name + " vybed you!")
                            .setContentText("Vybe back!");
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addParentStack(MainActivity.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // mId allows you to update the notification later on.
            mNotificationManager.notify(1234566789, mBuilder.build());


            if (instruction.toLowerCase().equalsIgnoreCase("start")) {
                v.vibrate(7000);
            } else if (instruction.toLowerCase().equalsIgnoreCase("stop")) {
                v.cancel();
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        VibrateStartWakefulReciever.completeWakefulIntent(intent);
    }

    private String getContactNameFromNumber(String number) {
        ContentResolver cr = getContentResolver();

        String [] projection = new String []{
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        };
        String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ? ";;
        String[] selectionArgs = new String[]{"%"+number+ "%"};

        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        //proceed as you need
        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            Log.d("SERVICE", cursor.toString());
            return name;
        }

        // return the original number if no match was found
        return number;
    }
}
