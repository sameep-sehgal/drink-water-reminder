package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.ui.screens.hometab.components.BeverageGrid

@Composable
fun BeverageDialog(
  setShowBeverageDialog:(Boolean)->Unit,
  setSelectedBeverage: (String) -> Unit,
  selectedBeverage: String,
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val beverageList = roomDatabaseViewModel.beverageList.collectAsState()

  LaunchedEffect(key1 = true) {
    roomDatabaseViewModel.getAllBeverages()
  }

  Dialog(
    onDismissRequest = { setShowBeverageDialog(false) }
  ) {
    BeverageDialogContent(
      beverageList = beverageList.value,
      setSelectedBeverage = setSelectedBeverage,
      selectedBeverage = selectedBeverage,
      roomDatabaseViewModel = roomDatabaseViewModel,
      setShowBeverageDialog = setShowBeverageDialog
    )
  }
}

@Composable
fun BeverageDialogContent(
  beverageList:List<Beverage>,
  setSelectedBeverage: (String) -> Unit,
  selectedBeverage:String,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  setShowBeverageDialog:(Boolean)->Unit,
) {
  val (showAddBeverageDialog, setShowAddBeverageDialog) =  remember { mutableStateOf(false) }

  Column(
    modifier = Modifier.fillMaxHeight(0.85f)
  ) {

    BeverageDialogTitle(setShowBeverageDialog = setShowBeverageDialog)

    BeverageGrid(
      beverageList = beverageList,
      selectedBeverage = selectedBeverage,
      setSelectedBeverage = setSelectedBeverage,
      roomDatabaseViewModel = roomDatabaseViewModel,
      modifier = Modifier.weight(1f)
    )

    AddBeverageDialogOpenButton(
      setShowAddBeverageDialog = setShowAddBeverageDialog
    )
  }

  if(showAddBeverageDialog){
    AddBeverageDialog(
      roomDatabaseViewModel = roomDatabaseViewModel,
      setShowBeverageDialog = setShowAddBeverageDialog
    )
  }
}

@Composable
fun BeverageDialogTitle(
  setShowBeverageDialog: (Boolean) -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .background(MaterialTheme.colors.background)
      .padding(12.dp)
  ) {
    Text(
      text = "Switch Beverage",
      fontSize = 18.sp
    )
    IconButton(
      onClick = { setShowBeverageDialog(false) }
    ) {
      Text(
        text = "x",
        color = Color.Gray,
        fontSize = 18.sp
      )
    }
  }
  Divider(
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun AddBeverageDialogOpenButton(
  setShowAddBeverageDialog: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { setShowAddBeverageDialog(true) }
      .background(MaterialTheme.colors.background),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "+",
      fontSize = 30.sp
    )
  }
}