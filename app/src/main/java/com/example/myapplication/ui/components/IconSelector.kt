package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun IconSelector(
  icons: List<Int>,
  setSelectedIcon: (Int) -> Unit,
  selectedIcon:Int
) {
  val scrollState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    PreviousButton(
      onClick = {
        scope.launch {
          scrollState.animateScrollToItem(scrollState.firstVisibleItemIndex-3)
        }
      },
      fontSize = 12.sp
    )
    LazyRow(
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
      state = scrollState,
      modifier = Modifier.weight(1f)
    ) {
      icons.forEachIndexed { index,icon ->
        item {
          IconButton(
            onClick = { setSelectedIcon(index) },
            modifier = Modifier
              .background(
                color = if (selectedIcon == index) Color.Gray else MaterialTheme.colors.surface,
                shape = CircleShape
              )
          ) {
            Image(
              painter = painterResource(id = icon),
              contentDescription = "Icon $icon",
              modifier = Modifier
                .padding(12.dp, 8.dp)
                .size(36.dp)
            )
          }
        }
      }
    }
    NextButton(
      onClick = {
        scope.launch {
          scrollState.animateScrollToItem(scrollState.firstVisibleItemIndex+3)
        }
      },
      fontSize = 12.sp
    )
  }
}