package com.sameep.watertracker.ui.screens.remindertab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.R
import com.sameep.watertracker.ui.components.ShowDialog

@Composable
fun DozeModeFixDialog(
  setShowDozeModeFixDialog: (Boolean) -> Unit
) {
  ShowDialog(
    title = "Doze Mode Fix",
    content = {
      Column {
        Text(
          text = "1. Open your Device Settings and tap Battery\n",
          fontSize = 12.sp
        )
        Text(
          text = "2. Look for Battery Optimization. (Tap the more button(Three dots) on the action bar at top right)\n",
          fontSize = 12.sp
        )
        Text(
          text = "3. All apps list will appear. Look for ${stringResource(id = R.string.app_name)} and choose the Don't optimize option.\n",
          fontSize = 12.sp
        )
        Text(
          text = "4. Click on Reset Notification in Reminder Settings.",
          fontSize = 12.sp
        )
      }
    },
    setShowDialog = setShowDozeModeFixDialog,
    onConfirmButtonClick = {},
    showConfirmButton = false
  )
}