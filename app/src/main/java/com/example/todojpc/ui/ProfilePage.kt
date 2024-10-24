package com.example.todojpc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todojpc.R

@Composable
fun ProfilePage(onSignOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.userpro), // Replace with your image resource
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        Text(
            text = "Username", // Replace with dynamic username if needed
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Sign Out Button
        Button(
            onClick = { onSignOut() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF189AB4)),
            modifier = Modifier
                .width(130.dp)

        ) {
            Text("Sign Out")
        }
    }

}
