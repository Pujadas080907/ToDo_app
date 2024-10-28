package com.puja.notestasker.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.puja.notestasker.pages.ConvertersClass


@Database(entities = [Todo::class, Note::class], version = 2, exportSchema = false)
@TypeConverters(ConvertersClass::class)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun getTodoDao() : TodoDao
    abstract fun  getNoteDao() : NoteDao

//    companion object{
//        const val NAME = "Todo_DB"
//    }

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_note_database"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}