package com.example.main.model

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID


// Datenklassen für den UI-Zustand
// ----------------------------------------------------------------

// Datenklasse für ein To-Do-Item
@Serializable
data class ToDoItem(
    val id: String = UUID.randomUUID().toString(), // a unique random string
    val title: String = "",       // title of the to do item
    val subject: String = "",      // subject of the to do item
    val dateString: String = LocalDate.now().format(dateFormatter),
    val timeString: String = LocalTime.now().format(timeFormatter),
)

// Persistenter UI-Zustand
@Serializable
data class PersistantUiState(
    val toDoList: List<ToDoItem> = emptyList()
)

// Nicht persistenter UI-Zustand
data class UiState(
    val currentToDo: ToDoItem = ToDoItem(),
    val selectedToDo: ToDoItem? = null,
)



