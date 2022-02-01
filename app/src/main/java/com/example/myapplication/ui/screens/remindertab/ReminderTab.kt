package com.example.myapplication.ui.screens.remindertab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.remindertab.components.ReminderSettings
import com.example.myapplication.ui.screens.remindertab.components.TopReminderTabBar
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.SetReminderFrequencySettingDialog
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.SetReminderPeriodSettingDialog
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.SetReminderSoundSettingDialog
import com.example.myapplication.utils.*

@Composable
fun ReminderTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  val context = LocalContext.current
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = Weight.NOT_SET)
  val reminderPeriodStart = preferenceDataStoreViewModel.reminderPeriodStart.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderPeriodEnd = preferenceDataStoreViewModel.reminderPeriodEnd.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderGap = preferenceDataStoreViewModel.reminderGap.collectAsState(initial = ReminderGap.NOT_SET)
  val reminderSound = preferenceDataStoreViewModel.reminderSound.collectAsState(initial = ReminderSound.WATER_DROP)
  val reminderAfterGoalAchieved = preferenceDataStoreViewModel.reminderAfterGoalAchieved.collectAsState(initial = false)
  val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
  val (dialogToShow, setDialogToShow) =  remember { mutableStateOf("") }

  val glassCapacity = preferenceDataStoreViewModel.glassCapacity.collectAsState(initial = Container.baseGlassCapacity(waterUnit.value))
  val mugCapacity = preferenceDataStoreViewModel.mugCapacity.collectAsState(initial = Container.baseMugCapacity(waterUnit.value))
  val bottleCapacity = preferenceDataStoreViewModel.bottleCapacity.collectAsState(initial = Container.baseBottleCapacity(waterUnit.value))

  TopReminderTabBar(onCheckedChange = {})

  ReminderSettings(
    reminderPeriodStart.value,
    reminderPeriodEnd.value,
    reminderGap.value,
    reminderSound.value,
    reminderAfterGoalAchieved.value,
    setShowDialog,
    setDialogToShow,
    preferenceDataStoreViewModel
  )

  if(showDialog && dialogToShow == Settings.REMINDER_PERIOD){
    SetReminderPeriodSettingDialog(
      reminderGap = reminderGap.value,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      setShowDialog = setShowDialog,
      reminderPeriodStart = reminderPeriodStart.value,
      reminderPeriodEnd = reminderPeriodEnd.value,
      glassCapacity = glassCapacity.value,
      mugCapacity = mugCapacity.value,
      bottleCapacity = bottleCapacity.value,
      reminderSound = reminderSound.value,
      dailyWaterGoal = dailyWaterGoal.value,
      remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
      waterUnit = waterUnit.value,
      context = context
    )
  }
  if(showDialog && dialogToShow == Settings.REMINDER_FREQUENCY){
    SetReminderFrequencySettingDialog(
      reminderGap = reminderGap.value,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      setShowDialog = setShowDialog,
      reminderPeriodStart = reminderPeriodStart.value,
      reminderPeriodEnd = reminderPeriodEnd.value,
      glassCapacity = glassCapacity.value,
      mugCapacity = mugCapacity.value,
      bottleCapacity = bottleCapacity.value,
      reminderSound = reminderSound.value,
      dailyWaterGoal = dailyWaterGoal.value,
      remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
      waterUnit = waterUnit.value,
      context = context
    )
  }
  if(showDialog && dialogToShow == Settings.REMINDER_SOUND){
    SetReminderSoundSettingDialog(
      reminderSound = reminderSound.value,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      setShowDialog = setShowDialog,
      reminderPeriodStart = reminderPeriodStart.value,
      reminderPeriodEnd = reminderPeriodEnd.value,
      reminderGap = reminderGap.value,
      glassCapacity = glassCapacity.value,
      mugCapacity = mugCapacity.value,
      bottleCapacity = bottleCapacity.value,
      dailyWaterGoal = dailyWaterGoal.value,
      remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
      waterUnit = waterUnit.value,
      context = context
    )
  }
}