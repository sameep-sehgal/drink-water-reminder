package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBar(
  selectedTab: Int,
  setSelectedTab: (Int) -> Unit,
  darkTheme: Boolean
) {
  BottomNavigation(
    modifier = Modifier.height(56.dp),
  ) {
    Tabs.values().forEachIndexed { i: Int, it: Tabs ->
      BottomNavigationItem(
        selected = selectedTab == i,
        onClick = { setSelectedTab(i) },
        icon = {
          Icon(
            painter = painterResource(id = it.icon),
            contentDescription = null,
            modifier = Modifier
              .padding(0.dp,0.dp,0.dp,4.dp)
              .size(15.dp)
          )
        },
        label = {
          Text(
            text = it.title,
            maxLines = 1,
            fontSize = 10.sp
          )
        },
        selectedContentColor =
        if(darkTheme)
          MaterialTheme.colors.primary
        else
          MaterialTheme.colors.secondary,
      )
    }
  }
}