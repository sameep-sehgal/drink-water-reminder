package com.example.myapplication.ui.screens.settingstab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowBooleanValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowNoValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowSelectValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsSubheading
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.*
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
  val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
  val (dialogToShow, setDialogToShow) =  remember { mutableStateOf("") }

  Column(
    modifier = Modifier.verticalScroll(scrollState)
  ) {
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
    PersonalSettings(
      gender.value,
      weight.value,
      weightUnit.value,
      recommendedWaterIntake.value,
      waterUnit.value,
      dailyWaterGoal.value,
      setShowDialog,
      setDialogToShow
    )
    ContainerSettings()
    MainSettings(
      appTheme.value,
      setShowDialog,
      setDialogToShow
    )

    if(showDialog && dialogToShow != Settings.GENDER){
      ShowDialog(
        title = "Set $dialogToShow",
        content = { /*TODO*/ },
        setShowDialog = setShowDialog,
        onConfirmButtonClick = { /*TODO*/ }
      )
    }
    if(showDialog && dialogToShow == Settings.REMINDER_PERIOD){
      SetReminderPeriodSettingDialog(
        reminderGap = reminderGap.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        reminderPeriodStart = reminderPeriodStart.value,
        reminderPeriodEnd = reminderPeriodEnd.value
      )
    }
    if(showDialog && dialogToShow == Settings.REMINDER_FREQUENCY){
      SetReminderFrequencySettingDialog(
        reminderGap = reminderGap.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        reminderPeriodStart = reminderPeriodStart.value,
        reminderPeriodEnd = reminderPeriodEnd.value
      )
    }
    if(showDialog && dialogToShow == Settings.REMINDER_SOUND){
      SetReminderSoundSettingDialog(
        reminderSound = reminderSound.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog
      )
    }
    if(showDialog && dialogToShow == Settings.GENDER){
      SetGenderSettingDialog(
        gender = gender.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        weight = weight.value,
        weightUnit = weightUnit.value,
        waterUnit = waterUnit.value
      )
    }
    if(showDialog && dialogToShow == Settings.WEIGHT){
      SetWeightSettingDialog(
        gender = gender.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        weight = weight.value,
        weightUnit = weightUnit.value,
        waterUnit = waterUnit.value
      )
    }
    if(showDialog && dialogToShow == Settings.DAILY_WATER_GOAL){
      SetDailyWaterGoalSettingDialog(
        dailyWaterGoal = dailyWaterGoal.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        recommendedWaterIntake = recommendedWaterIntake.value,
        waterUnit = waterUnit.value
      )
    }
    if(showDialog && dialogToShow == Settings.APP_THEME){
      SetAppThemeSettingDialog(
        appTheme = appTheme.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog
      )
    }
  }
}

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
  SettingsSubheading(text = "Reminder Settings")
  Divider()
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
      text = Settings.REMINDER_SOUND,
      value = reminderSound,
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.REMINDER_SOUND)
      }
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
  dailyWaterGoal:Int,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
){
  SettingsSubheading(text = "Personal Settings")
  Column {
    SettingsRowSelectValue(
      text = Settings.GENDER,
      value = gender,
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.GENDER)
      }
    )
    SettingsRowSelectValue(
      text = Settings.WEIGHT,
      value = "$weight$weightUnit",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.WEIGHT)
      }
    )
//    SettingsRowSelectValue(
//      text = "Units",
//      value = "kg/ml",
//      onSettingsRowClick = { /*TODO()*/ }
//    )
    SettingsRowSelectValue(
      text = Settings.RECOMMENDED_WATER_INTAKE,
      value = "$recommendedWaterIntake$waterUnit",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.RECOMMENDED_WATER_INTAKE)
      }
    )
    SettingsRowSelectValue(
      text = Settings.DAILY_WATER_GOAL,
      value = "$dailyWaterGoal$waterUnit",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.DAILY_WATER_GOAL)
      }
    )
  }
}

@Composable
fun ContainerSettings(

){
  SettingsSubheading(text = "Containers")
  SettingsRowSelectValue(
    text = "Glass",
    value = "200mL",
    onSettingsRowClick = {}
  )
  SettingsRowSelectValue(
    text = "Mug",
    value = "300mL",
    onSettingsRowClick = {}
  )
  SettingsRowSelectValue(
    text = "Bottle",
    value = "500mL",
    onSettingsRowClick = {}
  )
}

@Composable
fun MainSettings(
  appTheme:String,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
){
  val context = LocalContext.current
  SettingsSubheading(text = "Main Settings")
  SettingsRowSelectValue(
    text = Settings.APP_THEME,
    value = appTheme,
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.APP_THEME)
    }
  )
  SettingsRowNoValue(
    text = Settings.RESET_DATA,
    onSettingsRowClick = {/*TODO()*/}
  )
  SettingsRowNoValue(
    text = Settings.RATE_US,
    onSettingsRowClick = {/*TODO()*/}
  )
  SettingsRowNoValue(
    text = Settings.SHARE,
    onSettingsRowClick = {
      val sharingIntent = Intent(Intent.ACTION_SEND)
      sharingIntent.type = "text/plain"
      sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
      sharingIntent.putExtra(Intent.EXTRA_TEXT, "Isko Edit karna hai")//TODO
      val bundleOptions = Bundle()
      startActivity(
        context,
        Intent.createChooser(sharingIntent, "Share via"),
        bundleOptions
      )
    }
  )
  SettingsRowNoValue(
    text = Settings.CONTACT_DEVELOPERS,
    onSettingsRowClick = {
      val contactIntent = Intent(Intent.ACTION_SEND)
      contactIntent.data = Uri.parse("test@gmail.com")
      contactIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
      contactIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      contactIntent.type = "text/plain"
      contactIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
      contactIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("test@gmail.com"))
      val bundleOptions = Bundle()
      startActivity(
        context,
        Intent.createChooser(contactIntent, "Send Email via"),
        bundleOptions
      )
    }
  )
}
