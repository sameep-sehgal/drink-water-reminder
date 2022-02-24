package com.sameep.watertracker.ui.screens.settingstab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.sameep.watertracker.BuildConfig
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.ui.screens.settingstab.components.SettingsRowBooleanValue
import com.sameep.watertracker.ui.screens.settingstab.components.SettingsRowNoValue
import com.sameep.watertracker.ui.screens.settingstab.components.SettingsRowSelectValue
import com.sameep.watertracker.ui.screens.settingstab.components.SettingsSubheading
import com.sameep.watertracker.R
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.components.dialogs.RateAppDialog
import com.sameep.watertracker.ui.screens.settingstab.components.settingsdialogcontent.*
import com.sameep.watertracker.utils.*

val horizontalPaddingSettings = 8.dp
val verticalPaddingSettings = 16.dp

@Composable
fun SettingsTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  todaysWaterRecord: DailyWaterRecord
){
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val columnAlpha by remember {
    mutableStateOf(Animatable(0f))
  }
  val gender = preferenceDataStoreViewModel.gender.collectAsState(initial = Gender.NOT_SET)
  val weight = preferenceDataStoreViewModel.weight.collectAsState(initial = Weight.NOT_SET)
  val activityLevel = preferenceDataStoreViewModel.activityLevel.collectAsState(initial = ActivityLevel.LIGHTLY_ACTIVE)
  val weather = preferenceDataStoreViewModel.weather.collectAsState(initial = Weather.NORMAL)
  val weightUnit = preferenceDataStoreViewModel.weightUnit.collectAsState(initial = Units.KG)
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)

  val isDailyWaterGoalTrackingRecommendedIntake = preferenceDataStoreViewModel.isDailyWaterGoalTrackingRecommendedIntake.collectAsState(initial = true)
  val recommendedWaterIntake = preferenceDataStoreViewModel.recommendedWaterIntake.collectAsState(initial = Weight.NOT_SET)
  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = Weight.NOT_SET)

  val glassCapacity = preferenceDataStoreViewModel.glassCapacity.collectAsState(initial = Container.baseGlassCapacity(waterUnit.value))
  val mugCapacity = preferenceDataStoreViewModel.mugCapacity.collectAsState(initial = Container.baseMugCapacity(waterUnit.value))
  val bottleCapacity = preferenceDataStoreViewModel.bottleCapacity.collectAsState(initial = Container.baseBottleCapacity(waterUnit.value))

  val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
  val (dialogToShow, setDialogToShow) =  remember { mutableStateOf("") }

  LaunchedEffect(key1 = recommendedWaterIntake.value) {
    if(isDailyWaterGoalTrackingRecommendedIntake.value){
      preferenceDataStoreViewModel.setDailyWaterGoal(recommendedWaterIntake.value)
    }
  }

  LaunchedEffect(key1 = true){
    columnAlpha.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 400)
    )
  }

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

  Column(
    modifier = Modifier
      .verticalScroll(scrollState)
      .alpha(columnAlpha.value)
  ) {
    PersonalSettings(
      gender = gender.value,
      weight = weight.value,
      activityLevel = activityLevel.value,
      weather = weather.value,
      weightUnit = weightUnit.value,
      setShowDialog = setShowDialog,
      setDialogToShow = setDialogToShow
    )
    YourWaterIntakeSettings(
      recommendedWaterIntake = recommendedWaterIntake.value,
      waterUnit = waterUnit.value,
      dailyWaterGoal = dailyWaterGoal.value,
      isDailyWaterGoalTrackingRecommendedIntake = isDailyWaterGoalTrackingRecommendedIntake.value,
      setShowDialog = setShowDialog,
      setDialogToShow = setDialogToShow,
      preferenceDataStoreViewModel = preferenceDataStoreViewModel
    )
    ContainerSettings(
      glassCapacity = glassCapacity.value,
      mugCapacity = mugCapacity.value,
      bottleCapacity = bottleCapacity.value,
      waterUnit = waterUnit.value,
      setShowDialog = setShowDialog,
      setDialogToShow = setDialogToShow
    )
    OtherSettings(
     setShowDialog =  setShowDialog,
     setDialogToShow = setDialogToShow
    )

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
        waterUnit = waterUnit.value,
        roomDatabaseViewModel = roomDatabaseViewModel,
        todaysWaterRecord = todaysWaterRecord
      )
    }
    if(showDialog && dialogToShow == Settings.GLASS_CAPACITY){
      SetContainerCapacity(
        container = Container.GLASS,
        capacity = glassCapacity.value,
        waterUnit = waterUnit.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
      )
    }
    if(showDialog && dialogToShow == Settings.MUG_CAPACITY){
      SetContainerCapacity(
        container = Container.MUG,
        capacity = mugCapacity.value,
        waterUnit = waterUnit.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
      )
    }
    if(showDialog && dialogToShow == Settings.BOTTLE_CAPACITY){
      SetContainerCapacity(
        container = Container.BOTTLE,
        capacity = bottleCapacity.value,
        waterUnit = waterUnit.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowDialog = setShowDialog,
      )
    }
    if(showDialog && dialogToShow == Settings.RATE_US){
      RateAppDialog(
        setShowDialog = setShowDialog,
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
fun YourWaterIntakeSettings(
  recommendedWaterIntake:Int,
  dailyWaterGoal:Int,
  waterUnit:String,
  isDailyWaterGoalTrackingRecommendedIntake:Boolean,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  Column {
    SettingsSubheading(text = "Your Water Intake")
    SettingsRowSelectValue(
      text = Settings.RECOMMENDED_WATER_INTAKE,
      value = "$recommendedWaterIntake$waterUnit",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.RECOMMENDED_WATER_INTAKE)
      },
      enabled = isDailyWaterGoalTrackingRecommendedIntake
    )
    SettingsRowBooleanValue(
      text = "Set To Recommended Value",
      value = isDailyWaterGoalTrackingRecommendedIntake,
      onCheckedChange = {
        preferenceDataStoreViewModel.setIsDailyWaterGoalTrackingRecommendedIntake(it)
        if(it) preferenceDataStoreViewModel.setDailyWaterGoal(recommendedWaterIntake)
      }
    )
    SettingsRowSelectValue(
      text = Settings.DAILY_WATER_GOAL,
      value = "$dailyWaterGoal$waterUnit",
      onSettingsRowClick = {
        setShowDialog(true)
        setDialogToShow(Settings.DAILY_WATER_GOAL)
      },
      enabled = !isDailyWaterGoalTrackingRecommendedIntake
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
  SettingsSubheading(text = "Configure Intake Buttons")
  SettingsRowSelectValue(
    text = "Glass",
    value = "$glassCapacity$waterUnit",
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.GLASS_CAPACITY)
    },
    icon = R.drawable.glass_2
  )
  SettingsRowSelectValue(
    text = "Mug",
    value = "$mugCapacity$waterUnit",
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.MUG_CAPACITY)
    },
    icon = R.drawable.mug_3
  )
  SettingsRowSelectValue(
    text = "Bottle",
    value = "$bottleCapacity$waterUnit",
    onSettingsRowClick = {
      setShowDialog(true)
      setDialogToShow(Settings.BOTTLE_CAPACITY)
    },
    icon = R.drawable.bottle_4
  )
}

@Composable
fun OtherSettings(
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
){
  val context = LocalContext.current
  SettingsSubheading(text = "Other Settings")
  SettingsRowNoValue(
    text = Settings.RATE_US+" \uD83D\uDC96",
    onSettingsRowClick = {
      setDialogToShow(Settings.RATE_US)
      setShowDialog(true)
    }
  )
  SettingsRowNoValue(
    text = Settings.SHARE,
    onSettingsRowClick = {
      val sharingIntent = Intent(Intent.ACTION_SEND)
      sharingIntent.type = "text/plain"
      sharingIntent.putExtra(
        Intent.EXTRA_TEXT,
        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +
        "\nHey! Check out this awesome app that will remind you to Drink Water and " +
                "will Track your Water Intake."
      )
      val bundleOptions = Bundle()
      startActivity(
        context,
        Intent.createChooser(sharingIntent, "Share via"),
        bundleOptions
      )
    }
  )
  SettingsRowNoValue(
    text = Settings.PRIVACY_POLICY,
    onSettingsRowClick = {
      val browserIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://pages.flycricket.io/drink-water-reminder-4/privacy.html")
      )
      context.startActivity(browserIntent)
    }
  )
}
