package com.example.myapplication.ui.components

import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Units

@Composable
fun WaterQuantityPicker(
  waterUnit:String,
  amount:Int,
  setAmount:(Int) -> Unit,
) {
  val numberPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    if(waterUnit == Units.ML){
      setAmount(newVal*10)
    }else {
      setAmount(newVal)
    }
  }

  AndroidView(
    factory = { context ->
      val numberPicker = NumberPicker(context)
      numberPicker.maxValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MAX_WATER_LEVEL_IN_ML_FOR_LOG/10 else RecommendedWaterIntake.MAX_WATER_LEVEL_IN_OZ_FOR_LOG
      numberPicker.minValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MIN_WATER_LEVEL_IN_ML_FOR_LOG/10 else RecommendedWaterIntake.MIN_WATER_LEVEL_IN_OZ_FOR_LOG
      numberPicker.displayedValues = RecommendedWaterIntake.VALUES_FOR_WATER_LOG_NUMBER_PICKER(waterUnit).toTypedArray()
      numberPicker.setOnValueChangedListener(numberPickerChangeListener)
      numberPicker
    },
    update = {
      it.value = amount/10
    }
  )
//    Text(
//      text = waterUnit,
//      modifier = Modifier.padding(8.dp),
//      color = MaterialTheme.colors.onSurface
//    )
}