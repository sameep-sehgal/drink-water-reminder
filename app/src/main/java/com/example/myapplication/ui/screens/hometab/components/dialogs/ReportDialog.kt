package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun ReportDialog(setShowReportDialog:(Boolean)->Unit):Unit{
  val title = "Drink Water Report"
  ShowDialog(
    title = title,
    content = { ReportDialogContent() },
    setShowDialog = setShowReportDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun ReportDialogContent():Unit{
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(text = "Dialog working")
    Text(text = "This is Report Dialog Content")
  }
}