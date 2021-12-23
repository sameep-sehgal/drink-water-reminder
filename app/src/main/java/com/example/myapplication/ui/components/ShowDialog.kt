package com.example.myapplication.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable

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
      Text(title)
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