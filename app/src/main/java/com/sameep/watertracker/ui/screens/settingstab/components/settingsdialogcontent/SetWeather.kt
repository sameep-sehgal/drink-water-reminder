package com.sameep.watertracker.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.ui.components.OptionRow
import com.sameep.watertracker.ui.components.ShowDialog
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Weather
import com.sameep.watertracker.utils.Settings

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