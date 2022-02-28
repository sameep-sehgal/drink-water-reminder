package com.sameep.watertracker.ui.screens.remindertab.components.dialogs

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.R
import com.sameep.watertracker.ui.components.dialogs.ShowDialog
import com.sameep.watertracker.ui.components.dialogs.SwitchAutoStartAppOnDialog
import com.sameep.watertracker.utils.ReminderReceiverUtil

@Composable
fun ReminderNotWorkingDialog(
  setShowDialog: (Boolean) -> Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  reminderGap:Int,
  context:Context,
  reminderPeriodStart:String
) {
  val (showDozeModeFixDialog, setShowDozeModeFixDialog) =  remember { mutableStateOf(false) }
  val (showAutoLaunchDialog, setShowAutoLaunchDialog) =  remember { mutableStateOf(false) }
  val (showDozeModeLearnMoreDialog, setShowDozeModeLearnMoreDialog) =  remember { mutableStateOf(false) }
  ShowDialog(
    title = "Reminder Not Working?",
    content = {
      Column {
        Text(
          text = "Fix",
          textDecoration = TextDecoration.Underline,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "1. Allow App to 'Auto-Start' or 'Auto-Launch'.",
          fontSize = 12.sp
        )
        Row{
          Text(
            text = "See How",
            color = MaterialTheme.colors.primary,
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
              .padding(8.dp, 0.dp)
              .clickable {
                setShowAutoLaunchDialog(true)
              }
          )
        }
        Text(
          text = "\n2. Change App's Battery Optimization settings to \"Don't Optimize\".",
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
          text = "Click the Reset Reminder Button below to restart the notification service after trying the above fixes.\n",
          fontSize = 12.sp
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {
          Button(onClick = {
            ReminderReceiverUtil.setBothReminderAndAlarm(
              reminderGap =  reminderGap,
              context = context,
              reminderPeriodStartTime = reminderPeriodStart
            )
            Toast.makeText(context, "Reminder is Reset", Toast.LENGTH_SHORT).show()
          }) {
            Text(text = "Reset Reminder")
          }
        }
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
  if(showAutoLaunchDialog) {
    SwitchAutoStartAppOnDialog(
      preferenceDataStoreViewModel = preferenceDataStoreViewModel,
      setShowDialog = setShowAutoLaunchDialog
    )
  }
}