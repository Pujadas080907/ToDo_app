package com.puja.notestasker.pages

import android.app.Application
import androidx.room.Room
import com.puja.notestasker.Database.TodoDatabase

class MainApplication : Application() {

    val database: TodoDatabase by lazy {
        TodoDatabase.getDatabase(this)
    }
    companion object{
        lateinit var todoDatabase : TodoDatabase

    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_note_database"
        ).fallbackToDestructiveMigrationFrom()
            .build()
    }
}