package com.example.jetpack_compose_assignment_2.ui.screens.todo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_assignment_2.domain.TodoRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoDetailUiState>(TodoDetailUiState.Loading)
    val uiState: StateFlow<TodoDetailUiState> = _uiState

    fun loadTodo(todoId: Int) {
        _uiState.value = TodoDetailUiState.Loading
        viewModelScope.launch {
            try {
                val todo = repository.getTodos()
                    .catch { e ->
                        _uiState.value = TodoDetailUiState.Error(e.message ?: "Unknown error")
                    }
                    .firstOrNull()
                    ?.find { it.id == todoId }
                if (todo != null) {
                    _uiState.value = TodoDetailUiState.Success(todo)
                } else {
                    _uiState.value = TodoDetailUiState.Error("Todo not found")
                }
            } catch (e: Exception) {
                _uiState.value = TodoDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteTodo(todoId: Int, onDeleted: () -> Unit) {
        viewModelScope.launch {
            if (repository is com.example.jetpack_compose_assignment_2.data.TodoRepository) {
                repository.deleteTodoById(todoId)
                onDeleted()
            }
        }
    }

    fun updateTodoCompleted(todo: com.example.jetpack_compose_assignment_2.domain.Todo, completed: Boolean) {
        viewModelScope.launch {
            if (repository is com.example.jetpack_compose_assignment_2.data.TodoRepository) {
                val updated = com.example.jetpack_compose_assignment_2.data.local.TodoEntity(
                    id = todo.id,
                    userId = todo.userId,
                    title = todo.title,
                    completed = completed
                )
                repository.updateTodo(updated)
                loadTodo(todo.id)
            }
        }
    }

    fun updateTodoTitle(todo: com.example.jetpack_compose_assignment_2.domain.Todo, newTitle: String) {
        viewModelScope.launch {
            if (repository is com.example.jetpack_compose_assignment_2.data.TodoRepository) {
                val updated = com.example.jetpack_compose_assignment_2.data.local.TodoEntity(
                    id = todo.id,
                    userId = todo.userId,
                    title = newTitle,
                    completed = todo.completed
                )
                repository.updateTodo(updated)
                loadTodo(todo.id)
            }
        }
    }
} 