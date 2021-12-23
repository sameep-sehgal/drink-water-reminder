package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun ResetDialog(setShowResetDialog:(Boolean)->Unit):Unit{
    val title = "Reset Today's Record?"
    ShowDialog(
        title = title,
        content = { ResetDialogContent() },
        setShowDialog = setShowResetDialog,
        onConfirmButtonClick = { /*Todo*/ }
    )
}

@Composable
fun ResetDialogContent():Unit{
    Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
        Text(text = "Are you sure?")
        Text(text = "You will not be able to ")
    }
}