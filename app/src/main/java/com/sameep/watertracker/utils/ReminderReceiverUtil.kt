package com.sameep.watertracker.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sameep.watertracker.MainActivity
import com.sameep.watertracker.R
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.remindernotification.AddWaterReceiver
import com.sameep.watertracker.remindernotification.BootReceiver
import com.sameep.watertracker.remindernotification.NOTIFICATION_CHANNEL
import com.sameep.watertracker.ui.theme.AppColorPrimary
import com.sameep.watertracker.remindernotification.ReminderReceiver
import java.util.*

object ReminderReceiverUtil {
  fun shallNotify(
    reminderPeriodStart:String?,
    reminderPeriodEnd:String?,
    remindAfterGoalAchieved:Boolean?,
    todaysWaterRecord: DailyWaterRecord,
    currTime:Calendar = Calendar.getInstance()
  ): Boolean {
    var reminderPeriodStartTime: Calendar = Calendar.getInstance()
    var reminderPeriodEndTime: Calendar = Calendar.getInstance()
    if(reminderPeriodStart != null) {
      reminderPeriodStartTime = TimeString.getCalendarInstance(reminderPeriodStart)
    }
    if(reminderPeriodEnd != null) {
      reminderPeriodEndTime = TimeString.getCalendarInstance(reminderPeriodEnd)
    }

    if(reminderPeriodStartTime > reminderPeriodEndTime){
      //Case -- reminderPeriodEnd = "02:00" and reminderPeriodStart = "10:00"
      reminderPeriodEndTime.add(Calendar.DAY_OF_MONTH,1)
    }

    if(currTime < reminderPeriodEndTime && currTime > reminderPeriodStartTime)
      if (!(!remindAfterGoalAchieved!! && todaysWaterRecord.isGoalAchieved))
        return true

    return false
  }

  @SuppressLint("UnspecifiedImmutableFlag")
  fun setReminder(
    reminderGap:Int,
    context: Context
  ) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
      PendingIntent.getBroadcast(context, 5, intent, PendingIntent.FLAG_IMMUTABLE)
    } else {
      PendingIntent.getBroadcast(context, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    val millisecondsToFirstAlarm = Calendar.getInstance()
    millisecondsToFirstAlarm.add(Calendar.HOUR, 1)
    millisecondsToFirstAlarm.set(Calendar.MINUTE, 0)
    millisecondsToFirstAlarm.set(Calendar.SECOND, 0)
    millisecondsToFirstAlarm.set(Calendar.MILLISECOND, 0)
    val currTime = Calendar.getInstance()

    alarmManager.setInexactRepeating(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      SystemClock.elapsedRealtime() +
              (millisecondsToFirstAlarm.timeInMillis - currTime.timeInMillis), //TODO
      reminderGap.toLong(),
      pendingIntent
    )
    Log.d("TAG", "onReceive: reminder set")

    /* Restart if rebooted */
    val receiver = ComponentName(context, BootReceiver::class.java)
    context.packageManager.setComponentEnabledSetting(
      receiver,
      PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
      PackageManager.DONT_KILL_APP
    )
  }

  @SuppressLint("UnspecifiedImmutableFlag")
  fun cancelReminder(
    context: Context
  ) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)

    val pendingIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
      PendingIntent.getBroadcast(context, 5, intent, PendingIntent.FLAG_MUTABLE)
    } else {
      PendingIntent.getBroadcast(context, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    Log.d("TAG", "onReceive: cancelReminder: ${checkAlarm(context)}")

    alarmManager.cancel(pendingIntent)
    Log.d("TAG", "onReceive: cancelReminder: Reminder Cancelled")

    /* Alarm won't start again if device is rebooted */
    val receiver = ComponentName(context, BootReceiver::class.java)
    val pm = context.packageManager
    pm.setComponentEnabledSetting(
      receiver,
      PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
      PackageManager.DONT_KILL_APP
    )
    Log.i("AlarmHelper", "Cancelling alarms")
  }

  @SuppressLint("UnspecifiedImmutableFlag")
  fun buildBasicNotification(
    context: Context?,
    glassCapacity: Int?,
    mugCapacity: Int?,
    bottleCapacity: Int?,
    waterUnit:String?,
    dailyWaterGoal: Int?
  ): NotificationCompat.Builder? {
    val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    //Request codes are used to uniquely identify intents. They must not be the same for different intents
    val pendingIntent = PendingIntent.getActivity(context,0,mainActivityIntent,PendingIntent.FLAG_UPDATE_CURRENT)

    //Build notification only when we need to show.
    val builder = context?.let {
      NotificationCompat.Builder(it, NOTIFICATION_CHANNEL)
        .setSound(
          Uri.parse("android.resource://"
                  + context.packageName + "/" + R.raw.water))
        .setSmallIcon(R.drawable.water_drop_icon)
        .setContentTitle("It's Time To Drink Water!")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setDefaults(Notification.DEFAULT_VIBRATE)
        .setContentIntent(pendingIntent)
        .setColor(AppColorPrimary.hashCode())
        .setFullScreenIntent(pendingIntent, true)
    }

    //Add Action Buttons
    if(glassCapacity != null && glassCapacity != 0) {
      val addWaterIntentGlass = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", glassCapacity)
        putExtra("container", Container.GLASS)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentGlass = PendingIntent.getBroadcast(context,1, addWaterIntentGlass, PendingIntent.FLAG_UPDATE_CURRENT)
      builder?.addAction(R.drawable.glass_2, "${glassCapacity}${waterUnit}", pendingActionIntentGlass)
    }
    if(mugCapacity != null && mugCapacity != 0) {
      val addWaterIntentMug = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", mugCapacity)
        putExtra("container", Container.MUG)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentMug = PendingIntent.getBroadcast(context,2, addWaterIntentMug, PendingIntent.FLAG_UPDATE_CURRENT)
      builder?.addAction(R.drawable.mug_3, "${mugCapacity}${waterUnit}", pendingActionIntentMug)
    }
    if(bottleCapacity != null && bottleCapacity != 0) {
      val addWaterIntentBottle = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", bottleCapacity)
        putExtra("container", Container.BOTTLE)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentBottle = PendingIntent.getBroadcast(context,3, addWaterIntentBottle, PendingIntent.FLAG_UPDATE_CURRENT)
      builder?.addAction(R.drawable.bottle_4, "${bottleCapacity}${waterUnit}", pendingActionIntentBottle)
    }

    return builder
  }

  @SuppressLint("UnspecifiedImmutableFlag")
  fun checkAlarm(context: Context): Boolean {

    val alarmIntent = Intent(context, ReminderReceiver::class.java)

    return PendingIntent.getBroadcast(
      context, 5,
      alarmIntent,
      PendingIntent.FLAG_NO_CREATE
    ) != null
  }
}