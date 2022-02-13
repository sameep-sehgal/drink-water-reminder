package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.ActivityLevel
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Settings

@Composable
fun SetActivityLevel(
  setShowDialog:(Boolean) -> Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  weight:Int,
  weightUnit:String,
  waterUnit:String,
  activityLevel:String,
  weather:String,
  gender: String,
) {
  var selectedActivityLevel by remember {
    mutableStateOf(activityLevel)
  }
  val setSelectedActivityLevel = {value: String -> selectedActivityLevel = value}

  ShowDialog(
    title = "Set ${Settings.ACTIVITY_LEVEL}",
    content = {
      Column {
        ActivityLevel.OPTIONS.forEach {
          OptionRow(
            selected = selectedActivityLevel == it,
            onClick = { setSelectedActivityLevel(it) },
            text = it
          )
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setActivityLevel(selectedActivityLevel)
      val newIntake = RecommendedWaterIntake.calculateRecommendedWaterIntake(
        gender = gender,
        weight = weight,
        weightUnit = weightUnit,
        waterUnit = waterUnit,
        activityLevel = selectedActivityLevel,
        weather = weather
      )
      preferenceDataStoreViewModel.setRecommendedWaterIntake(newIntake)
    }
  )
}