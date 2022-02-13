package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Weather
import com.example.myapplication.utils.Settings

@Composable
fun SetWeather(
  setShowDialog:(Boolean) -> Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  weight:Int,
  weightUnit:String,
  waterUnit:String,
  activityLevel:String,
  weather:String,
  gender: String,
) {
  var selectedWeather by remember {
    mutableStateOf(weather)
  }
  val setSelectedWeather = {value: String -> selectedWeather = value}

  ShowDialog(
    title = "Set ${Settings.WEATHER}",
    content = {
      Column {
        Weather.OPTIONS.forEach {
          OptionRow(
            selected = selectedWeather == it,
            onClick = { setSelectedWeather(it) },
            text = it
          )
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setWeather(selectedWeather)
      val newIntake = RecommendedWaterIntake.calculateRecommendedWaterIntake(
        gender = gender,
        weight = weight,
        weightUnit = weightUnit,
        waterUnit = waterUnit,
        activityLevel = activityLevel,
        weather = selectedWeather
      )
      preferenceDataStoreViewModel.setRecommendedWaterIntake(newIntake)
    }
  )
}