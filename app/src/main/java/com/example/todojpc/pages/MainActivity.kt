package com.example.todojpc.pages

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.todojpc.Database.NoteViewModel
import com.example.todojpc.Database.TodoViewModel
import com.example.todojpc.ui.TodoListPage

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        enableEdgeToEdge()
        setContent {

                TodoListPage(todoViewModel = todoViewModel, noteViewModel = noteViewModel)
        }
    }
}

