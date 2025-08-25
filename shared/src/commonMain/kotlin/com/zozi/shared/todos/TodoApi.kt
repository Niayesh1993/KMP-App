package com.zozi.shared.todos


import com.zozi.shared.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class TodoApi {
    suspend fun fetchTodos(limit: Int = 5): List<Todo> {
        val dto: List<TodoDto> = httpClient.get("https://jsonplaceholder.typicode.com/todos") {
            url {
                parameters.append("_limit", limit.toString())
            }
            accept(ContentType.Application.Json)
        }.body()
        return dto.map { it.toDomain() }
    }
}
