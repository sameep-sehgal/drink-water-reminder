package com.example.myapplication.ui.screens.collectuserdata.components.screens

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
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  weight:Int,
  weightUnit: String
) {
  fun setSelectedWeight(value:Int) {
    preferenceDataStoreViewModel.setWeight(value)
  }
  fun setSelectedWeightUnit(unit:String) {
    preferenceDataStoreViewModel.setWeightUnit(unit)
  }

  val unitPickerChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    if (newVal == 0) {
      setSelectedWeightUnit(Units.KG)
      setSelectedWeight(Weight.lbToKg(weight))
    }
    else {
      setSelectedWeightUnit(Units.LB)
      setSelectedWeight(Weight.kgToLb(weight))
    }
    Log.d("TAG", "GetWeight: Before -> $weight $weightUnit")
  }
  val weightPickerChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    setSelectedWeight(newVal)
  }

  Text(
    modifier = Modifier.padding(0.dp,16.dp),
    text = "Select Weight",
    fontWeight = FontWeight.Bold
  )
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(4.dp, 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      modifier = Modifier.weight(1f),
      painter = painterResource(id = R.drawable.weight_measuring_machine),
      contentDescription = "Weight Measuring Machine"
    )
    Row(
      modifier = Modifier.weight(1f),
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
          view.value = weight //To attach state variable
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
          view.value = if(weightUnit == Units.KG) 0 else 1
        }
      )
    }
  }
}