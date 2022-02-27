package com.sameep.watertracker.ui.components.dialogs

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sameep.watertracker.utils.AutoStartHelper
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.R


@Composable
fun SwitchAutoStartAppOnDialog(
  setShowDialog: (Boolean) -> Unit
) {
  val context = LocalContext.current
  val isIntentAvailable = AutoStartHelper.getInstance().isAutoStartPermissionAvailable(context = context)
  val (doneButtonEnabled, setDoneButtonEnabled) =  remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = {}
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface)
        .padding(12.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ){
      Text(
        text = "Please Allow App to Auto-Start ⚠",
        fontSize = 17.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "\nThis is required for the reminder service to function properly, else the service will stop when device restarts\n",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text = "Click on the button below &",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text =
        if(isIntentAvailable)
          "Search for ${context.getString(R.string.app_name)} in the list of apps and switch it on"
        else
          "Look for 'Auto-Start' or 'Auto-Launch' Setting and switch it on",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
      )

      Button(
        onClick = {
          if(isIntentAvailable)
            AutoStartHelper.getInstance().getAutoStartPermission(context = context)
          else{
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.parse("package:" + context.packageName)
            intent.data = uri
            context.startActivity(intent)
          }
          setDoneButtonEnabled(true)
        },
        modifier = Modifier.padding(12.dp)
      ) {
        Text(
          text = "Go to Settings"
        )
      }


      if(!isIntentAvailable) {
        Text(
          text = "NOTE: If not visible directly, Please search in 'Battery' related settings of the app. Ignore if no such setting exists for your device.",
          fontSize = 11.sp,
          textAlign = TextAlign.Center
        )
      }else {
        Text(
          text = "NOTE: Ignore if no such setting exists for your device.",
          fontSize = 11.sp,
          textAlign = TextAlign.Center
        )
      }

      Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
      ){
        Button(
          onClick = { setShowDialog(false) },
          colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = MaterialTheme.colors.onPrimary
          )
        ) {
          Text(text = "Dismiss")
        }
        Button(
          onClick = { setShowDialog(false) },
          enabled = doneButtonEnabled
        ) {
          Text(text = "Done")
        }
      }
    }
  }
}