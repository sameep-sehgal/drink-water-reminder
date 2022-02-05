package com.example.myapplication.ui.screens.statstab.components.renderpiechart

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartUtils
import com.example.myapplication.ui.screens.statstab.components.renderpiechart.popups.PieChartBeverageDataPopup
import com.example.myapplication.ui.screens.statstab.components.renderpiechart.popups.PieChartOtherBeveragesDataPopup

@Composable
fun PieChartBeverageListButton(
  pieChartData: List<PieChartData.Slice>,
  it: Int,
  waterUnit: String,
  beverageToShow: Int,
  setBeverageToShow: (Int) -> Unit,
  showBeverageDetails: Boolean,
  setShowBeverageDetail: (Boolean) -> Unit,
  otherBeverageList: List<PieChartData.Slice>
) {
  Button(
    onClick = {
      setBeverageToShow(it)
      setShowBeverageDetail(!showBeverageDetails)
    },
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
    if(beverageToShow == it) {
      if(pieChartData[it].name === PieChartUtils.OTHERS_PIE_NAME){
        PieChartOtherBeveragesDataPopup(
          waterUnit = waterUnit,
          showBeverageDetails = showBeverageDetails,
          setShowBeverageDetail = setShowBeverageDetail,
          otherBeverageList = otherBeverageList
        )
      }else {
        PieChartBeverageDataPopup(
          pieChartData = pieChartData,
          it = it,
          waterUnit = waterUnit,
          showBeverageDetails = showBeverageDetails,
          setShowBeverageDetail = setShowBeverageDetail
        )
      }
    }
  }
}