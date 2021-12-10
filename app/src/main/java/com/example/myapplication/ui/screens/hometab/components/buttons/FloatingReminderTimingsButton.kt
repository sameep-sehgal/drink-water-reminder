package com.example.myapplication.ui.screens.hometab.components.buttons

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun FloatingAlarmButton(setShowReminderTimingsDialog: (Boolean)->Unit) {
    FloatingActionButton(
        onClick = {
            setShowReminderTimingsDialog(true)
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.notification_bell),
            contentDescription = "Scroll to Top"
        )
    }
}