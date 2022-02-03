package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.ui.components.IconSelector
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.Beverages

@Composable
fun AddBeverageDialog(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  setShowBeverageDialog:(Boolean)->Unit,
) {
  var beverageName by remember{ mutableStateOf("") }
  val setBeverageName = {name:String -> beverageName = name}
  var beverageIcon by remember{ mutableStateOf(0) }
  val setBeverageIcon = {icon:Int -> beverageIcon = icon}

  val title = "Add Beverage"
  ShowDialog(
    title = title,
    content = {
      AddBeverageDialogContent(
        beverageName = beverageName,
        setBeverageName = setBeverageName,
        beverageIcon = beverageIcon,
        setBeverageIcon = setBeverageIcon
      )
    },
    setShowDialog = setShowBeverageDialog,
    onConfirmButtonClick = {
      roomDatabaseViewModel.insertBeverage(
        Beverage(
          name = beverageName,
          icon = beverageIcon
        )
      )
    },
    showConfirmButton = beverageName != ""
  )

}
@Composable
fun AddBeverageDialogContent(
  beverageName:String,
  setBeverageName: (String) -> Unit,
  beverageIcon:Int,
  setBeverageIcon: (Int) -> Unit
) {
  Text(text = "")
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TextField(
      value = beverageName,
      onValueChange = { setBeverageName(it) },
      maxLines = 1,
      label = {
        Text(text = "Beverage Name")
      },
      modifier = Modifier.padding(8.dp,16.dp)
    )

    Text(
      text = "Select Icon",
      modifier = Modifier.padding(8.dp)
    )

    IconSelector(
      icons = Beverages.IMAGE_MAPPER,
      setSelectedIcon = setBeverageIcon,
      selectedIcon = beverageIcon
    )
  }
}
