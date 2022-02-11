package com.example.myapplication.ui.screens.collectuserdata

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.collectuserdata.components.*
import com.example.myapplication.ui.screens.collectuserdata.components.screens.*
import com.example.myapplication.utils.*
import java.util.*

@Composable
fun CollectUserDataContent(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val scrollState = rememberScrollState()
  var currentScreen by remember{ mutableStateOf(0)}
  val setCurrentScreen = { value:Int ->
    currentScreen = value
  }
  val columnAlpha by remember{ mutableStateOf(Animatable(1f)) }
  val context = LocalContext.current
  val scope = rememberCoroutineScope()
  val gender = preferenceDataStoreViewModel.gender.collectAsState(initial = Gender.NOT_SET)
  val weight = preferenceDataStoreViewModel.weight.collectAsState(initial = Weight.NOT_SET)
  val activityLevel = preferenceDataStoreViewModel.activityLevel.collectAsState(initial = ActivityLevel.LIGHTLY_ACTIVE)
  val weather = preferenceDataStoreViewModel.weather.collectAsState(initial = Weather.NORMAL)
  val weightUnit = preferenceDataStoreViewModel.weightUnit.collectAsState(initial = Units.KG)
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val isReminderOn = preferenceDataStoreViewModel.isReminderOn.collectAsState(initial = true)
  val reminderPeriodStart = preferenceDataStoreViewModel.reminderPeriodStart.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderPeriodEnd = preferenceDataStoreViewModel.reminderPeriodEnd.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderGap = preferenceDataStoreViewModel.reminderGap.collectAsState(initial = ReminderGap.NOT_SET)
  var recommendedWaterIntake by remember {
    mutableStateOf(RecommendedWaterIntake.DEFAULT_WATER_GOAL_IN_ML)
  }
  val onLetsGoButtonClick = {
    preferenceDataStoreViewModel.setRecommendedWaterIntake(recommendedWaterIntake)
    preferenceDataStoreViewModel.setDailyWaterGoal(recommendedWaterIntake)

    val baseGlassCapacity = Container.baseGlassCapacity(waterUnit.value)
    val baseMugCapacity = Container.baseMugCapacity(waterUnit.value)
    val baseBottleCapacity = Container.baseBottleCapacity(waterUnit.value)
    //Set Repeating Reminder
    ReminderReceiverUtil.setReminder(reminderGap = reminderGap.value, context = context)

    preferenceDataStoreViewModel.setGlassCapacity(baseGlassCapacity)
    preferenceDataStoreViewModel.setMugCapacity(baseMugCapacity)
    preferenceDataStoreViewModel.setBottleCapacity(baseBottleCapacity)
    preferenceDataStoreViewModel.setFirstWaterDataDate(DateString.getTodaysDate())

    //Insert Default Beveraged in Database
    Beverages.defaultBeverages.forEach {
      roomDatabaseViewModel.insertBeverage(it)
    }
    //User Data stored now switch to TabLayout HomeScreen
    preferenceDataStoreViewModel.setIsUserInfoCollected(true)
  }

  LaunchedEffect(key1 = currentScreen) {
    if(currentScreen == 7) {
      recommendedWaterIntake =
        RecommendedWaterIntake.calculateRecommendedWaterIntake(
          gender = gender.value,
          weight = weight.value,
          weightUnit = weightUnit.value,
          waterUnit = waterUnit.value,
          activityLevel = activityLevel.value,
          weather = weather.value
        )
    }
    columnAlpha.animateTo(1f)
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .alpha(columnAlpha.value),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState),
      verticalArrangement = Arrangement.Center
    ) {
      when (currentScreen) {
        0 -> IntroScreen(currentScreen, setCurrentScreen, columnAlpha,scope)
        1 -> GetGender(preferenceDataStoreViewModel, gender.value)
        2 -> GetWeight(preferenceDataStoreViewModel, weight.value, weightUnit.value)
        3 -> GetActivityLevel(preferenceDataStoreViewModel, activityLevel.value)
        4 -> GetWeather(preferenceDataStoreViewModel, weather.value)
        5 -> GetWaterUnit(preferenceDataStoreViewModel, waterUnit.value)
        6 -> GetReminderPeriod(
          preferenceDataStoreViewModel,
          reminderPeriodStart.value,
          reminderPeriodEnd.value,
          isReminderOn.value
        )
        7 -> CalculateWaterIntake(
          recommendedWaterIntake, waterUnit.value
        )
      }
    }

    if(currentScreen != 0 && currentScreen != 7) {
      LinearProgressIndicator(currentScreen/7f)

      NextBackButtonsRow(
        setCurrentScreen = setCurrentScreen,
        currentScreen = currentScreen,
        columnAlpha = columnAlpha,
        scope = scope
      )
    }
    if(currentScreen == 7) {
      CalculateWaterIntakeButtonsRow(
        scope = scope,
        columnAlpha = columnAlpha,
        currentScreen = currentScreen,
        setCurrentScreen = setCurrentScreen,
        onLetsGoButtonClick = onLetsGoButtonClick
      )
    }
  }
}