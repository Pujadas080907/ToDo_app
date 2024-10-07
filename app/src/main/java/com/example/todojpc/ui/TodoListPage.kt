

/*
package com.example.todojpc.ui

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todojpc.Database.NoteViewModel
import com.example.todojpc.Database.Todo
import com.example.todojpc.Database.TodoViewModel
import com.example.todojpc.R
import com.example.todojpc.pages.Navitems
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListPage(todoViewModel: TodoViewModel, noteViewModel: NoteViewModel) {
    var selectedIndex by remember { mutableStateOf(0) }

    val navItemList = listOf(
        Navitems("Home", Icons.Default.Home),
        Navitems("Notes", Icons.Default.Menu),
        Navitems("Profile", Icons.Default.Person)
    )

    BackHandler(enabled = selectedIndex !=0) {
        selectedIndex = 0
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {

              when (selectedIndex){
                  0->{
                      Box (
                          modifier = Modifier
                              .fillMaxWidth()
                              .height(70.dp)
                      ){
                          TopAppBar(
                              title = { Text(text = "My To-Do") },
                              backgroundColor = Color(0xFF189AB4),
                              contentColor = Color.White,

                              )
                      }
                  }

              }

        },
        bottomBar = {

            if (selectedIndex != 1) {
                NavigationBar {
                    navItemList.forEachIndexed { index, navitems ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = { Icon(imageVector = navitems.icon, contentDescription = "Icon") },
                            label = { Text(text = navitems.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Call ContentScreen based on the selected index in bottom navigation

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            ContentScreen(
                modifier = Modifier.padding(innerPadding),
                selectedIndex = selectedIndex,
                todoViewModel = todoViewModel,
                noteViewModel = noteViewModel
            )
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    todoViewModel: TodoViewModel,
    noteViewModel: NoteViewModel
) {
    when (selectedIndex) {
        0 -> TodoListScreen(todoViewModel = todoViewModel)  // Home Page (Todo list)
        1 -> NotesPage(noteViewModel = noteViewModel)       // Notes Page
        //else -> ProfilePage()  // Placeholder for Profile Page
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListScreen(todoViewModel: TodoViewModel) {
    val todoList by todoViewModel.todoList.observeAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it

                }, modifier = Modifier.weight(1f),
                maxLines = 2,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor  = Color.Gray,
                    unfocusedBorderColor  = Color.LightGray
                ),
                placeholder = { Text(text = " Write To-Do...")}

            )
            Button(
                onClick = {

                    if(inputText.isNotBlank()){
                        todoViewModel.addTodo(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF189AB4)
                ),
                contentPadding = PaddingValues(0.dp)

            ) {
               Icon(
                   painter = painterResource(id = R.drawable.done) ,
                   contentDescription = "Add",
                   tint = Color.White,
                   modifier = Modifier.size(25.dp)
               )
            }
        }
        todoList?.let {

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(3.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(it) { item ->
                    TodoItem(
                        item = item,
                        onDelete = { todoViewModel.deleteTodo(item.id) },
                        onCompleteChanged ={ completed ->
                            todoViewModel.updateTodoCompletion(item.id,completed)
                        }
                    )
                }
            }

        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No Items yet",
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, onCompleteChanged: (Boolean)->Unit) {
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
            Checkbox(
                checked = item.completed,
                onCheckedChange = {checked -> onCompleteChanged(checked)},
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF81B622),
                    uncheckedColor = Color.Black,
                    checkmarkColor = Color.White
                )
            )


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    color = if (item.completed) Color.Black else Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textDecoration = if(item.completed) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = SimpleDateFormat(
                        "HH:mm:aa, dd/MM",
                        Locale.ENGLISH
                    ).format(item.createdAt),
                    fontSize = 10.sp,
                    color = Color.LightGray,

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
}*/


package com.example.todojpc.ui

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todojpc.Database.NoteViewModel
import com.example.todojpc.Database.Todo
import com.example.todojpc.Database.TodoViewModel
import com.example.todojpc.R
import com.example.todojpc.pages.Navitems
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListPage(todoViewModel: TodoViewModel, noteViewModel: NoteViewModel) {
    var selectedIndex by remember { mutableStateOf(0) }

    val navItemList = listOf(
        Navitems("Home", Icons.Default.Home),
        Navitems("Notes", Icons.Default.Menu),
        Navitems("Profile", Icons.Default.Person)
    )

    BackHandler(enabled = selectedIndex != 0) {
        selectedIndex = 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (selectedIndex != 1) {
                NavigationBar {
                    navItemList.forEachIndexed { index, navitems ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = { Icon(imageVector = navitems.icon, contentDescription = "Icon") },
                            label = { Text(text = navitems.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Call ContentScreen based on the selected index in bottom navigation
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ContentScreen(
                modifier = Modifier.padding(innerPadding),
                selectedIndex = selectedIndex,
                todoViewModel = todoViewModel,
                noteViewModel = noteViewModel
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    todoViewModel: TodoViewModel,
    noteViewModel: NoteViewModel
) {
    when (selectedIndex) {
        0 -> TodoListScreen(todoViewModel = todoViewModel)  // Home Page (Todo list)
        1 -> NotesPage(noteViewModel = noteViewModel)       // Notes Page
        // else -> ProfilePage()  // Placeholder for Profile Page
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListScreen(todoViewModel: TodoViewModel) {
    val todoList by todoViewModel.todoList.observeAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        // Input section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                maxLines = 2,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                placeholder = { Text(text = " Write To-Do...") }
            )
            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        todoViewModel.addTodo(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF189AB4)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.done),
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        // Display the list of todos
        todoList?.let {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(3.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(it) { item ->
                    TodoItem(
                        item = item,
                        onDelete = { todoViewModel.deleteTodo(item.id) },
                        onCompleteChanged = { completed -> todoViewModel.updateTodoCompletion(item.id, completed) }
                    )
                }
            }
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No Items yet",
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, onCompleteChanged: (Boolean) -> Unit) {
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
        ) {
            Checkbox(
                checked = item.completed,
                onCheckedChange = { checked -> onCompleteChanged(checked) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF81B622),
                    uncheckedColor = Color.Black,
                    checkmarkColor = Color.White
                )
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    color = if (item.completed) Color.Black else Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textDecoration = if (item.completed) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = SimpleDateFormat(
                        "HH:mm:aa, dd/MM",
                        Locale.ENGLISH
                    ).format(item.createdAt),
                    fontSize = 10.sp,
                    color = Color.LightGray
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
