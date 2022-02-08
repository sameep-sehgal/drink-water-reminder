package com.example.myapplication.ui.screens.collectuserdata.components.screens

import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.utils.Units

@Composable
fun GetWaterUnit(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  waterUnit: String
) {
  fun setSelectedWaterUnit(unit:String) {
    preferenceDataStoreViewModel.setWaterUnit(unit)
  }

  val waterUnitPickerChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    if (newVal == 0) {
      setSelectedWaterUnit(Units.ML)
    }
    else {
      setSelectedWaterUnit(Units.OZ)
    }
  }

  Text(
    modifier = Modifier.padding(0.dp,8.dp),
    text = "Select Water Measurement Unit",
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
  )
  Row(
    modifier = Modifier.padding(24.dp,8.dp),
  ) {
    AndroidView(
      factory = { context ->
        val numberPicker = NumberPicker(context)
        numberPicker.maxValue = 1
        numberPicker.minValue = 0
        numberPicker.displayedValues = arrayOf(
          "MilliLitres (${Units.ML})",
          "Ounces (${ Units.OZ })"
        )
        numberPicker.setOnValueChangedListener(waterUnitPickerChangeListener)
        numberPicker
      },
      update = { view ->
        view.value = if(waterUnit == Units.ML) 0 else 1
      }
    )
  }
}