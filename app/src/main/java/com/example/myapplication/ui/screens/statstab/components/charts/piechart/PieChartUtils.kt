package com.example.myapplication.ui.screens.statstab.components.charts.piechart

import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.models.DrinkLogs

object PieChartUtils {
  fun calculateAngle(
    sliceLength: Float,
    totalLength: Float,
    progress: Float
  ): Float {
    return 360.0f * (sliceLength * progress) / totalLength
  }

  private const val MAX_PIES = 8
  const val OTHERS_PIE_NAME = "Others"

  private val COLORS = listOf(
    Color(0xFF45b3e0),
    Color(0xFF00bcd8),
    Color(0xFF00c3c0),
    Color(0xFF00c79b),
    Color(0xFF55c86e),
    Color(0xFF91c33e),
    Color(0xFFc9b906),
    Color(0xFFffa600),
  )

  private fun getColor(index:Int): Color {
    if(index >= MAX_PIES) return Color.Transparent
    return COLORS[index]
  }

  fun getBeverageData(
    drinkLogsList: List<DrinkLogs>
  ): List<MutableList<PieChartData.Slice>> {
    val data = HashMap<String, PieChartData.Slice>()
    val temp = mutableListOf<PieChartData.Slice>()
    val pieChartData = mutableListOf<PieChartData.Slice>()
    val otherBeverageList = mutableListOf<PieChartData.Slice>()

    for(drinkLog in drinkLogsList) {
      val curr = data[drinkLog.beverage]
      if(curr != null){
        curr.value += drinkLog.amount
      }else{
        data[drinkLog.beverage] =
          PieChartData.Slice(
            value = drinkLog.amount.toFloat(),
            color = Color.Red,
            name = drinkLog.beverage
          )
      }
    }
    for((_, value) in data) {
      temp.add(value)
    }
    temp.sortByDescending { it.value }
    temp.forEachIndexed { index, value ->
      if(index < MAX_PIES - 1) {
        value.color = PieChartUtils.getColor(index)
        pieChartData.add(value)
      }
      if(index == MAX_PIES - 1){
        val other = PieChartData.Slice(value.value, getColor(index), OTHERS_PIE_NAME)
        pieChartData.add(other)
        otherBeverageList.add(value)
      }
      if(index > MAX_PIES - 1) {
        pieChartData[MAX_PIES - 1].value += value.value
        otherBeverageList.add(value)
      }
    }

    return listOf(pieChartData, otherBeverageList)
  }
}