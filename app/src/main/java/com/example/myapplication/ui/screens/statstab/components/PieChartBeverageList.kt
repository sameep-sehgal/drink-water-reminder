package com.example.myapplication.ui.screens.statstab.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.NextButton
import com.example.myapplication.ui.components.PreviousButton
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData
import kotlinx.coroutines.launch

@Composable
fun PieChartBeverageList(
  pieChartData: List<PieChartData.Slice>,
) {
  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

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
        Button(
          onClick = { /*TODO*/ },
          shape = CircleShape,
          colors = ButtonDefaults.textButtonColors(
            backgroundColor = pieChartData[it].color,
            contentColor = MaterialTheme.colors.onPrimary
          )
        ) {
          Text(
            text = pieChartData[it].name,
            fontSize = 10.sp
          )
        }
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