package com.example.myapplication.ui.screens.remindertab

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.remindertab.components.ReminderSettings
import com.example.myapplication.ui.screens.remindertab.components.TopReminderTabBar
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.SetReminderFrequencySettingDialog
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.SetReminderPeriodSettingDialog
import com.example.myapplication.utils.*

@Composable
fun ReminderTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  val context = LocalContext.current
  val columnAlpha by remember {
    mutableStateOf(Animatable(0f))
  }
  val isReminderOn = preferenceDataStoreViewModel.isReminderOn.collectAsState(initial = true)
  val reminderPeriodStart = preferenceDataStoreViewModel.reminderPeriodStart.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderPeriodEnd = preferenceDataStoreViewModel.reminderPeriodEnd.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderGap = preferenceDataStoreViewModel.reminderGap.collectAsState(initial = ReminderGap.NOT_SET)
  val reminderAfterGoalAchieved = preferenceDataStoreViewModel.reminderAfterGoalAchieved.collectAsState(initial = false)
  val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
  val (dialogToShow, setDialogToShow) =  remember { mutableStateOf("") }

  LaunchedEffect(key1 = true){
    columnAlpha.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 400)
    )
  }

  TopReminderTabBar(
    onCheckedChange = {
      preferenceDataStoreViewModel.setIsReminderOn(it)
      if(it) {
        ReminderReceiverUtil.setReminder(reminderGap = reminderGap.value, context = context)
      } else {
        ReminderReceiverUtil.cancelReminder(context = context)
      }
    },
    isReminderOn = isReminderOn.value
  )

  Column(
    modifier = Modifier.alpha(columnAlpha.value)
  ) {
    ReminderSettings(
      reminderPeriodStart = reminderPeriodStart.value,
      reminderPeriodEnd = reminderPeriodEnd.value,
      reminderGap = reminderGap.value,
      reminderAfterGoalAchieved = reminderAfterGoalAchieved.value,
      setShowDialog = setShowDialog,
      setDialogToShow = setDialogToShow,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      isReminderOn = isReminderOn.value
    )
  }

  if(showDialog && dialogToShow == Settings.REMINDER_PERIOD){
    SetReminderPeriodSettingDialog(
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      setShowDialog = setShowDialog,
      reminderPeriodStart = reminderPeriodStart.value,
      reminderPeriodEnd = reminderPeriodEnd.value,
    )
  }
  if(showDialog && dialogToShow == Settings.REMINDER_FREQUENCY){
    SetReminderFrequencySettingDialog(
      reminderGap = reminderGap.value,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      setShowDialog = setShowDialog,
      context = context
    )
  }
}