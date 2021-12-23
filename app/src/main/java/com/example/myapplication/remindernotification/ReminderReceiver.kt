package com.example.myapplication.remindernotification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.utils.ReminderGap
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

//Use broadcast Receiver since app wont run when we have to show notifications
//It will automatically show notification at desired time without running the app
@AndroidEntryPoint
class ReminderReceiver: BroadcastReceiver() {

  private val TAG = ReminderReceiver::class.java.simpleName

  @ExperimentalPagerApi
  override fun onReceive(context: Context?, intent: Intent?) {

    val reminderPeriodStart = intent?.getStringExtra("reminder_period_start")
    val reminderPeriodEnd = intent?.getStringExtra("reminder_period_end")
    val reminderGap = intent?.getIntExtra("reminder_gap",ReminderGap.NOT_SET)

    val i = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT)

    //Build a notification here
    val builder = context?.let {
      NotificationCompat.Builder(it, CHANNEL_ID)
        .setSound(
          Uri.parse("android.resource://"
                  + context.packageName + "/" + R.raw.water_drop))
        .setSmallIcon(R.drawable.home_icon_glass_white)
        .setContentTitle("Time To Drink Water!!")
        .setContentText("Drink Now> $reminderPeriodStart -- $reminderPeriodEnd")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
    }

    val reminderPeriodEndHour = reminderPeriodEnd?.split(':')?.get(0)?.toInt()
    val reminderPeriodEndMinute = reminderPeriodEnd?.split(':')?.get(1)?.toInt()
    val reminderPeriodStartHour = reminderPeriodStart?.split(':')?.get(0)?.toInt()
    val reminderPeriodStartMinute = reminderPeriodStart?.split(':')?.get(1)?.toInt()

    val reminderPeriodEndTime = Calendar.getInstance()
    if (reminderPeriodEndHour != null) {
      reminderPeriodEndTime.set(Calendar.HOUR_OF_DAY,reminderPeriodEndHour)
    }
    if (reminderPeriodEndMinute != null) {
      reminderPeriodEndTime.set(Calendar.MINUTE,reminderPeriodEndMinute)
    }
    reminderPeriodEndTime.set(Calendar.SECOND,0)
    reminderPeriodEndTime.set(Calendar.MILLISECOND,0)

    val reminderPeriodStartTime = Calendar.getInstance()
    if (reminderPeriodStartHour != null) {
      reminderPeriodStartTime.set(Calendar.HOUR_OF_DAY,reminderPeriodStartHour)
    }
    if (reminderPeriodStartMinute != null) {
      reminderPeriodStartTime.set(Calendar.MINUTE,reminderPeriodStartMinute)
    }
    reminderPeriodStartTime.set(Calendar.SECOND,0)
    reminderPeriodStartTime.set(Calendar.MILLISECOND,0)

    if(reminderPeriodStartTime > reminderPeriodEndTime){
      //Case -- reminderPeriodEnd = "02:00" and reminderPeriodStart = "10:00"
      reminderPeriodEndTime.add(Calendar.DATE,1)
    }

    val currTime = Calendar.getInstance()

    if(currTime < reminderPeriodEndTime){
      if (builder != null) {
        with(context.let { NotificationManagerCompat.from(it) }) {
          // notificationId is a unique int for each notification that you must define
          this.notify(TEST_NOTIFICATION_ID, builder.build())
        }
      }
    }else{
      //Set Next Day Repeating Reminder
      reminderPeriodStartTime.add(Calendar.DATE,1)
      if (reminderPeriodStart != null) {
        if (reminderPeriodEnd != null) {
          if (reminderGap != null) {
            if (context != null) {
              setReminder(
                reminderPeriodStartTime.timeInMillis,
                reminderPeriodStart,
                reminderPeriodEnd,
                reminderGap,
                context
              )
            }
          }
        }
      }
    }
  }

  companion object {
    fun setReminder(
      time:Long,
      reminderPeriodStart:String,
      reminderPeriodEnd:String,
      reminderGap:Int,
      context: Context
    ) {
      if(reminderGap == ReminderGap.DONT_REMIND) {
        cancelReminder(context)
        return
      }
      val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intent = Intent(context, ReminderReceiver::class.java)
      //pass data to broadcast receiver using intent extras
      //As broadcast receiver only survives for a short time reading app data from datastore is not feasable
      //Could have used workmanager but that does not guarantee task completion at exact time
      intent.putExtra("reminder_period_start",reminderPeriodStart)
      intent.putExtra("reminder_period_end",reminderPeriodEnd)
      intent.putExtra("reminder_gap",reminderGap)
      val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT)
      alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        time,
        reminderGap.toLong(),
        pendingIntent
      )
    }

    fun cancelReminder(
      context: Context
    ) {
      val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intent = Intent(context, ReminderReceiver::class.java)
      val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT)
      alarmManager.cancel(pendingIntent)
      Log.d("TAG", "cancelReminder: Reminder Cancelled")
    }
  }
}