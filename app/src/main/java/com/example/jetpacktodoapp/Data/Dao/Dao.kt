package com.example.jetpacktodoapp.Data.Dao

import com.example.jetpacktodoapp.Data.Model.TodoItem
import java.time.LocalDate

class Dao {

    fun getAllTodos(): List<TodoItem> {
        return todos
    }

    fun insertTodo(
        todoItem: TodoItem
    ){
        todoItem.id = todos.size
        todos = todos+ listOf(todoItem)
    }

    fun updateTodo(todoItem: TodoItem){
        todoItem.id?.let { deleteTodo(it) }
        insertTodo(todoItem)
    }

    fun deleteTodo(id:Int){
        todos = todos.toMutableList().also { it.removeIf { todo-> todo.id==id } }
    }

    companion object {
        private var todos = listOf<TodoItem>(
            TodoItem(1,"Walking","Walking...",LocalDate.now().toString()),
            TodoItem(2,"Gymm","Gymm...",LocalDate.now().toString()),
            TodoItem(3,"Study","Studying...",LocalDate.now().toString()),
            TodoItem(4,"Walking","Walking...",LocalDate.now().toString())
        )
    }
}