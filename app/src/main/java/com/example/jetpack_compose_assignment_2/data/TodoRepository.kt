package com.example.jetpack_compose_assignment_2.data

import com.example.jetpack_compose_assignment_2.data.local.TodoDao
import com.example.jetpack_compose_assignment_2.data.local.TodoEntity
import com.example.jetpack_compose_assignment_2.data.remote.TodoApi
import com.example.jetpack_compose_assignment_2.domain.Todo
import com.example.jetpack_compose_assignment_2.domain.TodoRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepository(
    private val api: TodoApi,
    private val dao: TodoDao
) : TodoRepositoryInterface {
    override fun getTodos(): Flow<List<Todo>> =
        dao.getAllTodos().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refreshTodos() {
        val remoteTodos = api.getTodos()
        val entities = remoteTodos.map {
            TodoEntity(
                id = it.id,
                userId = it.userId,
                title = it.title,
                completed = it.completed
            )
        }
        dao.clearTodos()
        dao.insertTodos(entities)
    }

    override suspend fun clearTodos() = dao.clearTodos()

    suspend fun insertTodo(todo: TodoEntity) = dao.insertTodo(todo)

    suspend fun deleteTodoById(todoId: Int) = dao.deleteTodoById(todoId)

    suspend fun updateTodo(todo: TodoEntity) = dao.updateTodo(todo)
}

private fun TodoEntity.toDomain(): Todo =
    Todo(id = id, userId = userId, title = title, completed = completed) 