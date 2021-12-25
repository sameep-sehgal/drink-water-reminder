package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun EditDrinkLogDialog(
  drinkLog:DrinkLogs,
  setShowEditDrinkLogDialog:(Boolean)->Unit
){
  val title = "Edit Record"
  ShowDialog(
    title = title,
    content = { EditDrinkLogDialogContent(drinkLog) },
    setShowDialog = setShowEditDrinkLogDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun EditDrinkLogDialogContent(
  drinkLog:DrinkLogs
){
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(text = "${drinkLog.amount} ${drinkLog.id}")
    Text(text = "You will not be able to ")
  }
}