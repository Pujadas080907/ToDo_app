package com.example.todojpc.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojpc.pages.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel: ViewModel() {
    val noteDao = MainApplication.todoDatabase.getNoteDao()
    val noteList: LiveData<List<Note>> = noteDao.getAllNotes()

    fun addNote(title: String, body : String){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.addNote(Note(title = title, body = body))
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(id)
        }
    }
}