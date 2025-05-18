package com.example.jetpack_compose_assignment_2.ui.screens.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_assignment_2.domain.TodoRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Loading)
    val uiState: StateFlow<TodoListUiState> = _uiState

    init {
        println("TodoListViewModel created")
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            repository.getTodos()
                .catch { e ->
                    _uiState.value = TodoListUiState.Error(e.message ?: "Unknown error")
                }
                .collectLatest { todos ->
                    println("Todos received: $todos")
                    _uiState.value = TodoListUiState.Success(todos)
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = TodoListUiState.Loading
            try {
                repository.refreshTodos()
            } catch (e: Exception) {
                _uiState.value = TodoListUiState.Error(e.message ?: "Failed to refresh")
            }
        }
    }

    fun addTodo(userId: Int, title: String, completed: Boolean) {
        viewModelScope.launch {
            // Generate a unique ID (for demo, use current time)
            val newId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
            val todo = com.example.jetpack_compose_assignment_2.data.local.TodoEntity(
                id = newId,
                userId = userId,
                title = title,
                completed = completed
            )
            if (repository is com.example.jetpack_compose_assignment_2.data.TodoRepository) {
                repository.insertTodo(todo)
            }
        }
    }
} 