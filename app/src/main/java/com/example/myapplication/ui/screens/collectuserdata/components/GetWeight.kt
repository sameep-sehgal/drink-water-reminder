package com.example.myapplication.ui.screens.collectuserdata.components

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.utils.Units
import com.example.myapplication.utils.Weight

@Composable
fun GetWeight(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  var selectedWeight by  remember { mutableStateOf(65) }
  fun setSelectedWeight(weight:Int) {
    selectedWeight = weight
  }
  var selectedWeightUnit by  remember { mutableStateOf(Units.KG) }
  fun setSelectedWeightUnit(unit:String) {
    selectedWeightUnit = unit
  }

  val unitPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    Log.d("TAG", "GetWeight: After -> $selectedWeight")
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
    Log.d("TAG", "GetWeight: $selectedWeight")
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ){
    Text(
      text = "Select Weight",
      fontWeight = FontWeight.Bold
    )
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.weight_measuring_machine),
        contentDescription = "Weight Measuring Machine"
      )
      Row(
        modifier = Modifier.padding(24.dp,8.dp),
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
            view.value = if(selectedWeightUnit == Units.KG) 0 else 1
          }
        )
      }
    }
  }
}