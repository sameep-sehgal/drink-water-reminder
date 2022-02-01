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
  waterGoalPicker:Boolean = false
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
      if(waterGoalPicker) {
        numberPicker.maxValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MAX_WATER_LEVEL_IN_ML/10 else RecommendedWaterIntake.MAX_WATER_LEVEL_IN_OZ
        numberPicker.minValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MIN_WATER_LEVEL_IN_ML/10 else RecommendedWaterIntake.MIN_WATER_LEVEL_IN_OZ
        numberPicker.displayedValues = RecommendedWaterIntake.valuesForWaterGoalNumberPicker(waterUnit).toTypedArray()
      }else{
        numberPicker.maxValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MAX_WATER_LEVEL_IN_ML_FOR_LOG/10 else RecommendedWaterIntake.MAX_WATER_LEVEL_IN_OZ_FOR_LOG
        numberPicker.minValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MIN_WATER_LEVEL_IN_ML_FOR_LOG/10 else RecommendedWaterIntake.MIN_WATER_LEVEL_IN_OZ_FOR_LOG
        numberPicker.displayedValues = RecommendedWaterIntake.valuesForWaterLogNumberPicker(waterUnit).toTypedArray()
      }
      numberPicker.setOnValueChangedListener(numberPickerChangeListener)
      numberPicker
    },
    update = {
      it.value = if(waterUnit == Units.ML) amount/10 else amount
    }
  )
}