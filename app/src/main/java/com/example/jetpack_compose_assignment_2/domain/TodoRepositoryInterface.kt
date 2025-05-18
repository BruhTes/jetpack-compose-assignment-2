package com.example.jetpack_compose_assignment_2.domain

import kotlinx.coroutines.flow.Flow

interface TodoRepositoryInterface {
    fun getTodos(): Flow<List<Todo>>
    suspend fun refreshTodos()
    suspend fun clearTodos()
}
