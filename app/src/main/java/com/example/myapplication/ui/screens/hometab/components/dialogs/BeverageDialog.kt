package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.screens.hometab.components.BeverageCard

@Composable
fun BeverageDialog(
  setShowBeverageDialog:(Boolean)->Unit,
  onConfirmButtonClick: () -> Unit,
  beverageList:List<Beverage>
) {
  val title = "Switch Beverage"
  ShowDialog(
    title = title,
    content = { BeverageDialogContent(beverageList) },
    setShowDialog = setShowBeverageDialog,
    onConfirmButtonClick = onConfirmButtonClick
  )
}

@Composable
fun BeverageDialogContent(
  beverageList:List<Beverage>
) {
  var selectedDrink by remember { mutableStateOf(0) }
  val scrollState = rememberScrollState()

  Text("")
  Column(
    modifier = Modifier
      .background(MaterialTheme.colors.background)
      .verticalScroll(scrollState)
  ) {
    var i = 0
    var j:Int
    while(i < beverageList.size) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        j = 0
        while(j < 3) {
          val index = i
          val beverage = beverageList[i]
          BeverageCard(
            beverage = beverage,
            selected = selectedDrink == index,
            onClick = { selectedDrink = index },
            modifier = Modifier
              .weight(1f)
              .padding(4.dp)
              .height(96.dp)
          )
          i++
          j++
        }
      }
    }
  }

}