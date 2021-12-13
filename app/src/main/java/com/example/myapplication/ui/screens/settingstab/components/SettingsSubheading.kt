package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Typography

@Composable
fun SettingsSubheading(text:String){
  Text(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    text = text,
    style = Typography.body1,
    textAlign = TextAlign.Center
  )
}