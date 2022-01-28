package com.example.myapplication.utils

import android.util.Log
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData

object Statistics {

  //TimeLines for statistics
  const val WEEKLY = "Weekly"
  const val MONTHLY = "Monthly"
  const val YEARLY = "Yearly"

  fun calculateDrinkFrequency(
    waterRecordsCount:Int,
    drinkLogsCount:Int
  ): Int {
    if(waterRecordsCount == 0) return 0
    return drinkLogsCount/waterRecordsCount
  }

  fun calculateAverage(
    waterRecordsList: List<DailyWaterRecord>,
    type:String
  ) : Int {
    var divide = 30
    if(type == "weekly") divide = 7

    var sum = 0
    for(record in waterRecordsList) {
      sum += record.currWaterAmount
    }

    return sum/divide
  }

  fun calculateAverageCompletion(
    completedWaterRecordsCount:Int,
    totalDays:Int
  ) : Int {
    if(totalDays == 0) return 0
    return (completedWaterRecordsCount*100)/totalDays
  }

  fun createWaterRecordHashMap(
    waterRecordsList:List<DailyWaterRecord>
  ): HashMap<String, Float> {
    val dateRecordMapper = hashMapOf<String, Float>()
    for(waterRecord in waterRecordsList) {
      val value = ((waterRecord.currWaterAmount*100).toFloat()/waterRecord.goal)
      if(waterRecord.goal != 0){
        if(value >= 100f)
          dateRecordMapper[waterRecord.date] = 100f
        else
          dateRecordMapper[waterRecord.date] = value
      }
      else dateRecordMapper[waterRecord.date] = 0f
    }

    return dateRecordMapper
  }

  fun createBarChartData(
    startDate:String,
    endDate:String,
    dateRecordMapper: HashMap<String, Float>,
    selectedStatsTimeLine: String
  ): MutableList<BarChartData.Bar> {
    var currDate = endDate
    val bars = MutableList(0) { index ->
      BarChartData.Bar(0f, "$index")
    }
    while(currDate >= startDate) {
      val value = dateRecordMapper[currDate]
      var notNullValue = 0f
      if(value != null) notNullValue = value
      bars.add(
        0,
        BarChartData.Bar(
          value = notNullValue,
          label =
          if(selectedStatsTimeLine == WEEKLY)
            DateString.getDayOfWeek(7 - bars.size)
          else if(selectedStatsTimeLine == MONTHLY) {
            val date = currDate.split("-")[2].toInt()
            if(date%2 == 0) date.toString()
            else ""
          }
          else
            currDate.split("-")[2]
        )
      )
      currDate = DateString.getPrevDate(currDate)
    }

    return bars
  }

  fun createDropDownOptions(): List<HashMap<String, Any?>> {
    return listOf(
      hashMapOf("text" to "$WEEKLY   ", "value" to WEEKLY),
      hashMapOf("text" to "$MONTHLY ", "value" to MONTHLY),
      hashMapOf("text" to "$YEARLY   ", "value" to YEARLY),
    )
  }
}