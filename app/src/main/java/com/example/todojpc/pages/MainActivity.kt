

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
import com.example.todojpc.Database.AuthViewModel



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        enableEdgeToEdge()

        setContent {
            Navigation(todoViewModel = todoViewModel, noteViewModel = noteViewModel, authViewModel = authViewModel )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(todoViewModel: TodoViewModel, noteViewModel: NoteViewModel, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splashScreen") {
        composable("splashScreen") {
            SplashScreen(navController = navController)
        }
        composable("signUpPage"){
            SignUpPage(navController = navController, authViewModel = authViewModel)
        }
        composable("signInPage"){
            SignInPage(navController = navController, authViewModel = authViewModel)

        }
        composable("main_screen")
        {
            val context = LocalContext.current
            BackHandler {
                (context as? Activity)?.finish()
            }
            val onSignOut: ()-> Unit ={
                authViewModel.signout()
                navController.navigate("signInPage")
            }
            TodoListPage(todoViewModel = todoViewModel, noteViewModel = noteViewModel, onSignOut = onSignOut)
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
        navController.navigate("signUpPage") {
            popUpTo("splashScreen") { inclusive = true }
        }
    }

    // Centered logo with animation
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.animationbg),
            contentDescription = "SplashLogo",
            modifier = Modifier.scale(scale.value)
        )
    }
}


@Composable
fun SignUpPage(navController: NavController, authViewModel: AuthViewModel) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
   // var errorMsg by remember { mutableStateOf("") }

    val context = LocalContext.current

    val authState = authViewModel.authState.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
//              authViewModel.signup(email,password)
//                navController.navigate("main_screen")
                if (email.contains("@")) {
                    authViewModel.signup(email, password)
                    navController.navigate("main_screen")
                } else {
                   Toast.makeText(context, "Please enter a valid email id.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(130.dp)
        ) {
            Text("Sign Up")
        }


        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Already have an account? ")
            Text(
                text = "Sign In",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate("signInPage")
                }
            )
        }
    }
}

@Composable
fun SignInPage(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.login(email,password)
                navController.navigate("main_screen")
            },
            modifier = Modifier
                .width(130.dp)

        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))



        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Don't have an account? ")
            Text(
                text = "Sign Up",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate("signUpPage")
                }
            )
        }
    }
}