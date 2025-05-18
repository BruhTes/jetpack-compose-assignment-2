package com.example.jetpack_compose_assignment_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jetpack_compose_assignment_2.ui.AppNavigation
import com.example.jetpack_compose_assignment_2.ui.theme.Jetpackcomposeassignment2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpackcomposeassignment2Theme {
                AppNavigation()
            }
        }
    }
}