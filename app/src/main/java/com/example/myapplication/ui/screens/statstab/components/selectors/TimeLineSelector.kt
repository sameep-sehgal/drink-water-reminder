package com.example.myapplication.ui.screens.statstab.components.selectors

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.utils.Statistics

@Composable
fun TimeLineSelector(
  selectedStatsTimeLine: String
) {
  if(selectedStatsTimeLine == Statistics.WEEKLY){
    WeekSelector()
  }
  if(selectedStatsTimeLine == Statistics.MONTHLY){
    MonthSelector()
  }
  if(selectedStatsTimeLine == Statistics.YEARLY){
    YearSelector()
  }
}
