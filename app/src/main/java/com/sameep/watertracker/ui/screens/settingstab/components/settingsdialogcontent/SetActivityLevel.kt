package com.sameep.watertracker.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.ui.components.selectors.OptionRow
import com.sameep.watertracker.ui.components.dialogs.ShowDialog
import com.sameep.watertracker.utils.ActivityLevel
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Settings

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