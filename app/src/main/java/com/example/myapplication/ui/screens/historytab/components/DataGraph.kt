package com.example.myapplication.ui.screens.historytab.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.myapplication.RoomDatabaseViewModel

@Composable
fun DataGraph(
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val listState = rememberLazyListState()
// Remember a CoroutineScope to be able to launch
  val coroutineScope = rememberCoroutineScope()

  val firstWaterRecord = roomDatabaseViewModel.firstWaterRecord.collectAsState()
  Log.d("TAG", "DataGraph: ${firstWaterRecord.value.date}")
  LazyRow{
    item {
      Text(text = "First item")
    }

    // Add 5 items
    items(5) { index ->
      Text(text = "Item: $index")
    }

    // Add another single item
    item {
      Text(text = "Last item")
    }
  }
}