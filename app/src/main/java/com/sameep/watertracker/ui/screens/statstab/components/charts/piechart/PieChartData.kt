package com.sameep.watertracker.ui.screens.statstab.components.charts.piechart

import androidx.compose.ui.graphics.Color

data class PieChartData(
  val slices: List<Slice>
){
  internal val totalSize: Float
    get() {
      var total = 0f
      slices.forEach { total += it.value }
      return total
    }

  data class Slice(
    var value: Float,
    var color: Color,
    val name: String
  )
}
