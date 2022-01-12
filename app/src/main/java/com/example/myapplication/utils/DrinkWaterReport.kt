package com.example.myapplication.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.screens.historytab.components.Bar

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

  fun calculateAverageCompletion() : Int {
    //TODO
    return 76
  }
}