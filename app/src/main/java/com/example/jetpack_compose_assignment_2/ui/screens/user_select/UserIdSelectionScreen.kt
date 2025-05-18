package com.example.jetpack_compose_assignment_2.ui.screens.user_select

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserIdSelectionScreen(onUserSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedUserId by remember { mutableStateOf<Int?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Select a User ID", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedUserId?.toString() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("User ID") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    (1..10).forEach { userId ->
                        DropdownMenuItem(
                            text = { Text("User $userId") },
                            onClick = {
                                selectedUserId = userId
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { selectedUserId?.let { onUserSelected(it) } },
                enabled = selectedUserId != null
            ) {
                Text("Continue")
            }
        }
    }
} 