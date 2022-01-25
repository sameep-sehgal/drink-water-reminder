package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.ui.theme.Typography
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

import com.example.myapplication.R

enum class Tabs(val title:String, val icon:Int){
  Home("Home", R.drawable.home_icon_glass_white),
  Stats("Stats", R.drawable.stats_tab_icon),
  History("History", R.drawable.history_icon_white),
  Settings("Settings", R.drawable.settings_icon_white),
}

@ExperimentalPagerApi
@Composable
fun DisplayTabLayout(
  pagerState: PagerState
) {
  val scope = rememberCoroutineScope()
  TabRow(selectedTabIndex = pagerState.currentPage) {
    Tabs.values().forEachIndexed { index, tab ->
      Tab(
        text = {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(painter = painterResource(id = tab.icon), contentDescription = null)
            Text(text = tab.title,
              modifier = Modifier.padding(start = Dp(5f)),
              fontSize = 9.sp
            )
          }
        },
        selected = pagerState.currentPage == index,
        onClick = {
          scope.launch {
            pagerState.scrollToPage(tab.ordinal)
          }
        },
        modifier = Modifier.background(color = MaterialTheme.colors.primary)
      )
    }
  }
}
