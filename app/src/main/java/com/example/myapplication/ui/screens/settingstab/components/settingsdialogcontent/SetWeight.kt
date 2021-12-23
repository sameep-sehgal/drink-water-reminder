package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.*

@Composable
fun SetWeightSettingDialog(
  gender: String,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  weight:Int,
  weightUnit:String,
  waterUnit:String
) {
  var selectedWeight by  remember { mutableStateOf(weight) }
  fun setSelectedWeight(weight:Int) {
    selectedWeight = weight
  }
  var selectedWeightUnit by  remember { mutableStateOf(weightUnit) }
  fun setSelectedWeightUnit(unit:String) {
    selectedWeightUnit = unit
  }

  val unitPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
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
  val weightPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
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
      val newIntake = RecommendedWaterIntake.calculateBaseWaterIntake(
        gender = gender,
        weight = selectedWeight,
        weightUnit = selectedWeightUnit,
        waterUnit = waterUnit
      )
      preferenceDataStoreViewModel.setRecommendedWaterIntake(newIntake)
    }
  )
}