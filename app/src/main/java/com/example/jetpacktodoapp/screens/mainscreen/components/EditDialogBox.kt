package com.example.jetpacktodoapp.screens.mainscreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.jetpacktodoapp.Data.Model.TodoItem
import com.example.jetpacktodoapp.repository.Repository

@Composable
fun EditDialogBox(
    repository: Repository,
    todoItem: TodoItem,
    updateList: () -> Unit,
    openDialog: MutableState<Boolean>
){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {openDialog.value=false},
            title = { Text(todoItem.title)},
            text = {
                Text(todoItem.description+"\n"+todoItem.date)
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {openDialog.value = false}
                ){
                    Text("OK")
                }
            }
        )
    }
}