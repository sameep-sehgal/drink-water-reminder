package com.example.myapplication.ui.screens.hometab.components.buttons

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.example.myapplication.R
import kotlinx.coroutines.launch

@Composable
fun BackToTopButton(scrollState: ScrollState){
    val scope = rememberCoroutineScope()
    if(scrollState.value>250){
        IconButton(
            modifier = Modifier.zIndex(2f),
            onClick = {
                scope.launch {
                    scrollState.animateScrollTo(0)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.up_arrow),
                contentDescription = "Scroll to Top"
            )
        }
    }
}