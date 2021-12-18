package com.example.myapplication.ui.screens.collectuserdata.components

import android.R.color
import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.utils.Units
import java.lang.reflect.Field


@Composable
fun GetWaterUnit(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit
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
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
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
            view.value = if(selectedWaterUnit == Units.ML) 0 else 1
          }
        )
      }
    }

    LinearProgressIndicator(currentScreen/6f)

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Button(
        onClick = {
          setCurrentScreen(currentScreen - 1)
        },
        shape = CircleShape
      ) {
        Text(
          text = "Back",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 21.sp
        )
      }
      Button(
        onClick = {
          preferenceDataStoreViewModel.setWaterUnit(selectedWaterUnit)
          setCurrentScreen(currentScreen + 1)
        },
        shape = CircleShape
      ) {
        Text(
          fontSize = 21.sp,
          text = "Next",
          fontWeight = FontWeight.ExtraBold
        )
      }
    }
  }
}