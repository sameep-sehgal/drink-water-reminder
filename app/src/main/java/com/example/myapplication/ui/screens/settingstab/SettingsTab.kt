package com.example.myapplication.ui.screens.settingstab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.PreferenceDataStoreViewModel
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
  val activityLevel = preferenceDataStoreViewModel.activityLevel.collectAsState(initial = ActivityLevel.LIGHTLY_ACTIVE)
  val weather = preferenceDataStoreViewModel.weather.collectAsState(initial = Weather.NORMAL)
  val weightUnit = preferenceDataStoreViewModel.weightUnit.collectAsState(initial = Units.KG)
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)

  val isDailyWaterGoalTrackingRecommendedIntake = preferenceDataStoreViewModel.isDailyWaterGoalTrackingRecommendedIntake.collectAsState(initial = true)
  val recommendedWaterIntake = preferenceDataStoreViewModel.recommendedWaterIntake.collectAsState(initial = Weight.NOT_SET)
  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = Weight.NOT_SET)

  val reminderPeriodStart = preferenceDataStoreViewModel.reminderPeriodStart.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderPeriodEnd = preferenceDataStoreViewModel.reminderPeriodEnd.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderGap = preferenceDataStoreViewModel.reminderGap.collectAsState(initial = ReminderGap.NOT_SET)
  val reminderSound = preferenceDataStoreViewModel.reminderSound.collectAsState(initial = ReminderSound.WATER_DROP)
  val reminderAfterGoalAchieved = preferenceDataStoreViewModel.reminderAfterGoalAchieved.collectAsState(initial = false)

  val glassCapacity = preferenceDataStoreViewModel.glassCapacity.collectAsState(initial = Container.baseGlassCapacity(waterUnit.value))
  val mugCapacity = preferenceDataStoreViewModel.mugCapacity.collectAsState(initial = Container.baseMugCapacity(waterUnit.value))
  val bottleCapacity = preferenceDataStoreViewModel.bottleCapacity.collectAsState(initial = Container.baseBottleCapacity(waterUnit.value))

  val context = LocalContext.current
  val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
  val (dialogToShow, setDialogToShow) =  remember { mutableStateOf("") }

  Column(
    modifier = Modifier.verticalScroll(scrollState)
  ) {
    TopAppBar(
      backgroundColor = MaterialTheme.colors.primary
    ) {
      Text(
        text = "Settings",
        fontSize = 18.sp,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(8.dp, 0.dp)
      )
    }

    PersonalSettings(
      gender = gender.value,
      weight = weight.value,
      activityLevel = activityLevel.value,
      weather = weather.value,
      weightUnit = weightUnit.value,
      setShowDialog = setShowDialog,
      setDialogToShow = setDialogToShow
    )
    HowMuchWaterToDrinkSettings(
      recommendedWaterIntake = recommendedWaterIntake.value,
      waterUnit = waterUnit.value,
      dailyWaterGoal = dailyWaterGoal.value,
      isDailyWaterGoalTrackingRecommendedIntake = isDailyWaterGoalTrackingRecommendedIntake.value,
      setShowDialog = setShowDialog,
      setDialogToShow = setDialogToShow
    )
//    ContainerSettings(
//      glassCapacity = glassCapacity.value,
//      mugCapacity = mugCapacity.value,
//      bottleCapacity = bottleCapacity.value,
//      waterUnit = waterUnit.value,
//      setShowDialog = setShowDialog,
//      setDialogToShow = setDialogToShow
//    )
    MainSettings(
      setShowDialog,
      setDialogToShow
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
    if(showDialog && dialogToShow == Settings.GENDER){
      SetGenderSettingDialog(
        gender = gender.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        weight = weight.value,
        weightUnit = weightUnit.value,
        waterUnit = waterUnit.value,
        activityLevel = activityLevel.value,
        weather = weather.value
      )
    }
    if(showDialog && dialogToShow == Settings.WEIGHT){
      SetWeightSettingDialog(
        gender = gender.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        weight = weight.value,
        weightUnit = weightUnit.value,
        waterUnit = waterUnit.value,
        activityLevel = activityLevel.value,
        weather = weather.value
      )
    }
    if(showDialog && dialogToShow == Settings.ACTIVITY_LEVEL){
      SetActivityLevel(
        gender = gender.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        weight = weight.value,
        weightUnit = weightUnit.value,
        waterUnit = waterUnit.value,
        activityLevel = activityLevel.value,
        weather = weather.value
      )
    }
    if(showDialog && dialogToShow == Settings.WEATHER){
      SetWeather(
        gender = gender.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        weight = weight.value,
        weightUnit = weightUnit.value,
        waterUnit = waterUnit.value,
        activityLevel = activityLevel.value,
        weather = weather.value
      )
    }
    if(showDialog && dialogToShow == Settings.DAILY_WATER_GOAL){
      SetDailyWaterGoalSettingDialog(
        dailyWaterGoal = dailyWaterGoal.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        recommendedWaterIntake = recommendedWaterIntake.value,
        reminderPeriodStart = reminderPeriodStart.value,
        reminderPeriodEnd = reminderPeriodEnd.value,
        reminderGap = reminderGap.value,
        glassCapacity = glassCapacity.value,
        mugCapacity = mugCapacity.value,
        bottleCapacity = bottleCapacity.value,
        reminderSound = reminderSound.value,
        waterUnit = waterUnit.value,
        remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
        context = context
      )
    }
    if(showDialog && dialogToShow == Settings.GLASS_CAPACITY){
      SetContainerCapacity(
        container = Container.GLASS,
        capacity = glassCapacity.value,
        waterUnit = waterUnit.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        reminderGap = reminderGap.value,
        reminderPeriodStart = reminderPeriodStart.value,
        reminderPeriodEnd = reminderPeriodEnd.value,
        glassCapacity = glassCapacity.value,
        mugCapacity = mugCapacity.value,
        bottleCapacity = bottleCapacity.value,
        reminderSound = reminderSound.value,
        dailyWaterGoal = dailyWaterGoal.value,
        remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
        context = context
      )
    }
    if(showDialog && dialogToShow == Settings.MUG_CAPACITY){
      SetContainerCapacity(
        container = Container.MUG,
        capacity = mugCapacity.value,
        waterUnit = waterUnit.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        reminderGap = reminderGap.value,
        reminderPeriodStart = reminderPeriodStart.value,
        reminderPeriodEnd = reminderPeriodEnd.value,
        glassCapacity = glassCapacity.value,
        mugCapacity = mugCapacity.value,
        bottleCapacity = bottleCapacity.value,
        reminderSound = reminderSound.value,
        dailyWaterGoal = dailyWaterGoal.value,
        remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
        context = context
      )
    }
    if(showDialog && dialogToShow == Settings.BOTTLE_CAPACITY){
      SetContainerCapacity(
        container = Container.BOTTLE,
        capacity = bottleCapacity.value,
        waterUnit = waterUnit.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
        reminderGap = reminderGap.value,
        reminderPeriodStart = reminderPeriodStart.value,
        reminderPeriodEnd = reminderPeriodEnd.value,
        glassCapacity = glassCapacity.value,
        mugCapacity = mugCapacity.value,
        bottleCapacity = bottleCapacity.value,
        reminderSound = reminderSound.value,
        dailyWaterGoal = dailyWaterGoal.value,
        remindAfterGoalAchieved = reminderAfterGoalAchieved.value,
        context = context
      )
    }
  }
}

