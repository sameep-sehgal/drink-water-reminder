package com.sameep.watertracker.ui.screens.hometab.components.dialogs

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.Beverage
import com.sameep.watertracker.ui.screens.hometab.components.BeverageGrid
import com.sameep.watertracker.utils.Beverages

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
      setShowAddBeverageDialog = setShowAddBeverageDialog,
      beverageListSize = beverageList.size
    )
  }

  if(showAddBeverageDialog){
    AddBeverageDialog(
      roomDatabaseViewModel = roomDatabaseViewModel,
      setShowBeverageDialog = setShowAddBeverageDialog,
      beverageListSize = beverageList.size
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
  setShowAddBeverageDialog: (Boolean) -> Unit,
  beverageListSize:Int
) {
  val context = LocalContext.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        if(beverageListSize >= Beverages.MAX_ALLOWED_COUNT) {
          Toast.makeText(
            context,
            "Maximum ${Beverages.MAX_ALLOWED_COUNT} beverages can be added. Delete existing ones to add more.",
            Toast.LENGTH_SHORT
          ).show()
        }else setShowAddBeverageDialog(true)
      }
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