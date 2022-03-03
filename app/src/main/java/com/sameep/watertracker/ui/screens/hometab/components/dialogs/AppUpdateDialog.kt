package com.sameep.watertracker.ui.screens.hometab.components.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sameep.watertracker.BuildConfig

@Composable
fun AppUpdateDialog(
  setShowDialog: (Boolean) -> Unit,
  context: Context
) {
  Dialog(
    onDismissRequest = { setShowDialog(false) }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface)
    ) {
      Text(
        text = "New Update Available!!",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        fontWeight = FontWeight.Bold
      )
      Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Button(onClick = { setShowDialog(false) }) {
          Text(text = "Dismiss")
        }
        Button(
          onClick = {
            val intent = Intent(
              Intent.ACTION_VIEW,
              Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
            )
            context.startActivity(intent)
            setShowDialog(false)
          }
        ) {
          Text(text = "Update")
        }
      }
    }
  }
}