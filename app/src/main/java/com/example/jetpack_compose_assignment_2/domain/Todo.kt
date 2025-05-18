package com.example.jetpack_compose_assignment_2.domain

data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)

