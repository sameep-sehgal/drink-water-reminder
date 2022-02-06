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
        Row(
          modifier = Modifier.clickable {
            selectedGender.value = Gender.MALE
          }.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = selectedGender.value == Gender.MALE,
            onCheckedChange = {selectedGender.value = Gender.MALE}
          )
          Text(
            text = Gender.MALE,
            style = Typography.subtitle1
          )
        }
        Row(
          modifier = Modifier.clickable {
            selectedGender.value = Gender.FEMALE
          }.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = selectedGender.value == Gender.FEMALE,
            onCheckedChange = {selectedGender.value = Gender.FEMALE}
          )
          Text(
            text = Gender.FEMALE,
            style = Typography.subtitle1
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