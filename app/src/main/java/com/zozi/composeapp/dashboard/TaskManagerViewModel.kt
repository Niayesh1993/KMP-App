package com.zozi.composeapp.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskManagerViewModel(): ViewModel() {

     private val _tasks = MutableStateFlow(
         listOf(
             Task("1", "Finish report", "Complete the quarterly report"),
             Task("2", "Team meeting", "Discuss roadmap with team"),
             Task("3", "Review PRs", "Go through pull requests in repository")
         ))

    val tasks: StateFlow<List<Task>> = _tasks
}


data class Task(val id: String, val title: String, val description: String?)