package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.Gender
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Settings

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