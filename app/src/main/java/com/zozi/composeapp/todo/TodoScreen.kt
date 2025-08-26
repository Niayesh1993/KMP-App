package com.zozi.composeapp.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zozi.shared.todos.Todo
import com.zozi.shared.todos.TodoRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(repo: TodoRepository = TodoRepository()) {
    var items by remember { mutableStateOf<List<Todo>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val checkedState = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(Unit) {
        runCatching { repo.list(limit = 5) }
            .onSuccess {
                items = it
                // Initialize checked state
                it.forEach { todo -> checkedState[todo.id] = todo.completed }
            }
            .onFailure { error = it.message ?: it::class.simpleName }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Todos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Error: $error",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        // You could add a retry button here
                    }
                }

                items == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                items!!.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No todos yet!",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items!!) { todo ->
                            TodoItemRow(
                                todo = todo,
                                isChecked = checkedState[todo.id] ?: false,
                                onCheckedChange = { newCheckedState ->
                                    checkedState[todo.id] = newCheckedState
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemRow(
    todo: Todo,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}