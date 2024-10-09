package com.example.todojpc.Database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojpc.pages.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {


    val todoDao = MainApplication.todoDatabase.getTodoDao()
    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()


    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(Todo(title = title, createdAt = Date.from(Instant.now())))
        }
    }

    fun deleteTodo(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }

    }

    fun updateTodoCompletion(id: Int, completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val todo = todoDao.getTodoById(id)
            todo?.let {
                val updatedTodo = it.copy(completed = completed)
                todoDao.updateTodo(updatedTodo)
            }

        }
    }

    fun updateTodoTitle(id: Int, completed: String) {
     viewModelScope.launch(Dispatchers.IO){
         val todo = todoDao.getTodoById(id)
         todo?.let {
             val updatedTodo = it.copy(title = completed)
             todoDao.updateTodo(updatedTodo)
         }
     }
    }
}