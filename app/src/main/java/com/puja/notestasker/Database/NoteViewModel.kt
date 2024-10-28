package com.puja.notestasker.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puja.notestasker.pages.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class NoteViewModel: ViewModel() {
    val noteDao = MainApplication.todoDatabase.getNoteDao()
    val noteList: LiveData<List<Note>> = noteDao.getAllNotes()

    fun addNote(title: String, body : String){
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = Date()
            val newNote = Note(title = title, body = body, createdAt = currentDate, updateAt = currentDate)
            noteDao.addNote(newNote)
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(id)
        }
    }

    fun updateNote(id: Int, title: String, updatedBody: String) {
        viewModelScope.launch(Dispatchers.IO){
            val updatedNote = Note(id = id, title = title, body = updatedBody, updateAt = Date())
            noteDao.updateNote(updatedNote)
        }

    }
}