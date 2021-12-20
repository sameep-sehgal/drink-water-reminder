package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
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
  History("History", R.drawable.history_icon_white),
  Settings("Settings", R.drawable.settings_icon_white)
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = tab.icon), contentDescription = null)
            Text(text = tab.title,
              modifier = Modifier.padding(start = Dp(5f)),
              style = Typography.subtitle1)
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
