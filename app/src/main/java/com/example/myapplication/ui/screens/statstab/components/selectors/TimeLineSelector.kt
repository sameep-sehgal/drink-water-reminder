package com.example.myapplication.ui.screens.statstab.components.selectors

import androidx.compose.runtime.Composable
import com.example.myapplication.utils.Statistics

@Composable
fun TimeLineSelector(
  selectedStatsTimeLine: String,
  startDate:String,
  endDate:String,
  setStartDate:(String) -> Unit,
  setEndDate:(String) -> Unit
) {
  if(selectedStatsTimeLine == Statistics.WEEKLY){
    WeekSelector(
      startDate = startDate,
      endDate = endDate,
      setStartDate = setStartDate,
      setEndDate = setEndDate
    )
  }
  if(selectedStatsTimeLine == Statistics.MONTHLY){
    MonthSelector()
  }
}
