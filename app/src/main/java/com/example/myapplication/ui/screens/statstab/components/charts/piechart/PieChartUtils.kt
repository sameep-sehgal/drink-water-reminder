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
    Color(0xFF98a0f8),
    Color(0xFFce8eea),
    Color(0xFFf979c8),
    Color(0xFFff6b98),
    Color(0xFFff6f61),
    Color(0xFFff8323),
    Color(0xFFff5600),
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
    val totalAmountDrunk = mutableListOf(PieChartData.Slice(0f, Color.Red, "Total Amount Drunk"))

    for(drinkLog in drinkLogsList) {
      val curr = data[drinkLog.beverage]
      totalAmountDrunk[0].value += drinkLog.amount
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
        value.color = getColor(index)
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

    return listOf(pieChartData, otherBeverageList, totalAmountDrunk)
  }
}