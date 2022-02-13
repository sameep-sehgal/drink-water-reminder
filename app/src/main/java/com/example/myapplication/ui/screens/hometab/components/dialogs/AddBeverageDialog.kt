package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
  beverageListSize:Int
) {
  val context = LocalContext.current
  var beverageName by remember{ mutableStateOf("") }
  val setBeverageName = { name:String ->
    if(name.length > Beverages.MAX_BEVERAGE_NAME_LENGTH){
      Toast.makeText(context, "Beverage Name cannot exceed " +
              "${Beverages.MAX_BEVERAGE_NAME_LENGTH} " +
              "characters", Toast.LENGTH_SHORT
      ).show()
    }else {
      beverageName = name
    }
  }
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
          icon = beverageIcon,
          importance = beverageListSize
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
      modifier = Modifier.padding(8.dp,16.dp),
      keyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Words,
        keyboardType = KeyboardType.Text,
        autoCorrect = true,
        imeAction = ImeAction.Done
      ),
      singleLine = true,
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
