package com.zozi.shared.todos


class TodoRepository(
    private val api: TodoApi = TodoApi()
) {
    suspend fun list(limit: Int = 5): List<Todo> = api.fetchTodos(limit)
}
