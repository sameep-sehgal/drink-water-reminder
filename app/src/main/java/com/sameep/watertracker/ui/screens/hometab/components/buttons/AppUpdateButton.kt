package com.sameep.watertracker.ui.screens.hometab.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.R

@Composable
fun AppUpdateButton(
  setShowAppUpdateDialog: (Boolean) -> Unit
) {
  IconButton(onClick = {
    setShowAppUpdateDialog(true)
  }) {
    Box(
      modifier = Modifier.size(24.dp)
    ) {
      Icon(
        painter = painterResource(id = R.drawable.phone_update_icon),
        contentDescription = "App Update"
      )
      Image(
        painter = painterResource(id = R.drawable.new_icon),
        contentDescription = "App Update"
      )
    }
  }
}