//package com.example.todojpc.Database
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import com.example.todojpc.Database.Todo
//
//@Dao
//interface TodoDao {
//
//    @Query("SELECT * FROM TODO")
//    fun getAllTodo(): LiveData<List<Todo>>
//
//    @Insert
//    fun addTodo(todo: Todo)
//
//    @Query("Delete FROM Todo where id = id")
//    fun deleteTodo(int: Int)
//
//
//}

package com.puja.notestasker.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo")
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    // Corrected delete query to bind the parameter
    @Query("DELETE FROM Todo WHERE id = :id")
    fun deleteTodo(id: Int)

    @Query("SELECT * FROM todo WHERE id = :id LIMIT 1")
     fun getTodoById(id: Int): Todo?

    @Update
    fun updateTodo(todo: Todo)
}


