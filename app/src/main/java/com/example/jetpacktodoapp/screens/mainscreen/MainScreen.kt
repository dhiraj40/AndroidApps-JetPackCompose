package com.example.jetpacktodoapp.screens.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpacktodoapp.Data.Model.TodoItem
import com.example.jetpacktodoapp.repository.Repository
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpacktodoapp.screens.mainscreen.components.AddDialogBox
import com.example.jetpacktodoapp.screens.mainscreen.components.EditDialogBox

@Composable
fun MainScreen( ){

    val repository = Repository()
    var todoItems by remember { mutableStateOf(repository.getAllTodos()) }
    val updateList = {todoItems = repository.getAllTodos()}


    Scaffold(
        topBar = { TopAppBar { Text("Todo App") } },
        floatingActionButton = {
            val openDialog = remember { mutableStateOf(false) }

            FloatingActionButton(onClick = { openDialog.value = true }){
                AddDialogBox(repository, openDialog,updateList)
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ){
        val openEditDialog = remember{ mutableStateOf(false)}
        val openConfirmDelete = remember { mutableStateOf(false) }
        val selectedTodo = remember { mutableStateOf(TodoItem(1,"Dummy","Dummy","Dummy")) }

        val setSelectedTodo:(TodoItem, Int)->Unit = { todoItem, actionType -> run{
            selectedTodo.value = todoItem
            when(actionType){
                ActionTypes.EDIT_DIALOG ->{
                    openEditDialog.value = true

                }
                ActionTypes.DELETE_DIALOG ->{
                    openConfirmDelete.value = true
                }
            }
        }
        }
        ItemList(todoItems, setSelectedTodo )
        EditDialogBox(repository, selectedTodo.value,updateList,openEditDialog)
        ConfirmDelete(repository,selectedTodo.value,updateList,openConfirmDelete)
    }

}

@Composable
fun ItemList(
    todoItems: List<TodoItem>,
    setSelectedTodo: (TodoItem, Int) -> Unit,
){
    LazyColumn {
        items(todoItems){
            todo -> TodoView(todo, setSelectedTodo)
        }
    }
}

@Composable
fun TodoView(todoItem: TodoItem, setSelectedTodo: (TodoItem, Int) -> Unit){
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth()
            .clickable (onClick = {

                setSelectedTodo(todoItem, ActionTypes.EDIT_DIALOG)

                //EditDialogBox(repository, todoItem,updateList,openDialog)
                //Toast.makeText(context,"Item clicked: "+todoItem.title,Toast.LENGTH_SHORT).show()
            })
    ) {

        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier.padding(10.dp)){
                Text(text = todoItem.title, fontWeight = FontWeight.ExtraBold)
                Text(text = todoItem.description, fontStyle = FontStyle.Italic)
            }

            IconButton(onClick = {
                setSelectedTodo(todoItem, ActionTypes.DELETE_DIALOG)
                //confirmDelete(repository, todoItem, updateList,openDialog)
                },
                content = { Icon(Icons.Default.Delete, contentDescription = "delete") }
            )
        }


    }
}

@Composable
fun ConfirmDelete(
    repository: Repository,
    todoItem: TodoItem,
    updateList: () -> Unit,
    openDialog: MutableState<Boolean>
){

    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Are you sure?")},
            confirmButton = {

                OutlinedButton(
                    onClick = {
                        repository.delete(todoItem)
                        openDialog.value = false
                        updateList()
                    }
                ){
                    Text("Yes")
                }


            },
            dismissButton = {

                OutlinedButton(
                    onClick = {openDialog.value = false}
                ){
                    Text("No")
                }

            }
        )
    }

}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme{
        MainScreen()
    }
}