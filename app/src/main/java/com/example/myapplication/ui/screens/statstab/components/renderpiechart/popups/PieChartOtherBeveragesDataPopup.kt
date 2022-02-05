package com.example.myapplication.ui.screens.statstab.components.renderpiechart.popups

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
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData

@Composable
fun PieChartOtherBeveragesDataPopup(
  waterUnit: String,
  showBeverageDetails: Boolean,
  setShowBeverageDetail: (Boolean) -> Unit,
  otherBeverageList: List<PieChartData.Slice>
) {
  DropdownMenu(
    expanded = showBeverageDetails,
    onDismissRequest = { setShowBeverageDetail(false) },
    modifier = Modifier.padding(4.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      otherBeverageList.forEach {
        Text(
          text = "${it.name} - ${it.value.toInt()}$waterUnit",
          fontSize = 10.sp
        )
        Spacer(modifier = Modifier.size(2.dp))
      }
    }
  }
}