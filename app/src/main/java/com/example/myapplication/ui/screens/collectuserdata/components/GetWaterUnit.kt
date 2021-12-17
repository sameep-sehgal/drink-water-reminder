package com.example.myapplication.ui.screens.collectuserdata.components

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.hometab.components.AnimatedWaterGlass
import com.example.myapplication.utils.Units

@Composable
fun GetWaterUnit(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  var selectedWaterUnit by  remember { mutableStateOf(Units.ML) }
  fun setSelectedWaterUnit(unit:String) {
    selectedWaterUnit = unit
  }

  val waterUnitPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    if (newVal == 0) {
      setSelectedWaterUnit(Units.ML)
    }
    else {
      setSelectedWaterUnit(Units.OZ)
    }
    Log.d("TAG", "GetWeight: Before -> $selectedWaterUnit")
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ){
    Text(
      text = "Select Measurement Unit",
      fontWeight = FontWeight.Bold
    )
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
      AnimatedWaterGlass()
      Row(
        modifier = Modifier.padding(24.dp,8.dp),
      ) {
        AndroidView(
          factory = { context ->
            val numberPicker = NumberPicker(context)
            numberPicker.maxValue = 1
            numberPicker.minValue = 0
            numberPicker.displayedValues = arrayOf(
              Units.ML,
              Units.OZ
            )
            numberPicker.setOnValueChangedListener(waterUnitPickerChangeListener)
            numberPicker
          },
          update = { view ->
            view.value = if(selectedWaterUnit == Units.ML) 0 else 1
          }
        )
      }
    }
  }
}