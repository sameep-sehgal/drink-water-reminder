package com.example.myapplication.ui.screens.hometab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun WorkoutDialog(setShowWorkoutDialog:(Boolean)->Unit):Unit{
    val title = "Set Today's Workout Level"
    ShowDialog(title = title, content = { WorkoutDialogContent() }, setShowDialog = setShowWorkoutDialog)
}

@Composable
fun WorkoutDialogContent():Unit{
    Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
        Text(text = "Dialog working")
        Text(text = "This is workout Dialog Content")
    }
}