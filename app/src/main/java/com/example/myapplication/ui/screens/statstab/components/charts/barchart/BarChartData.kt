package com.example.myapplication.ui.screens.statstab.components.charts.barchart

import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.AppColorPrimary

data class BarChartData(
  val bars: List<Bar>,
  val padBy: Float = 10f,
  val startAtZero: Boolean = true
){
  val maxBarValue = 100f

  init {
    require(padBy in 0f..100f)
    for(bar in bars) {
      if(bar.value > maxBarValue){
        bar.value = maxBarValue
      }
    }
  }

  data class Bar(
    var value: Float,
    val label: String,
    val color: Color = AppColorPrimary
  )
}
