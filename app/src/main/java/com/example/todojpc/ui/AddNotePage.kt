package com.example.todojpc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.example.todojpc.R


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    BackHandler {
        onBack()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Top AppBar with back and save icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back"
                )
            }

            IconButton(onClick = {
                if (title.isNotBlank() && body.isNotBlank()) {
                    onNoteAdded(title, body) // Add note
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.done),
                    contentDescription = "Save"
                )
            }
        }


        TextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Title",color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )

        // Body input - takes the rest of the space
        TextField(
            value = body,
            onValueChange = { body = it },
            placeholder = { Text("Write your notes here...", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
    }
}
*/

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    BackHandler {
        onBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            TopAppBar(
                title = { Text(text = "Add Note") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank() && body.isNotBlank()) {
                            onNoteAdded(title, body) // Add note and navigate back
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = "Save"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF189AB4),
                    titleContentColor = Color.White
                )
            )
        },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            // Body input - takes the rest of the space
            TextField(
                value = body,
                onValueChange = { body = it },
                placeholder = { Text("Write your notes here...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
        }
    }
}


*/



/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    BackHandler {
        onBack()
    }

    // Remove any window insets or top padding
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Add Note") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank() && body.isNotBlank()) {
                            onNoteAdded(title, body) // Add note and navigate back
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.done),
                            contentDescription = "Save"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF189AB4),
                    titleContentColor = Color.White
                ),
            modifier = Modifier.fillMaxWidth()

            )
        },
        modifier = Modifier.fillMaxSize()

    ) { paddingValues ->
        // Ensure that padding is applied only to content below the AppBar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // This will ensure the content is placed correctly

        ) {
            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            // Body input
            TextField(
                value = body,
                onValueChange = { body = it },
                placeholder = { Text("Write your notes here...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Make it take the remaining space
                    .padding(top = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
        }
    }
}*/


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    BackHandler {
        onBack()
    }

    // Use Scaffold without the TopAppBar
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        // Custom AppBar with Back and Save icons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Ensure content is correctly placed
        ) {
            // Custom Row for icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), // Add padding around icons
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back"
                    )
                }

                IconButton(onClick = {
                    if (title.isNotBlank() && body.isNotBlank()) {
                        onNoteAdded(title, body) // Add note and navigate back
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = "Save"
                    )
                }
            }

            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            // Body input
            TextField(
                value = body,
                onValueChange = { body = it },
                placeholder = { Text("Write your notes here...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Make it take the remaining space
                    .padding(top = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
        }
    }
}


*/
/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    BackHandler {
        onBack()
    }

    // Use Scaffold without the TopAppBar
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        // Custom AppBar with Back and Save icons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding(),
                    top = 0.dp // Set top padding to 0 to eliminate extra space
                )
        ) {
            // Custom Row for icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), // Add padding around icons
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back"
                    )
                }

                IconButton(onClick = {
                    if (title.isNotBlank() && body.isNotBlank()) {
                        onNoteAdded(title, body) // Add note and navigate back
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = "Save"
                    )
                }
            }

            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            // Body input
            TextField(
                value = body,
                onValueChange = { body = it },
                placeholder = { Text("Write your notes here...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Make it take the remaining space
                    .padding(top = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
        }
    }
}*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    BackHandler {
        onBack()
    }

    // Remove the Scaffold and use a Column directly
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Optional padding for overall layout
    ) {
        // Custom Row for icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp), // Add vertical padding around icons
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back"
                )
            }

            IconButton(onClick = {
                if (title.isNotBlank() && body.isNotBlank()) {
                    onNoteAdded(title, body) // Add note and navigate back
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.done),
                    contentDescription = "Save"
                )
            }
        }

        // Title input
        TextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Title", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )

        // Body input
        TextField(
            value = body,
            onValueChange = { body = it },
            placeholder = { Text("Write your notes here...", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Make it take the remaining space
                .padding(top = 5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
    }
}
