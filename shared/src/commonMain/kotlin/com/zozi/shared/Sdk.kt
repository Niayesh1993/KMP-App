package com.zozi.shared

import com.zozi.shared.todos.Todo
import com.zozi.shared.todos.TodoRepository


class Sdk(
    private val repo: TodoRepository = TodoRepository()
) {
    suspend fun fetchTodos(limit: Int = 5): List<Todo> = repo.list(limit)
}
