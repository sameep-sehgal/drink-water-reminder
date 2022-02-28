package com.sameep.watertracker.ui.screens.remindertab.components

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.remindernotification.NOTIFICATION_CHANNEL
import com.sameep.watertracker.ui.screens.remindertab.components.dialogs.ReminderNotWorkingDialog
import com.sameep.watertracker.utils.ReminderGap
import com.sameep.watertracker.utils.ReminderReceiverUtil
import com.sameep.watertracker.ui.screens.settingstab.components.*

@Composable
fun ReminderSettings(
  reminderPeriodStart:String,
  reminderPeriodEnd:String,
  reminderGap:Int,
  reminderAfterGoalAchieved:Boolean,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  isReminderOn:Boolean
){
  val context = LocalContext.current
  val (showReminderNotWorkingDialog, setShowReminderNotWorkingDialog) =  remember { mutableStateOf(false) }

  Column {
    SettingsRowSelectValue(
      text = com.sameep.watertracker.utils.Settings.REMINDER_PERIOD,
      value = "$reminderPeriodStart-$reminderPeriodEnd",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(com.sameep.watertracker.utils.Settings.REMINDER_PERIOD)
      },
      enabled = isReminderOn
    )
    SettingsRowSelectValue(
      text = com.sameep.watertracker.utils.Settings.REMINDER_FREQUENCY,
      value = "${ReminderGap.GAP_OPTION_TEXT_MAPPER[reminderGap]}",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(com.sameep.watertracker.utils.Settings.REMINDER_FREQUENCY)
      },
      enabled = isReminderOn
    )
    SettingsRowBooleanValue(
      text = com.sameep.watertracker.utils.Settings.REMINDER_AFTER_GOAL_ACHIEVED,
      value = reminderAfterGoalAchieved,
      onCheckedChange = {
        preferenceDataStoreViewModel.setReminderAfterGoalAchieved(it)
      },
      enabled = isReminderOn
    )

    SettingsSubheading(text = "Notifications")

    SettingsRowNoValueWithSubtitle(
      text = "Notification System Settings",
      subtitle = "Set sound, vibration, and priority in system settings",
      onSettingsRowClick = {
        var intent = Intent()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            .putExtra(Settings.EXTRA_CHANNEL_ID, NOTIFICATION_CHANNEL)
        }else{
          intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

          intent.putExtra("app_package", context.packageName)
          intent.putExtra("app_uid", context.applicationInfo.uid)
        }
        context.startActivity(intent)
      },
      enabled = isReminderOn
    )
  }

  SettingsRowNoValue(
    text = "Reminder not Working?",
    onSettingsRowClick = {
      ReminderReceiverUtil.setReminder(reminderGap, context)
      setShowReminderNotWorkingDialog(true)
    },
    enabled = isReminderOn
  )

  if(showReminderNotWorkingDialog) {
    ReminderNotWorkingDialog(
      setShowDialog = setShowReminderNotWorkingDialog,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      reminderGap = reminderGap,
      context = context,
      reminderPeriodStart = reminderPeriodStart
    )
  }
}