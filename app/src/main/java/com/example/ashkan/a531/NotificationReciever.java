package com.example.ashkan.a531;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.ashkan.a531.Activity.MainScreen;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.ashkan.a531.AlarmClock.FRIDAY_TOGGLE_BUTTON_KEY;
import static com.example.ashkan.a531.AlarmClock.HOUR_KEY;
import static com.example.ashkan.a531.AlarmClock.MINUTE_KEY;
import static com.example.ashkan.a531.AlarmClock.MONDAY_TOGGLE_BUTTON_KEY;
import static com.example.ashkan.a531.AlarmClock.SATURDAY_TOGGLE_BUTTON_KEY;
import static com.example.ashkan.a531.AlarmClock.SUNDAY_TOGGLE_BUTTON_KEY;
import static com.example.ashkan.a531.AlarmClock.SWITCH_KEY;
import static com.example.ashkan.a531.AlarmClock.THURSDAY_TOGGLE_BUTTON_KEY;
import static com.example.ashkan.a531.AlarmClock.TUESDAY_TOGGLE_BUTTON_KEY;
import static com.example.ashkan.a531.AlarmClock.WEDNESDAY_TOGGLE_BUTTON_KEY;

public class NotificationReciever extends BroadcastReceiver {

    private static final String ALARM_CLOCK_TIME_KEY = "alarmClockTimeSet";
    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_menu_send)
                        .setContentTitle("5/3/1 Nsuns")
                        .setContentText("Let's Do It!")
                        .setAutoCancel(true);


       // mMediaPlayer = MediaPlayer.create(context, R.raw.morning_alarm);
       // mMediaPlayer.start();


        Bundle extras = intent.getExtras();
        String test = extras.getString("String");
        int hour = extras.getInt(HOUR_KEY);
        int minute = extras.getInt(MINUTE_KEY);
        boolean switchOn = extras.getBoolean(SWITCH_KEY);
        boolean mondaySelected =  extras.getBoolean(MONDAY_TOGGLE_BUTTON_KEY);
        boolean tuesdaySelected =  extras.getBoolean(TUESDAY_TOGGLE_BUTTON_KEY);
        boolean wednesdaySelected =  extras.getBoolean(WEDNESDAY_TOGGLE_BUTTON_KEY);
        boolean thursdaySelected =  extras.getBoolean(THURSDAY_TOGGLE_BUTTON_KEY);
        boolean fridaySelected =  extras.getBoolean(FRIDAY_TOGGLE_BUTTON_KEY);
        boolean saturdaySelected =  extras.getBoolean(SATURDAY_TOGGLE_BUTTON_KEY);
        boolean sundaySelected =  extras.getBoolean(SUNDAY_TOGGLE_BUTTON_KEY);


        Intent resultIntent = new Intent(context, MainScreen.class);

        resultIntent.putExtra("String","does this make it");
        resultIntent.putExtra(HOUR_KEY,hour);
        resultIntent.putExtra(MINUTE_KEY,minute);
        // intent.putExtra(ALARM_CLOCK_TIME_KEY,mTime);
        resultIntent.putExtra(SWITCH_KEY,switchOn);
        resultIntent.putExtra(MONDAY_TOGGLE_BUTTON_KEY,mondaySelected);
        resultIntent.putExtra(TUESDAY_TOGGLE_BUTTON_KEY,tuesdaySelected);
        resultIntent.putExtra(WEDNESDAY_TOGGLE_BUTTON_KEY,wednesdaySelected);
        resultIntent.putExtra(THURSDAY_TOGGLE_BUTTON_KEY,thursdaySelected);
        resultIntent.putExtra(FRIDAY_TOGGLE_BUTTON_KEY,fridaySelected);
        resultIntent.putExtra(SATURDAY_TOGGLE_BUTTON_KEY,saturdaySelected);
        resultIntent.putExtra(SUNDAY_TOGGLE_BUTTON_KEY,sundaySelected);



        //resultIntent.putExtra("EXTRAS",intent.getExtras());
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.



        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
