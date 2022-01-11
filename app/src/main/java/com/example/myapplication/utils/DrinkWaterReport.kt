package com.example.myapplication.utils

object DrinkWaterReport {

  fun calculateDrinkFrequency(
    waterRecordsCount:Int,
    drinkLogsCount:Int
  ): Int {
    if(waterRecordsCount == 0) return 0
    return drinkLogsCount/waterRecordsCount
  }

  fun calculateStreak(): Int {
    //TODO
    return 3
  }

  fun calculateWeeklyAverage() : Int {
    //TODO
    return 2000
  }

  fun calculateMonthlyAverage() : Int {
    //TODO
    return 2000
  }

  fun calculateAverageCompletion() : Int {
    //TODO
    return 76
  }
}