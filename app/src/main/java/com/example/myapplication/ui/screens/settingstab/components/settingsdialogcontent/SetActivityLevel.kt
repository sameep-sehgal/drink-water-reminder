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
  val options = listOf(
    ActivityLevel.VERY_ACTIVE,
    ActivityLevel.MODERATELY_ACTIVE,
    ActivityLevel.LIGHTLY_ACTIVE,
    ActivityLevel.SEDENTARY
  )
  var selectedActivityLevel by remember {
    mutableStateOf(activityLevel)
  }
  val setSelectedActivityLevel = {value: String -> selectedActivityLevel = value}

  ShowDialog(
    title = "Set ${Settings.ACTIVITY_LEVEL}",
    content = {
              Column {
                options.forEach {
                  Row(
                    modifier = Modifier
                      .clickable {
                        setSelectedActivityLevel(it)
                      }
                      .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Checkbox(
                      checked = selectedActivityLevel == it,
                      onCheckedChange = { _ ->
                        setSelectedActivityLevel(it)
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