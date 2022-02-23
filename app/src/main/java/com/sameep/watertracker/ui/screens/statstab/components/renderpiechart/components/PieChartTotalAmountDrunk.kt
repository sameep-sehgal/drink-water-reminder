package com.sameep.watertracker.ui.screens.statstab.components.renderpiechart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun PieChartTotalAmountDrunk(
  totalAmountDrunk:Int,
  waterUnit: String
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = "Total",
      fontSize = 10.sp
    )
    Text(
      text = "$totalAmountDrunk$waterUnit",
      fontSize = 10.sp
    )
  }
}