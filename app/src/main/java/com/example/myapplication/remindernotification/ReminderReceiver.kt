package com.example.myapplication.remindernotification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.accompanist.pager.ExperimentalPagerApi

//Use broadcast Receiver since app wont run when we have to show notifications
//It will automatically show notification at desired time without running the app
class ReminderReceiver: BroadcastReceiver() {

  @ExperimentalPagerApi
  override fun onReceive(context: Context?, intent: Intent?) {
    //Build a notification here
    val i = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(context,0,i,0)

    //Unique Id for this notification

    val builder = context?.let {
      NotificationCompat.Builder(it, CHANNEL_ID)
        .setSmallIcon(R.drawable.home_icon_glass_white)
        .setContentTitle("Time To Drink Water!!")
        .setContentText("Drink water Now")
        .setAutoCancel(true)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
    }

    if (builder != null) {
      with(context.let { NotificationManagerCompat.from(it) }) {
            // notificationId is a unique int for each notification that you must define
            this.notify(TEST_NOTIFICATION_ID, builder.build())
          }
    }
  }
}