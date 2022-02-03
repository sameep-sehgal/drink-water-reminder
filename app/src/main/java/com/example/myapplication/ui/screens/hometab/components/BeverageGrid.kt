package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.Beverage

@Composable
fun BeverageGrid(
  beverageList: List<Beverage>,
  selectedBeverage:String,
  setSelectedBeverage: (String) -> Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  var i = 0
  var j:Int
  Column(
    modifier = modifier
      .background(MaterialTheme.colors.background)
      .fillMaxWidth()
      .verticalScroll(scrollState)
  ) {
    while (i < beverageList.size) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        j = 0
        while (j < 3 && i < beverageList.size) {
          val currBeverage = beverageList[i]
          BeverageCard(
            beverage = currBeverage,
            selected = selectedBeverage == currBeverage.name,
            onClick = { setSelectedBeverage(currBeverage.name) },
            modifier = Modifier
              .weight(1f)
              .padding(4.dp)
              .height(96.dp),
            roomDatabaseViewModel = roomDatabaseViewModel
          )
          i++
          j++
        }
      }
    }
  }
}