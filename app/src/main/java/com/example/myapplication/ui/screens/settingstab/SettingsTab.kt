package com.example.myapplication

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsTab():Unit{
    Surface{
        Text(
            text = "Settings Tab selected",
            style = MaterialTheme.typography.body1
        )
    }
}