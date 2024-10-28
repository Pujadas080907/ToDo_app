package com.puja.notestasker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.puja.notestasker.Database.Note
import com.puja.notestasker.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNotePage(note: Note, onNoteUpdated: (String) -> Unit, onBack: () -> Unit) {
    var body by remember { mutableStateOf(note.body) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        IconButton(onClick = onBack) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Display the title (non-editable)
        Text(
            text = note.title,

            modifier = Modifier.padding(8.dp)
        )
        Divider(
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 8.dp) // Add some vertical padding if needed
        )

        Spacer(modifier = Modifier.height(10.dp))


        TextField(
            value = body,
            onValueChange = { body = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Make it take the remaining space
                .padding(top = 5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        FloatingActionButton(
            onClick = { onNoteUpdated(body) },
            modifier = Modifier.align(Alignment.End),
            containerColor = Color(0xFF189AB4),
            shape = androidx.compose.foundation.shape.CircleShape
        ) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = "Done")
        }
    }
}
