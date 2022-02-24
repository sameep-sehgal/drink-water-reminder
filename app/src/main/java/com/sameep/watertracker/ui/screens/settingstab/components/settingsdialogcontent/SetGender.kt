package com.sameep.watertracker.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.ui.components.selectors.OptionRow
import com.sameep.watertracker.ui.components.dialogs.ShowDialog
import com.sameep.watertracker.utils.Gender
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Settings

@Composable
fun SetGenderSettingDialog(
  gender: String,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  weight:Int,
  weightUnit:String,
  waterUnit:String,
  activityLevel:String,
  weather:String,
) {
  val selectedGender = remember { mutableStateOf(gender) }

  ShowDialog(
    title = "Set ${Settings.GENDER}",
    content = {
      //Bring Show Dialog here
      Column {
        Gender.OPTIONS.forEach {
          OptionRow(
            selected = selectedGender.value == it,
            onClick = { selectedGender.value = it },
            text = it
          )
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setGender(selectedGender.value)
      val newIntake = RecommendedWaterIntake.calculateRecommendedWaterIntake(
        gender = selectedGender.value,
        weight = weight,
        weightUnit = weightUnit,
        waterUnit = waterUnit,
        activityLevel = activityLevel,
        weather = weather
      )
      preferenceDataStoreViewModel.setRecommendedWaterIntake(newIntake)
    }
  )
}