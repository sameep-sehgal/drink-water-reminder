package com.example.myapplication.ui.screens.historytab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.historytab.components.DataGraph
import com.example.myapplication.ui.screens.hometab.components.buttons.BackToTopButton
import com.example.myapplication.ui.screens.hometab.components.buttons.CustomAddWaterButton

@Composable
fun HistoryTab(
  roomDatabaseViewModel: RoomDatabaseViewModel
){
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()

  Scaffold(
    floatingActionButton = {
      Column(horizontalAlignment = Alignment.CenterHorizontally){
        BackToTopButton(scrollState)
        CustomAddWaterButton(setShowCustomAddWaterDialog)
      }
    },
    floatingActionButtonPosition = FabPosition.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ){
      DataGraph(roomDatabaseViewModel)
    }
  }
}