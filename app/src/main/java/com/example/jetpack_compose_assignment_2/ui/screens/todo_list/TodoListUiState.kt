package com.example.jetpack_compose_assignment_2.ui.screens.todo_list

import com.example.jetpack_compose_assignment_2.domain.Todo

sealed class TodoListUiState {
    object Loading : TodoListUiState()
    data class Success(val todos: List<Todo>) : TodoListUiState()
    data class Error(val message: String) : TodoListUiState()
} 