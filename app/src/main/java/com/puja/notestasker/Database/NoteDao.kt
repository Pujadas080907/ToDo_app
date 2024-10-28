package com.puja.notestasker.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNote(id: Int)

    @Update
    fun updateNote(note: Note)


}