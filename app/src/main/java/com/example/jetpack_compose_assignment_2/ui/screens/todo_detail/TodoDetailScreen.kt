package com.example.jetpack_compose_assignment_2.ui.screens.todo_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    todoId: Int,
    onBack: () -> Unit,
    viewModel: TodoDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load the todo when entering the screen
    androidx.compose.runtime.LaunchedEffect(todoId) {
        viewModel.loadTodo(todoId)
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Detail", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding), contentAlignment = Alignment.Center) {
            when (uiState) {
                is TodoDetailUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is TodoDetailUiState.Error -> {
                    Text(
                        text = (uiState as TodoDetailUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is TodoDetailUiState.Success -> {
                    val todo = (uiState as TodoDetailUiState.Success).todo
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = todo.title,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                editedTitle = todo.title
                                showEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit Title")
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Completed:", fontWeight = FontWeight.SemiBold)
                            Checkbox(
                                checked = todo.completed,
                                onCheckedChange = { checked -> viewModel.updateTodoCompleted(todo, checked) },
                                enabled = true
                            )
                        }
                        Text("User ID: ${todo.userId}")
                        Text("Todo ID: ${todo.id}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.deleteTodo(todo.id) {
                                    onBack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Delete", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                    if (showEditDialog) {
                        AlertDialog(
                            onDismissRequest = { showEditDialog = false },
                            title = { Text("Edit Todo Title") },
                            text = {
                                OutlinedTextField(
                                    value = editedTitle,
                                    onValueChange = { editedTitle = it },
                                    label = { Text("Title") }
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    if (editedTitle.isNotBlank()) {
                                        viewModel.updateTodoTitle(todo, editedTitle)
                                        showEditDialog = false
                                    }
                                }) { Text("Save") }
                            },
                            dismissButton = {
                                Button(onClick = { showEditDialog = false }) { Text("Cancel") }
                            }
                        )
                    }
                }
            }
        }
    }
} 