package com.sameep.watertracker.ui.screens.statstab.components.renderpiechart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.components.NextButton
import com.sameep.watertracker.ui.components.PreviousButton
import com.sameep.watertracker.ui.screens.statstab.components.charts.piechart.PieChartData
import kotlinx.coroutines.launch

@Composable
fun PieChartBeverageList(
  pieChartData: List<PieChartData.Slice>,
  otherBeverageList: List<PieChartData.Slice>,
  waterUnit: String
) {
  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()
  var showBeverageDetails by remember { mutableStateOf(false) }
  var beverageToShow by remember { mutableStateOf(0) }
  val setBeverageToShow = { value:Int -> beverageToShow = value }
  val setShowBeverageDetail = {value:Boolean -> showBeverageDetails = value}

  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    PreviousButton(
      onClick = {
        scope.launch { listState.animateScrollToItem(listState.firstVisibleItemIndex-1) }
      },
      fontSize = 10.sp
    )
    LazyRow(
      state = listState,
      modifier = Modifier.weight(1f)
    ) {
      items(count = pieChartData.size) {
        PieChartBeverageListButton(
          pieChartData = pieChartData,
          it = it,
          waterUnit = waterUnit,
          beverageToShow = beverageToShow,
          setBeverageToShow = setBeverageToShow,
          showBeverageDetails = showBeverageDetails,
          setShowBeverageDetail = setShowBeverageDetail,
          otherBeverageList = otherBeverageList
        )
        Spacer(modifier = Modifier.size(8.dp))
      }
    }
    NextButton(
      onClick = {
        scope.launch { listState.animateScrollToItem(listState.firstVisibleItemIndex+1) }
      },
      fontSize = 10.sp
    )
  }

}