package com.example.timewise14;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.timewise14.R;
import com.example.timewise14.R;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "timewise_channel";
    public static final String CHANNEL_NAME = "TimeWise Reminders";
    public static final String CHANNEL_DESCRIPTION = "Notifications for TimeWise reminders";

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.projek14.ACTION_REMINDER".equals(intent.getAction())) {
            String reminderTitle = intent.getStringExtra("reminder_title");
            int reminderId = intent.getIntExtra("reminder_id", 0);

            int notificationIcon = R.mipmap.ic_notification;

            createNotificationChannel(context);

            Intent tapIntent = new Intent(context, com.example.timewise14.MainActivity.class);
            tapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingTapIntent = PendingIntent.getActivity(context, reminderId, tapIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Pengingat: " + reminderTitle)
                    .setContentText("Waktunya untuk: " + reminderTitle)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingTapIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(reminderId, builder.build());

            Toast.makeText(context, "Pengingat: " + reminderTitle, Toast.LENGTH_LONG).show();
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public static void cancelReminder(Context context, int reminderId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.setAction("com.example.projek14.ACTION_REMINDER");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminderId, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Toast.makeText(context, "Alarm dibatalkan!", Toast.LENGTH_SHORT).show();
        }
    }
}