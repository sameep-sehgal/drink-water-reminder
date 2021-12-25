package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun CustomAddWaterDialog(setShowCustomAddWaterDialog:(Boolean)->Unit){
  val title = "Add Water"
  ShowDialog(
    title = title,
    content = { CustomAddWaterDialogContent() },
    setShowDialog = setShowCustomAddWaterDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun CustomAddWaterDialogContent(){
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(text = "Add Water")
    Text(text = "You will be able to add water here")
  }
}