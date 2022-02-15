package com.sameep.watertracker.ui.screens.statstab.components.renderpiechart.popups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.screens.statstab.components.charts.piechart.PieChartData

@Composable
fun PieChartBeverageDataPopup(
  pieChartData: List<PieChartData.Slice>,
  it: Int,
  waterUnit: String,
  showBeverageDetails: Boolean,
  setShowBeverageDetail: (Boolean) -> Unit
) {
  DropdownMenu(
    expanded = showBeverageDetails,
    onDismissRequest = { setShowBeverageDetail(false) },
    modifier = Modifier.padding(4.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = pieChartData[it].name,
        fontSize = 10.sp
      )
      Spacer(modifier = Modifier.size(2.dp))
      Text(
        text = "${pieChartData[it].value.toInt()}$waterUnit",
        fontSize = 10.sp
      )
    }
  }
}