package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowDialog(
  title:String,
  content:@Composable()()->Unit,
  setShowDialog:(Boolean)->Unit,
  onConfirmButtonClick:() -> Unit
) {

  AlertDialog(
    onDismissRequest = { onDialogDismiss(setShowDialog) },
    title = {
      Text(
        title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
      )
    },
    confirmButton = {
      Button(
        onClick = {
          onConfirmButtonClick()
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
    },
  )
}


fun onDialogDismiss(setShowDialog: (Boolean) -> Unit){
  setShowDialog(false)
}