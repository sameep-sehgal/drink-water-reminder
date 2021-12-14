package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.myapplication.ui.theme.ApplicationTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Surface
import com.example.myapplication.ui.components.DisplayTabLayout

@ExperimentalPagerApi
@AndroidEntryPoint
class HomeActivty : ComponentActivity(){
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ApplicationTheme {
        Surface() {
          DisplayTabLayout()
        }
      }
    }
  }
}