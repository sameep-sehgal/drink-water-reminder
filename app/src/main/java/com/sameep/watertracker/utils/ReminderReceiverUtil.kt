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
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sameep.watertracker.MainActivity
import com.sameep.watertracker.R
import com.sameep.watertracker.remindernotification.*
import com.sameep.watertracker.ui.theme.AppColorPrimary
import java.util.*

object ReminderReceiverUtil {
  fun shallNotify(
    reminderPeriodStart:String?,
    reminderPeriodEnd:String?,
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

    if(reminderPeriodStartTime > reminderPeriodEndTime && currTime >= reminderPeriodStartTime){
      //Case -- reminderPeriodEnd = "02:00" and reminderPeriodStart = "10:00" and currTime = "11:00"
      reminderPeriodEndTime.add(Calendar.DAY_OF_MONTH,1)
    }

    if(reminderPeriodStartTime > reminderPeriodEndTime && currTime < reminderPeriodEndTime) {
      //Case -- reminderPeriodEnd = "02:00" and reminderPeriodStart = "10:00" and currTime = "01:00"
      reminderPeriodStartTime.add(Calendar.DAY_OF_MONTH, -1)
    }

    if(currTime < reminderPeriodEndTime && currTime >= reminderPeriodStartTime)
      return true

    return false
  }

  fun setReminder(
    reminderGap:Int,
    context: Context,
    time:Calendar? = null
  ) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      5,
      intent,
      PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    var triggerAtMillis = SystemClock.elapsedRealtime() + reminderGap
    if(time!=null) {
      val currTime = Calendar.getInstance()
      val timeDiff = time.timeInMillis - currTime.timeInMillis
      triggerAtMillis = SystemClock.elapsedRealtime() + timeDiff
    }

    alarmManager.setExactAndAllowWhileIdle(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      triggerAtMillis,
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

  fun setMorningFirstAlarm(
    context: Context,
    reminderPeriodStartTime: String
  ) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val morningAlarmIntent = Intent(context, MorningAlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      6,
      morningAlarmIntent,
      PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val calendar = TimeString.getCalendarInstance(reminderPeriodStartTime)
    val currTime = Calendar.getInstance()
    if(calendar < currTime)
      calendar.add(Calendar.DAY_OF_MONTH,1)

    alarmManager.setAlarmClock(
      AlarmManager.AlarmClockInfo(
        calendar.timeInMillis,
        pendingIntent
      ),
      pendingIntent
    )
  }

  fun cancelReminder(
    context: Context
  ) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)

    val pendingIntent = PendingIntent.getBroadcast(
      context,
      5,
      intent,
      PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
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
    val pendingIntent = PendingIntent.getActivity(
      context,
      0,
      mainActivityIntent,
      PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

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
      val pendingActionIntentGlass = PendingIntent.getBroadcast(
        context,
        1,
        addWaterIntentGlass,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
      )
      builder?.addAction(R.drawable.glass_2, "${glassCapacity}${waterUnit}", pendingActionIntentGlass)
    }
    if(mugCapacity != null && mugCapacity != 0) {
      val addWaterIntentMug = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", mugCapacity)
        putExtra("container", Container.MUG)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentMug = PendingIntent.getBroadcast(
        context,
        2,
        addWaterIntentMug,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
      )
      builder?.addAction(R.drawable.mug_3, "${mugCapacity}${waterUnit}", pendingActionIntentMug)
    }
    if(bottleCapacity != null && bottleCapacity != 0) {
      val addWaterIntentBottle = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", bottleCapacity)
        putExtra("container", Container.BOTTLE)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentBottle = PendingIntent.getBroadcast(
        context,3,
        addWaterIntentBottle,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
      )
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