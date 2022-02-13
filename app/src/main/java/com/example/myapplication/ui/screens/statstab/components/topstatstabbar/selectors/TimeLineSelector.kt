package com.example.myapplication.ui.screens.statstab.components.topstatstabbar.selectors

import androidx.compose.runtime.Composable
import com.example.myapplication.utils.Statistics

@Composable
fun TimeLineSelector(
  selectedStatsTimeLine: String,
  startDate:String,
  endDate:String,
  setStartDate:(String) -> Unit,
  setEndDate:(String) -> Unit,
  firstWaterRecordDate:String
) {
  if(selectedStatsTimeLine == Statistics.WEEKLY){
    WeekSelector(
      startDate = startDate,
      endDate = endDate,
      setStartDate = setStartDate,
      setEndDate = setEndDate,
      firstWaterRecordDate = firstWaterRecordDate
    )
  }
  if(selectedStatsTimeLine == Statistics.MONTHLY){
    MonthSelector(
      startDate = startDate,
      endDate = endDate,
      setStartDate = setStartDate,
      setEndDate = setEndDate,
      firstWaterRecordDate = firstWaterRecordDate
    )
  }
}