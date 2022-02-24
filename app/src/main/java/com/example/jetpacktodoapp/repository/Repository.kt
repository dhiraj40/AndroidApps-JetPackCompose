package com.example.jetpacktodoapp.repository

import com.example.jetpacktodoapp.Data.Dao.Dao
import com.example.jetpacktodoapp.Data.Model.TodoItem

class Repository{

    fun getAllTodos() = dao.getAllTodos()

    fun insert(todoItem: TodoItem){
        dao.insertTodo(todoItem)
    }

    fun update(todoItem: TodoItem){
        dao.updateTodo(todoItem)
    }

    fun delete(todoItem: TodoItem){
        todoItem.id?.let { dao.deleteTodo(it) }
    }

    companion object{
        val dao:Dao = Dao()
    }

}