package com.sameep.watertracker.remindernotification

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sameep.watertracker.MainActivity
import com.sameep.watertracker.R
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.ui.theme.AppColorPrimary
import com.sameep.watertracker.utils.ReminderReceiverUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RemindUserToTurnReminderOnReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    var isReminderOn: Boolean? = null

    GlobalScope.launch {
      context?.dataStore?.data?.first {
        isReminderOn = it[PreferenceDataStore.PreferencesKeys.IS_REMINDER_ON]
        true
      }

      if(isReminderOn == false) {
        if(context != null) {
          Log.d("TAG", "onReceive: isReminderOn == false")
          ReminderReceiverUtil.setRemindUserToTurnReminderOn(context)
          val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          }
          val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
          )
          Log.d("TAG", "onReceive: pending intent set")
          val turnReminderOnIntent = Intent(context, TurnReminderOnReceiver::class.java)
          val pendingTurnReminderOnIntent = PendingIntent.getBroadcast(
            context,
            8,
            turnReminderOnIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
          )
          val builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
              .setSound(
                Uri.parse(
                  "android.resource://"
                          + context.packageName + "/" + R.raw.water
                )
              )
              .setSmallIcon(R.drawable.water_drop_icon)
              .setContentTitle("Turn Reminder back On to Receive Reminders")
              .setContentText("We are not able to remind you\uD83E\uDD7A since reminder is turned off")
              .setAutoCancel(true)
              .setPriority(NotificationCompat.PRIORITY_MAX)
              .setDefaults(Notification.DEFAULT_VIBRATE)
              .setContentIntent(pendingIntent)
              .setColor(AppColorPrimary.hashCode())
              .setFullScreenIntent(pendingIntent, true)
              .addAction(R.drawable.ic_bell_svgrepo_com, "Turn On",pendingTurnReminderOnIntent)
          Log.d("TAG", "onReceive: Notification built")
          with(context.let { NotificationManagerCompat.from(it) }) {
            this.notify(NOTIFICATION_ID, builder.build())
          }
          Log.d("TAG", "onReceive: Notified")
        }
      }
    }
  }
}