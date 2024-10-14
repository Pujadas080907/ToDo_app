


package com.example.todojpc.ui

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    var isEditing by remember{ mutableStateOf(false) }
    var editTodo by remember{ mutableStateOf<Int?>(null) }
    var searchText by remember { mutableStateOf("") }


    // State for showing the bottom sheet
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text(text = "Search Tasks", color = Color.Gray) },
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
                        onCompleteChanged = { completed -> todoViewModel.updateTodoCompletion(item.id, completed) },
                        onEdit ={
                            inputText = item.title
                            isEditing = true
                            editTodo = item.id
                            showBottomSheet = true;
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
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        FloatingActionButton(
            onClick = { showBottomSheet = true},
            modifier = Modifier
                .padding(0.dp,0.dp,20.dp,0.dp),
            shape = CircleShape,
            containerColor = Color(0xFF189AB4)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }

        // Show bottom sheet for adding new task
    /*    if (showBottomSheet) {
            AddTaskBottomSheet(
                onDismiss = { showBottomSheet = false },
                onAddTask = { newTask ->
                    todoViewModel.addTodo(newTask)
                    showBottomSheet = false
                },
                currentTaskTitle = if (isEditing) inputText else ""
            )
        }*/
        if (showBottomSheet) {
            AddTaskBottomSheet(
                onDismiss = { showBottomSheet = false },
                onAddTask = { newTask ->
                    if (isEditing && editTodo != null) {
                        // Update the task if editing
                        todoViewModel.updateTodoTitle(editTodo!!, newTask)
                    } else {
                        // Add new task
                        todoViewModel.addTodo(newTask)
                    }
                    showBottomSheet = false
                },
                currentTaskTitle = inputText,
                isEditing = isEditing
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    onDismiss: () -> Unit,
    onAddTask: (String) -> Unit,
    currentTaskTitle: String,
    isEditing: Boolean = false
) {
    var inputText by remember { mutableStateOf(currentTaskTitle) }
    var showToast by remember { mutableStateOf(false) } // State to trigger the toast

    // Get the context for showing the toast
    val context = LocalContext.current

    // If showToast is true, show the toast and reset the flag
    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Please input a task", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    // Bottom sheet dialog
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if(isEditing) "Edit Task" else "Add Task",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp))

            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text(text = "Input your task ") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(0.2f), // Set the background color
                    focusedIndicatorColor = Color(0xFF189AB4).copy(0.2f), // Focused underline color
                    unfocusedIndicatorColor = Color.Transparent // No underline when unfocused
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ){
                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onAddTask(inputText)
                            inputText = ""
                        }else{
                            showToast = true
                        }
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.BottomEnd),
                    contentPadding = PaddingValues(0.dp),

                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF189AB4))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.uparrow),
                        contentDescription = "push task",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

        }
    }
}



@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, onCompleteChanged: (Boolean) -> Unit, onEdit: () -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onEdit() },
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
