package com.example.myapplication.ui.screens.collectuserdata

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.collectuserdata.components.CalculateWaterIntake
import com.example.myapplication.ui.screens.collectuserdata.components.GetWaterUnit
import com.example.myapplication.ui.screens.collectuserdata.components.GetWeight
import com.example.myapplication.ui.screens.collectuserdata.components.IntroScreen
import com.example.myapplication.utils.Gender
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun CollectUserData(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {

  var currentScreen by remember{ mutableStateOf(0)}
  val setCurrentScreen = { value:Int ->
    currentScreen = value
  }

  Column() {
    when(currentScreen) {
      0 -> IntroScreen(currentScreen ,setCurrentScreen)
      1 -> GetGender(preferenceDataStoreViewModel,currentScreen ,setCurrentScreen)
      2 -> GetWeight(preferenceDataStoreViewModel,currentScreen ,setCurrentScreen)
      3 -> GetWaterUnit(preferenceDataStoreViewModel,currentScreen ,setCurrentScreen)
      4 -> GetReminderPeriod(preferenceDataStoreViewModel,currentScreen ,setCurrentScreen)
      5 -> GetReminderFrequency(preferenceDataStoreViewModel,currentScreen ,setCurrentScreen)
      6 -> CalculateWaterIntake(preferenceDataStoreViewModel,currentScreen ,setCurrentScreen)
    }
  }
}