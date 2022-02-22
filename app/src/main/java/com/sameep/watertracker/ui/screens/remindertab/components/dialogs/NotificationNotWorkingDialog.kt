package com.sameep.watertracker.ui.screens.remindertab.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.R
import com.sameep.watertracker.ui.components.ShowDialog
import com.sameep.watertracker.ui.screens.remindertab.components.dialogs.DozeModeFixDialog
import com.sameep.watertracker.ui.screens.remindertab.components.dialogs.DozeModeLearnMoreDialog

@Composable
fun NotificationNotWorkingDialog(
  setShowDialog: (Boolean) -> Unit
) {
  val (showDozeModeFixDialog, setShowDozeModeFixDialog) =  remember { mutableStateOf(false) }
  val (showDozeModeLearnMoreDialog, setShowDozeModeLearnMoreDialog) =  remember { mutableStateOf(false) }
  ShowDialog(
    title = "Notification Not Working?",
    content = {
      Column {
        Text(
          text = "Fix",
          textDecoration = TextDecoration.Underline,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "1. Click the Reset Reminder setting in the Reminder Settings. This will restart the notification service.\n",
          fontSize = 12.sp
        )
        Text(
          text = "2. Change App's Battery Optimization settings to \"Don't Optimize\".",
          fontSize = 12.sp
        )
        Row {
          Text(
            text = "See How",
            color = MaterialTheme.colors.primary,
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
              .padding(8.dp, 0.dp)
              .clickable {
                setShowDozeModeFixDialog(true)
              }
          )
          Text(
            text = "Learn More",
            color = MaterialTheme.colors.primary,
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
              .padding(8.dp, 0.dp)
              .clickable {
                setShowDozeModeLearnMoreDialog(true)
              }
          )
        }
        Text(
          text = "\n3. Some Power-saving and Cleaner apps on your device might try to interfere with the notifications. " +
                  "Please check the settings of these apps and make sure they do not stop ${stringResource(
                    id = R.string.app_name
                  )}.\n",
          fontSize = 12.sp
        )
        Text(
          text = "4. Try restarting your device.",
          fontSize = 12.sp
        )
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {},
    showConfirmButton = false
  )

  if(showDozeModeFixDialog) {
    DozeModeFixDialog(setShowDozeModeFixDialog = setShowDozeModeFixDialog)
  }
  if(showDozeModeLearnMoreDialog) {
    DozeModeLearnMoreDialog(setShowDialog = setShowDozeModeLearnMoreDialog)
  }
}