package com.sameep.watertracker.utils

import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartData

object Statistics {

  //TimeLines for statistics
  const val WEEKLY = "Weekly"
  const val MONTHLY = "Monthly"

  fun calculateDrinkFrequency(
    waterRecordsCount:Int,
    drinkLogsCount:Int
  ): Int {
    if(waterRecordsCount == 0) return 0
    return drinkLogsCount/waterRecordsCount
  }

  fun calculateAverage(
    waterRecordsList: List<DailyWaterRecord>,
    daysCount: Int
  ) : Int {
    if(daysCount == 0) return 0

    var sum = 0
    for(record in waterRecordsList) {
      sum += record.currWaterAmount
    }

    return sum/daysCount
  }

  fun calculateAverageCompletion(
    waterRecordsList: List<DailyWaterRecord>
  ) : Int {
    var waterSum = 0
    var goalSum = 0
    for(record in waterRecordsList) {
      waterSum += record.currWaterAmount
      goalSum += record.goal
    }

    if(goalSum == 0) return 0

    return (waterSum*100)/goalSum
  }

  fun createWaterRecordHashMap(
    waterRecordsList:List<DailyWaterRecord>,
    incrementGoalCompletedDaysCount:() -> Unit
  ): HashMap<String, Float> {
    val dateRecordMapper = hashMapOf<String, Float>()
    for(waterRecord in waterRecordsList) {
      val value = ((waterRecord.currWaterAmount*100).toFloat()/waterRecord.goal)
      if(waterRecord.goal != 0){
        if(value >= 100f) {
          dateRecordMapper[waterRecord.date] = 100f
          incrementGoalCompletedDaysCount()
        }
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
    selectedStatsTimeLine: String,
    incrementDailyWaterRecordsCount:() -> Unit,
    todaysDate: String,
    firstWaterDataDate: String
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
          else {
            val date = currDate.split("-")[2].toInt()
            if(date%2 == 0) date.toString()
            else ""
          }
        )
      )
      if(currDate <= todaysDate && currDate >= firstWaterDataDate)
        incrementDailyWaterRecordsCount()
      currDate = DateString.getPrevDate(currDate)
    }

    return bars
  }

  fun createDropDownOptions(): List<HashMap<String, Any?>> {
    return listOf(
      hashMapOf("text" to "$WEEKLY   ", "value" to WEEKLY),
      hashMapOf("text" to "$MONTHLY ", "value" to MONTHLY),
    )
  }
}