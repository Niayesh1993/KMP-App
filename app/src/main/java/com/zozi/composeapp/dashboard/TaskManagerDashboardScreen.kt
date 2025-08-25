package com.zozi.composeapp.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp


//@Composable
//fun TaskManagerApp(viewModel: TaskViewModel = viewModel()) {
//    val navController = rememberNavController()
//    val tasks by viewModel.tasks.collectAsState()
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("Task Manager") }) },
//        bottomBar = {
//            BottomNavigation {
//                BottomNavigationItem(
//                    selected = true,
//                    onClick = { /* show list */ },
//                    icon = { Icon(Icons.Default.List, contentDescription = null) },
//                    label = { Text("Tasks") }
//                )
//                BottomNavigationItem(
//                    selected = false,
//                    onClick = { /* profile */ },
//                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
//                    label = { Text("Profile") }
//                )
//            }
//        }
//    ) { padding ->
//        NavHost(navController, startDestination = "list", modifier = Modifier.padding(padding)) {
//            composable("list") {
//                TaskList(tasks) { navController.navigate("detail/${it.id}") }
//            }
//            composable(
//                route = "detail/{taskId}",
//                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
//            ) { backStackEntry ->
//                val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
//                val task = tasks.firstOrNull { it.id == taskId }
//                TaskDetail(task, onBack = { navController.popBackStack() })
//            }
//        }
//    }
//}


@Composable
fun TaskList(tasks: List<Task>, onSelect: (Task) -> Unit) {
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            TaskRow(task, onClick = { onSelect(task) })
        }
    }
}

@Composable
fun TaskRow(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                task.description?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            }
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun TaskDetail(task: Task?, onBack: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(targetValue = if (expanded) 1f else 0f)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
        task?.let {
            Text(it.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            it.description?.let { text -> Text(text) }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { expanded = !expanded }) {
                Text(if (expanded) "Hide extra info" else "Show extra info")
            }
            AnimatedVisibility(visible = expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alphaAnim)
                        .padding(top = 8.dp)
                ) {
                    Text("Extra details about the task…")
                }
            }
        }
    }
}
