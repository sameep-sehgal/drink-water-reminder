package com.example.myapplication.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.example.myapplication.HistoryTab
import com.example.myapplication.R
import com.example.myapplication.ui.screens.hometab.HomeTab

enum class Tabs(val title:String, val icon:Int, val ui :@Composable()()->Unit){
    @ExperimentalFoundationApi
    Home("Home", R.drawable.home_icon_glass_white, { HomeTab() }),
    History("History", R.drawable.history_icon_white, { HistoryTab() }),
    Settings("Settings", R.drawable.settings_icon_white, { SettingsTab() })
}