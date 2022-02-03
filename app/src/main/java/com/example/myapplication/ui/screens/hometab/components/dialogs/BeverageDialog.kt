package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.screens.hometab.components.BeverageCard

@Composable
fun BeverageDialog(
  setShowBeverageDialog:(Boolean)->Unit,
  onConfirmButtonClick: () -> Unit,
  setSelectedBeverage: (String) -> Unit,
  selectedBeverage: String,
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val title = "Switch Beverage"
  val beverageList = roomDatabaseViewModel.beverageList.collectAsState()

  LaunchedEffect(key1 = true) {
    roomDatabaseViewModel.getAllBeverages()
  }

  ShowDialog(
    title = title,
    content = {
      BeverageDialogContent(
        beverageList = beverageList.value,
        setSelectedBeverage = setSelectedBeverage,
        selectedBeverage = selectedBeverage,
        roomDatabaseViewModel = roomDatabaseViewModel
      )
    },
    setShowDialog = setShowBeverageDialog,
    onConfirmButtonClick = onConfirmButtonClick
  )
}

@Composable
fun BeverageDialogContent(
  beverageList:List<Beverage>,
  setSelectedBeverage: (String) -> Unit,
  selectedBeverage:String,
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val scrollState = rememberScrollState()
  val (showAddBeverageDialog, setShowAddBeverageDialog) =  remember { mutableStateOf(false) }

  Text("")
  Column(
    modifier = Modifier.fillMaxHeight(0.85f)
  ) {
    Column(
      modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .weight(1f)
    ) {
      var i = 0
      var j:Int
      while(i < beverageList.size) {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          j = 0
          while(j < 3 && i < beverageList.size) {
            val currBeverage = beverageList[i]
            BeverageCard(
              beverage = currBeverage,
              selected = selectedBeverage == currBeverage.name,
              onClick = { setSelectedBeverage(currBeverage.name) },
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
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { setShowAddBeverageDialog(true) },
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "+",
        fontSize = 30.sp
      )
    }
  }

  if(showAddBeverageDialog){
    AddBeverageDialog(
      roomDatabaseViewModel = roomDatabaseViewModel,
      setShowBeverageDialog = setShowAddBeverageDialog
    )
  }
}