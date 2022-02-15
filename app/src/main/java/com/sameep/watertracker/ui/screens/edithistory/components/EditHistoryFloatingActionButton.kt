package com.sameep.watertracker.ui.screens.edithistory.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.sameep.watertracker.ui.screens.hometab.components.buttons.BackToTopButton
import com.sameep.watertracker.ui.screens.hometab.components.buttons.CustomAddWaterButton

@Composable
fun EditHistoryFloatingActionButton(
  scrollState: ScrollState,
  setShowCustomAddWaterDialog: (Boolean) -> Unit
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally){
    BackToTopButton(scrollState)
    CustomAddWaterButton(setShowCustomAddWaterDialog = setShowCustomAddWaterDialog)
  }
}