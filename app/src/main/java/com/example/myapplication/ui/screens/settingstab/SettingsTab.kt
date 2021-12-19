package com.example.myapplication.ui.screens.settingstab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowBooleanValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowNoValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowSelectValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsSubheading
import com.example.myapplication.utils.*

val horizontalPaddingSettings = 8.dp
val verticalPaddingSettings = 16.dp

@Composable
fun SettingsTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
){
  val scrollState = rememberScrollState()
  val gender = preferenceDataStoreViewModel.gender.collectAsState(initial = Gender.NOT_SET)
  val weight = preferenceDataStoreViewModel.weight.collectAsState(initial = Weight.NOT_SET)
  val weightUnit = preferenceDataStoreViewModel.weightUnit.collectAsState(initial = Units.KG)
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val recommendedWaterIntake = preferenceDataStoreViewModel.recommendedWaterIntake.collectAsState(initial = Weight.NOT_SET)
  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = Weight.NOT_SET)
  val reminderPeriodStart = preferenceDataStoreViewModel.reminderPeriodStart.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderPeriodEnd = preferenceDataStoreViewModel.reminderPeriodEnd.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderGap = preferenceDataStoreViewModel.reminderGap.collectAsState(initial = ReminderGap.NOT_SET)
  val reminderSound = preferenceDataStoreViewModel.reminderSound.collectAsState(initial = ReminderSound.WATER_DROP)
  val appTheme = preferenceDataStoreViewModel.appTheme.collectAsState(initial = ReminderSound.WATER_DROP)
  val reminderAfterGoalAchieved = preferenceDataStoreViewModel.reminderAfterGoalAchieved.collectAsState(initial = false)

  Column(
    modifier = Modifier.verticalScroll(scrollState)
  ) {
    ReminderSettings(
      reminderPeriodStart.value,
      reminderPeriodEnd.value,
      reminderGap.value,
      reminderSound.value,
      reminderAfterGoalAchieved.value
    )
    PersonalSettings(
      gender.value,
      weight.value,
      weightUnit.value,
      recommendedWaterIntake.value,
      waterUnit.value,
      dailyWaterGoal.value
    )
    MainSettings(
      appTheme.value
    )
  }
}

@Composable
fun ReminderSettings(
  reminderPeriodStart:String,
  reminderPeriodEnd:String,
  reminderGap:Int,
  reminderSound:String,
  reminderAfterGoalAchieved:Boolean
){
  SettingsSubheading(text = "Reminder Settings")
  Divider()
  Column {
    SettingsRowSelectValue(
      text = "Reminder Period",
      value = "$reminderPeriodStart-$reminderPeriodEnd",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Reminder Frequency",
      value = "${ReminderGap.GAP_OPTION_TEXT_MAPPER[reminderGap]}",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowBooleanValue(
      text = "Remind after goal is achieved?",
      value = reminderAfterGoalAchieved,
      onCheckedChange = {}
    )
    SettingsRowSelectValue(
      text = "Reminder Sound",
      value = reminderSound,
      onSettingsRowClick = { /*TODO()*/ }
    )
  }
  Divider()
}

@Composable
fun PersonalSettings(
  gender:String,
  weight:Int,
  weightUnit:String,
  recommendedWaterIntake:Int,
  waterUnit:String,
  dailyWaterGoal:Int
){
  SettingsSubheading(text = "Personal Settings")
  Divider()
  Column {
    SettingsRowSelectValue(
      text = "Gender",
      value = gender,
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Weight",
      value = "$weight$weightUnit",
      onSettingsRowClick = { /*TODO()*/ }
    )
//    SettingsRowSelectValue(
//      text = "Units",
//      value = "kg/ml",
//      onSettingsRowClick = { /*TODO()*/ }
//    )
    SettingsRowSelectValue(
      text = "Recommended Water Intake",
      value = "$recommendedWaterIntake$waterUnit",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Daily Water Goal",
      value = "$dailyWaterGoal$waterUnit",
      onSettingsRowClick = { /*TODO()*/ }
    )
  }
  Divider()
}

@Composable
fun MainSettings(
  appTheme:String
){
  SettingsSubheading(text = "Main Settings")
  Divider()
  SettingsRowSelectValue(
    text = "App Theme",
    value = appTheme,
    onSettingsRowClick = { /*TODO()*/ }
  )
  SettingsRowNoValue(
    text = "Reset Data",
    onSettingsRowClick = {/*TODO()*/}
  )
  SettingsRowNoValue(
    text = "Rate Us",
    onSettingsRowClick = {/*TODO()*/}
  )
  SettingsRowNoValue(
    text = "Share",
    onSettingsRowClick = {/*TODO()*/}
  )
  SettingsRowNoValue(
    text = "Contact Developers",
    onSettingsRowClick = {/*TODO()*/}
  )
  Divider()
}