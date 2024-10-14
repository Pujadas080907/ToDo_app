

package com.example.todojpc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todojpc.Database.Note
import com.example.todojpc.Database.NoteViewModel
import com.example.todojpc.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesPage(noteViewModel: NoteViewModel) {

    var searchText by remember { mutableStateOf("") }
    val notes by noteViewModel.noteList.observeAsState(emptyList())
    var showAddNotePage by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    var showEditNotePage by remember { mutableStateOf(false) }

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
    }else if (showEditNotePage && selectedNote != null) { // Change here
        // Show Edit Note Page
        EditNotePage(
            note = selectedNote!!,
            onNoteUpdated = { updatedBody ->
                noteViewModel.updateNote(selectedNote!!.id, selectedNote!!.title, updatedBody)
                showEditNotePage = false
                selectedNote = null
            },
            onBack = {
                showEditNotePage = false // Change here
                selectedNote = null
            }
        )
    }

    else{

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
                    NoteItem(note = note,
                      onClick = {selectedNote = note
                                showEditNotePage = true},
                        onDelete = {
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
                    .padding(0.dp,0.dp,20.dp,0.dp),
                shape = CircleShape,
                containerColor = Color(0xFF189AB4)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }

}






@Composable
fun NoteItem(note: Note, onDelete: () -> Unit, onClick: () -> Unit) {



    Card(
        modifier = Modifier
            .width(160.dp)
            .height(120.dp) // Adjusted height for better text accommodation
            .padding(4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF189AB4)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box( // Use Box to allow layering of elements
            modifier = Modifier.fillMaxSize()
        ) {
            // Column for text content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopStart), // Align text at the top left
                verticalArrangement = Arrangement.SpaceBetween // Distribute space evenly
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


            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = SimpleDateFormat("HH:mm aa, dd/MM", Locale.ENGLISH).format(note.createdAt),
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.width(10.dp)) // Add space between date and icon
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .padding(start = 8.dp) // Adjust padding for the icon
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dlticon),
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
