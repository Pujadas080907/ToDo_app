package com.puja.notestasker.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.puja.notestasker.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(onNoteAdded: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) } // State to trigger the toast

    // Get the context for showing the toast
    val context = LocalContext.current

    // If showToast is true, show the toast and reset the flag
    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Please write something...", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

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
                }else{
                    showToast = true
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
