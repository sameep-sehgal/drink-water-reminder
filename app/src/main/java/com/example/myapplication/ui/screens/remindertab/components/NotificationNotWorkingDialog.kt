package com.example.myapplication.ui.screens.remindertab.components

import androidx.compose.runtime.Composable
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun NotificationNotWorkingDialog(
  setShowDialog: (Boolean) -> Unit
) {
  ShowDialog(
    title = "Notification Not Working?",
    content = { /*TODO*/ },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {},
    showConfirmButton = false
  )
}