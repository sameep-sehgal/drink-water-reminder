package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun FruitDialog(setShowFruitDialog:(Boolean)->Unit){
  val title = "Add Fruit Intake"
  ShowDialog(
    title = title,
    content = { FruitDialogContent() },
    setShowDialog = setShowFruitDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}


@Composable
fun FruitDialogContent():Unit{
  
}