

package com.example.todojpc.pages

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todojpc.Database.NoteViewModel
import com.example.todojpc.Database.TodoViewModel
import com.example.todojpc.R
import com.example.todojpc.ui.TodoListPage
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.size.Size


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        enableEdgeToEdge()

        setContent {
            Navigation(todoViewModel = todoViewModel, noteViewModel = noteViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(todoViewModel: TodoViewModel, noteViewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splashScreen") {
        composable("splashScreen") {
            SplashScreen(navController = navController)
        }
        composable("main_screen")
        {
            val context = LocalContext.current
            BackHandler {
                (context as? Activity)?.finish()
            }

            TodoListPage(todoViewModel = todoViewModel, noteViewModel = noteViewModel)
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Launching the animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.5f,  // Final scale to full size
            animationSpec = tween(
                durationMillis = 600,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(1500L)  // Delay for the splash screen duration
        navController.navigate("main_screen") {
            popUpTo("splashScreen") { inclusive = true }
        }
    }

    // Centered logo with animation
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.wishbg),
            contentDescription = "SplashLogo",
            modifier = Modifier.scale(scale.value)
        )



    }
}


