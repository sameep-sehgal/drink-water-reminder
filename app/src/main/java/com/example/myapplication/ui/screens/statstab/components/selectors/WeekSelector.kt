package com.example.myapplication.ui.screens.statstab.components.selectors

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.NextButton
import com.example.myapplication.ui.components.PreviousButton
import com.example.myapplication.utils.DateString

@Composable
fun WeekSelector(
  startDate:String,
  endDate:String,
  setStartDate:(String) -> Unit,
  setEndDate:(String) -> Unit,
  firstWaterRecordDate: String
) {
  val todaysDate = DateString.getTodaysDate()
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(1f),
      horizontalArrangement = Arrangement.End
    ) {
      if(startDate>firstWaterRecordDate) {
        PreviousButton(onClick = {
          val newEndDate = DateString.getPrevDate(startDate)
          setStartDate(DateString.getWeekStartDate(newEndDate))
          setEndDate(newEndDate)
        })
      }else{
        Spacer(modifier = Modifier.size(4.dp))
      }
    }

    Spacer(modifier = Modifier.size(16.dp))

    Text(
      text = DateString.getDateIntervalString(
        startDate = startDate,
        endDate = endDate
      ),
      fontSize = 16.sp
    )

    Spacer(modifier = Modifier.size(16.dp))

    Row(
      modifier = Modifier.weight(1f),
      horizontalArrangement = Arrangement.Start
    ) {
      if(endDate < todaysDate) {
        NextButton(onClick = {
          val newStartDate = DateString.getNextDate(endDate)
          setEndDate(DateString.getWeekEndDate(newStartDate))
          setStartDate(newStartDate)
        })
      }else{
        Spacer(modifier = Modifier.size(4.dp))
      }
    }
  }
}