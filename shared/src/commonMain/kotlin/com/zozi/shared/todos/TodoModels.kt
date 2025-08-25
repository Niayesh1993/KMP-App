package com.zozi.shared.todos


import kotlinx.serialization.Serializable

@Serializable
internal data class TodoDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean? = null
)

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

internal fun TodoDto.toDomain() = Todo(
    userId = userId,
    id = id,
    title = title,
    completed = completed ?: false
)
