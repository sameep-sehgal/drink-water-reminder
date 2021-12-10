package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun WorkoutButton(modifier: Modifier,setShowWorkoutDialog:(Boolean)->Unit){
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(R.drawable.workout_button),
            contentDescription = "Workout Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(enabled = true, onClick = { setShowWorkoutDialog(true) }))
    }
}