package com.sameep.watertracker.ui.screens.remindertab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.components.ShowDialog

@Composable
fun DozeModeLearnMoreDialog(
  setShowDialog: (Boolean) -> Unit
) {
  ShowDialog(
    title = "Doze Mode",
    content = {
      Column {
        Text(
          text = "This can be caused by Doze Mode feature in Android 6 and higher.\n",
          fontSize = 12.sp
        )
        Text(
          text = "This is a battery optimization feature which may stop notifications from showing when device is left idle for a long time.\n",
          fontSize = 12.sp
        )
        Text(
          text = "Reminder Service is killed and needs to be restarted. To avoid this, change the battery optimization settings of the app to \"Don't Optimize\"",
          fontSize = 12.sp
        )
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {},
    showConfirmButton = false
  )
}