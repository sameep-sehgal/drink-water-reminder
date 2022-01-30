package com.example.myapplication.ui.screens.remindertab.components

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.remindernotification.CHANNEL_ID_4
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowBooleanValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowNoValueWithSubtitle
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowSelectValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsSubheading
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.ReminderGap
import com.example.myapplication.utils.Settings
import com.example.myapplication.utils.TimeString

@Composable
fun ReminderSettings(
  reminderPeriodStart:String,
  reminderPeriodEnd:String,
  reminderGap:Int,
  reminderSound:String,
  reminderAfterGoalAchieved:Boolean,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
){
  val context = LocalContext.current
  val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
  val nextAlarmTime = alarmManager.nextAlarmClock.triggerTime
  Column {
    SettingsRowSelectValue(
      text = Settings.REMINDER_PERIOD,
      value = "$reminderPeriodStart-$reminderPeriodEnd",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.REMINDER_PERIOD)
      }
    )
    SettingsRowSelectValue(
      text = Settings.REMINDER_FREQUENCY,
      value = "${ReminderGap.GAP_OPTION_TEXT_MAPPER[reminderGap]}",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.REMINDER_FREQUENCY)
      }
    )
    SettingsRowBooleanValue(
      text = Settings.REMINDER_AFTER_GOAL_ACHEIVED,
      value = reminderAfterGoalAchieved,
      onCheckedChange = {
        preferenceDataStoreViewModel.setReminderAfterGoalAchieved(it)
      }
    )
    SettingsRowSelectValue(
      text = "Next Reminder Time",
      value = TimeString.longToString(nextAlarmTime),
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.REMINDER_SOUND)
      }
    )

    SettingsSubheading(text = "Notifications")

    SettingsRowNoValueWithSubtitle(
      text = "Notification System Settings",
      subtitle = "Set sound, vibration, and priority in system settings",
      onSettingsRowClick = {
        var intent = Intent()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          intent = Intent(android.provider.Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            .putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, context.packageName)
            .putExtra(android.provider.Settings.EXTRA_CHANNEL_ID, CHANNEL_ID_4)
        }else{
          intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

          intent.putExtra("app_package", context.packageName)
          intent.putExtra("app_uid", context.applicationInfo.uid)
        }
        context.startActivity(intent)
      }
    )
  }

  SettingsRowNoValueWithSubtitle(
    text = "Notification not Working?",
    subtitle = "Click here and we will reset the settings",
    onSettingsRowClick = {}
  )
}