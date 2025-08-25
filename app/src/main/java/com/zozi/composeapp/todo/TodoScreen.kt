package com.zozi.composeapp.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zozi.composeapp.ui.theme.ComposeAppTheme
import com.zozi.shared.todos.Todo
import com.zozi.shared.todos.TodoRepository

@Composable
fun TodoScreen(repo: TodoRepository = TodoRepository()) {
    var items by remember { mutableStateOf<List<Todo>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        runCatching { repo.list(limit = 5) }
            .onSuccess { items = it }
            .onFailure { error = it.message ?: it::class.simpleName }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            error != null -> Text("Error: $error", modifier = Modifier.padding(16.dp))
            items == null -> Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
            else -> Column(Modifier.padding(16.dp)) {
                Text("Todos", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                items!!.forEach { Text("• ${it.title}") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAppTheme {
        TodoScreen()
    }
}
