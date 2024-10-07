

package com.example.todojpc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todojpc.Database.Note
import com.example.todojpc.Database.NoteViewModel
import com.example.todojpc.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.style.TextDecoration
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesPage(noteViewModel: NoteViewModel) {

    var searchText by remember { mutableStateOf("") }
    val notes by noteViewModel.noteList.observeAsState(emptyList())
    var showAddNotePage by remember { mutableStateOf(false) }

    if (showAddNotePage) {

        AddNotePage(
            onNoteAdded = { title, body ->
                noteViewModel.addNote(title, body)
                showAddNotePage = false
            },
            onBack = {
                showAddNotePage = false
            }
        )
    }else{

        // Column for layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Search Bar
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text(text = "Search Notes", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF189AB4).copy(alpha = 0.2f),
                    unfocusedIndicatorColor = Color(0xFF189AB4).copy(alpha = 0.2f),
                    focusedLabelColor = Color.Black
                )
            )

            // List of Notes
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notes.filter { it.title.contains(searchText, ignoreCase = true) || it.body.contains(searchText, ignoreCase = true) }) { note ->
                    NoteItem(note = note, onDelete = {
                        noteViewModel.deleteNote(note.id)
                    })
                }
            }
        }

        // Floating Action Button for adding new notes
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {

            FloatingActionButton(
                onClick = { showAddNotePage = true },
                modifier = Modifier
                    .padding(0.dp,0.dp,20.dp,10.dp),
                shape = CircleShape,
                containerColor = Color(0xFF189AB4)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }

}




@Composable
fun NoteItem(note: Note, onDelete: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF189AB4)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )

        {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = note.title,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = note.body,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }


            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.dlticon),
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}


