package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.theme.Typography
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
  val options = listOf(
    Weather.HOT,
    Weather.WARM,
    Weather.NORMAL,
    Weather.COLD
  )
  var selectedWeather by remember {
    mutableStateOf(weather)
  }
  val setSelectedWeather = {value: String -> selectedWeather = value}

  ShowDialog(
    title = "Set ${Settings.WEATHER}",
    content = {
      Column {
        options.forEach {
          Row(
            modifier = Modifier
              .clickable {
                setSelectedWeather(it)
              }
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = selectedWeather == it,
              onCheckedChange = { _ ->
                setSelectedWeather(it)
              }
            )
            Text(
              text = it,
              style = Typography.subtitle1
            )
          }
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