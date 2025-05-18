package com.example.jetpack_compose_assignment_2.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpack_compose_assignment_2.ui.screens.todo_list.TodoListScreen
import com.example.jetpack_compose_assignment_2.ui.screens.todo_detail.TodoDetailScreen
import com.example.jetpack_compose_assignment_2.ui.screens.user_select.UserIdSelectionScreen

object Destinations {
    const val USER_SELECT = "user_select"
    const val TODO_LIST = "todo_list/{userId}"
    const val TODO_DETAIL = "todo_detail/{todoId}"
    const val ARG_USER_ID = "userId"
    const val ARG_TODO_ID = "todoId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.USER_SELECT) {
        composable(Destinations.USER_SELECT) {
            UserIdSelectionScreen(onUserSelected = { userId ->
                navController.navigate("todo_list/$userId")
            })
        }
        composable(
            route = Destinations.TODO_LIST,
            arguments = listOf(navArgument(Destinations.ARG_USER_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt(Destinations.ARG_USER_ID) ?: return@composable
            TodoListScreen(
                userId = userId,
                onTodoClick = { todoId ->
                    navController.navigate("todo_detail/$todoId")
                }
            )
        }
        composable(
            route = Destinations.TODO_DETAIL,
            arguments = listOf(navArgument(Destinations.ARG_TODO_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt(Destinations.ARG_TODO_ID) ?: return@composable
            TodoDetailScreen(
                todoId = todoId,
                onBack = { navController.popBackStack() }
            )
        }
    }
} 