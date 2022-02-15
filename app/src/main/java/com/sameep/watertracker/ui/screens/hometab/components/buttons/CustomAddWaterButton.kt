package com.sameep.watertracker.ui.screens.hometab.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sameep.watertracker.R

@Composable
fun CustomAddWaterButton(
  modifier: Modifier = Modifier,
  setShowCustomAddWaterDialog:(Boolean) -> Unit
) {
  IconButton(
    onClick = {
      setShowCustomAddWaterDialog(true)
    }
  ) {
    Image(
      painter = painterResource(id = R.drawable.add_button),
      contentDescription = "Add Custom Water",
      modifier = modifier
    )
  }
}