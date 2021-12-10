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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.myapplication.ui.theme.Typography
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun DisplayTabLayout() {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {
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
                        pagerState.animateScrollToPage(tab.ordinal)
                    }},
                    modifier = Modifier.background(color = MaterialTheme.colors.primary)
                )
            }
        }
        //Display Function for diff tabs
        //Pager used to swipe between different tabs
        HorizontalPager(count = Tabs.values().size,
            state = pagerState, verticalAlignment = Alignment.Top) { index->
            Column(modifier = Modifier.fillMaxSize()) {
                Tabs.values()[index].ui()
            }
        }
    }
}