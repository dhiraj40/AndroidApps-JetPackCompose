package com.example.jetpacktodoapp.screens.mainscreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpacktodoapp.Data.Model.TodoItem
import com.example.jetpacktodoapp.repository.Repository
import java.time.LocalDate

@Composable
fun AddDialogBox(repository: Repository, openDialog: MutableState<Boolean>, updateList: () -> Unit){

    val title = remember{ mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value=false},
            title = { Text(text = "Add Todo", fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(10.dp)) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ) {
                    OutlinedTextField(
                        label= { Text("Title") },
                        value = title.value,
                        onValueChange = { title.value=it }
                    )
                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value=it },
                        label = { Text("Description")}
                    )
                }
            },
            confirmButton = { OutlinedButton(
                onClick = {
                    repository.insert(
                        TodoItem(
                            id = null,
                            title = title.value,
                            description = description.value,
                            date = LocalDate.now().toString()
                        )
                    )
                    updateList()
                    title.value = ""
                    description.value = ""
                    openDialog.value = false
                }
            ){
                Text("Save")
            } }

        )
    }
}