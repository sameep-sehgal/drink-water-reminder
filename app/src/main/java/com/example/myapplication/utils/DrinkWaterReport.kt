package com.example.myapplication.utils

import com.example.myapplication.data.models.DailyWaterRecord

object DrinkWaterReport {

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
}