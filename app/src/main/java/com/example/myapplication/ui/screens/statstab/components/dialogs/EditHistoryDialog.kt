package com.example.myapplication.ui.screens.statstab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun EditHistoryDialog(setShowEditHistoryDialog:(Boolean)->Unit) {
  val title = "Set Today's Beverage Level"
  ShowDialog(
    title = title,
    content = { EditHistoryDialogContent() },
    setShowDialog = setShowEditHistoryDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun EditHistoryDialogContent() {
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(text = "Dialog working")
    Text(text = "This is Beverage Dialog Content")
  }
}