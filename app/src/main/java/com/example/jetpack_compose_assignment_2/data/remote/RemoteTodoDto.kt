package com.example.jetpack_compose_assignment_2.data.remote

data class RemoteTodoDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
) 