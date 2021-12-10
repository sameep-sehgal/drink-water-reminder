package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun ShowDialog(
    title:String,
    content:@Composable()()->Unit,
    setShowDialog:(Boolean)->Unit
):Unit{
    AlertDialog(onDismissRequest = { setShowDialog(false) },
        title = {
            Text(title)
        },
        confirmButton = {
            Button(
                onClick = {
                    // Change the state to close the dialog
                    setShowDialog(false)
                },
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    // Change the state to close the dialog
                    setShowDialog(false)
                },
            ) {
                Text("Dismiss")
            }
        },
        text = {
            content()
        },)
}