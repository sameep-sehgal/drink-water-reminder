package com.sameep.watertracker.ui.screens.settingstab.components.settingsdialogcontent

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.ui.components.ShowDialog
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Settings
import com.sameep.watertracker.utils.Units
import com.sameep.watertracker.utils.Weight

@Composable
fun SetWeightSettingDialog(
  gender: String,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  weight:Int,
  weightUnit:String,
  waterUnit:String,
  activityLevel:String,
  weather:String,
) {
  var selectedWeight by  remember { mutableStateOf(weight) }
  fun setSelectedWeight(weight:Int) {
    selectedWeight = weight
  }
  var selectedWeightUnit by  remember { mutableStateOf(weightUnit) }
  fun setSelectedWeightUnit(unit:String) {
    selectedWeightUnit = unit
  }

  val unitPickerChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    if (newVal == 0) {
      setSelectedWeightUnit(Units.KG)
      setSelectedWeight(Weight.lbToKg(selectedWeight))
    }
    else {
      setSelectedWeightUnit(Units.LB)
      setSelectedWeight(Weight.kgToLb(selectedWeight))
    }
    Log.d("TAG", "GetWeight: Before -> $selectedWeight $selectedWeightUnit")
  }
  val weightPickerChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    setSelectedWeight(newVal)
  }

  ShowDialog(
    title = "Set ${Settings.WEIGHT}",
    content = {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        AndroidView(
          factory = { context ->
            val numberPicker = NumberPicker(context)
            numberPicker.maxValue = Weight.MAX_WEIGHT
            numberPicker.minValue = 1
            numberPicker.setOnValueChangedListener(weightPickerChangeListener)
            numberPicker
          },
          update = { view ->
            view.value = selectedWeight //To attach state variable
          }
        )

        AndroidView(
          factory = { context ->
            val numberPicker = NumberPicker(context)
            numberPicker.maxValue = 1
            numberPicker.minValue = 0
            numberPicker.displayedValues = arrayOf(
              Units.KG,
              Units.LB
            )
            numberPicker.setOnValueChangedListener(unitPickerChangeListener)
            numberPicker
          },
          update = { view ->
            view.value = if (selectedWeightUnit == Units.KG) 0 else 1
          }
        )
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setWeight(selectedWeight)
      preferenceDataStoreViewModel.setWeightUnit(selectedWeightUnit)
      val newIntake = RecommendedWaterIntake.calculateRecommendedWaterIntake(
        gender = gender,
        weight = selectedWeight,
        weightUnit = selectedWeightUnit,
        waterUnit = waterUnit,
        activityLevel = activityLevel,
        weather = weather
      )
      preferenceDataStoreViewModel.setRecommendedWaterIntake(newIntake)
    }
  )
}