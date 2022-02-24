package com.sameep.watertracker.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SwitchNotificationOnDialog(
  setShowDialog: (Boolean) -> Unit,
  context:Context,
) {
  Dialog(
    onDismissRequest = {
      setShowDialog(false)
    },
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .background(MaterialTheme.colors.surface)
        .padding(8.dp)
    ){
      Text(
        text = "Please Allow Notifications ⚠️\n",
        fontSize = 20.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text = "Seems like you Turned Off Notifications for the app in the settings\uD83E\uDD14",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text = "We will not be able to Remind you to Drink Water till you turn them back on\uD83E\uDD7A\n",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
      )
      Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
      ) {
        Button(onClick = {
          val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS )
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          val uri = Uri.fromParts("package", context.packageName, null)
          settingsIntent.data = uri
          context.startActivity(settingsIntent)
        }) {
          Text(text = "Go to Settings")
        }
        Button(onClick = { setShowDialog(false) }) {
          Text(text = "Dismiss")
        }
      }
    }
  }
}