@Composable
fun PersonalSettings(
  gender:String,
  weight:Int,
  activityLevel:String,
  weather:String,
  weightUnit:String,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
){
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
    SettingsRowSelectValue(
      text = Settings.ACTIVITY_LEVEL,
      value = activityLevel,
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.ACTIVITY_LEVEL)
      }
    )
    SettingsRowSelectValue(
      text = Settings.WEATHER,
      value = weather,
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.WEATHER)
      }
    )
  }
}

@Composable
fun HowMuchWaterToDrinkSettings(
  recommendedWaterIntake:Int,
  dailyWaterGoal:Int,
  waterUnit:String,
  isDailyWaterGoalTrackingRecommendedIntake:Boolean,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
) {
  Column {
    SettingsSubheading(text = "How Much Water To Drink?")
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
  glassCapacity:Int,
  mugCapacity:Int,
  bottleCapacity:Int,
  waterUnit: String,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
){
  SettingsSubheading(text = "Containers")
  SettingsRowSelectValue(
    text = "Glass",
    value = "$glassCapacity$waterUnit",
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.GLASS_CAPACITY)
    }
  )
  SettingsRowSelectValue(
    text = "Mug",
    value = "$mugCapacity$waterUnit",
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.MUG_CAPACITY)
    }
  )
  SettingsRowSelectValue(
    text = "Bottle",
    value = "$bottleCapacity$waterUnit",
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.BOTTLE_CAPACITY)
    }
  )
}

@Composable
fun MainSettings(
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
){
  val context = LocalContext.current
  SettingsSubheading(text = "Other Settings")
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
