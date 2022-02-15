package com.sameep.watertracker.ui.screens.statstab.components.topstatstabbar.selectors

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.components.NextButton
import com.sameep.watertracker.ui.components.PreviousButton
import com.sameep.watertracker.utils.DateString

@Composable
fun MonthSelector(
  startDate:String,
  endDate:String,
  setStartDate:(String) -> Unit,
  setEndDate:(String) -> Unit,
  firstWaterRecordDate: String
) {
  var startDateList = startDate.split("-")
  var month = DateString.getMonthString(startDateList[1].toInt())
  var year = startDateList[0]
  val todaysDate = DateString.getTodaysDate()

  LaunchedEffect(key1 = startDate, key2 = endDate) {
    startDateList = startDate.split("-")
    month = DateString.getMonthString(startDateList[1].toInt())
    year = startDateList[0]
  }

  Row(
    horizontalArrangement = Arrangement.Center,
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
          setStartDate(DateString.getMonthStartDate(newEndDate))
          setEndDate(newEndDate)
        })
      }else{
        Spacer(modifier = Modifier.size(4.dp))
      }
    }

    Spacer(modifier = Modifier.size(16.dp))

    Text(
      text = "$month $year",
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
          setEndDate(DateString.getMonthEndDate(newStartDate))
          setStartDate(newStartDate)
        })
      }else{
        Spacer(modifier = Modifier.size(4.dp))
      }
    }
  }
}