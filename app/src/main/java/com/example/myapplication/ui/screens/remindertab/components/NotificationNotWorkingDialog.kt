package com.example.myapplication.ui.screens.remindertab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.example.myapplication.R
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun NotificationNotWorkingDialog(
  setShowDialog: (Boolean) -> Unit
) {
  val (showDozeModeFixDialog, setShowDozeModeFixDialog) =  remember { mutableStateOf(false) }
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
          text = "1. Click the Reset Notification setting in the Reminder Settings. This will restart the notification service.\n",
          fontSize = 12.sp
        )
        Text(
          text = "2. This can be caused by Doze Mode feature in Android 6 and higher. " +
                  "This is a battery optimization feature which may stop notifications from showing when device is left idle for a long time.",
          fontSize = 12.sp
        )
        Text(
          text = "See fix",
          color = MaterialTheme.colors.primary,
          fontSize = 12.sp,
          textDecoration = TextDecoration.Underline,
          modifier = Modifier
            .padding(8.dp, 0.dp)
            .clickable { setShowDozeModeFixDialog(true) }
        )
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
}