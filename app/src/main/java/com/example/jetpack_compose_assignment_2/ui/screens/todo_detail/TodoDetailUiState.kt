package com.example.jetpack_compose_assignment_2.ui.screens.todo_detail

import com.example.jetpack_compose_assignment_2.domain.Todo

sealed class TodoDetailUiState {
    object Loading : TodoDetailUiState()
    data class Success(val todo: Todo) : TodoDetailUiState()
    data class Error(val message: String) : TodoDetailUiState()
} 