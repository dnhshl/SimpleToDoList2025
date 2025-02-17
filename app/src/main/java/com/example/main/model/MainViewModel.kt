package com.example.main.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val snackbarHostState = SnackbarHostState()
    private val Context.dataStore by preferencesDataStore(name = "ui_state")
    private val dataStore = application.dataStore
    private val datastoreManager = DatastoreManager(dataStore)

    // Persistenter State

    private val _pState = MutableStateFlow(PersistantUiState())
    val pState: StateFlow<PersistantUiState> get() = _pState

    // non persistenter State

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state


    init {
        // Hier können "Beobachter" auf Zustandsänderungen initialisiert werden
        // z.B. um den UI-Zustand zu speichern oder um auf Änderungen zu reagieren

        // Lade den persistenten UI-Zustand
        viewModelScope.launch {
            datastoreManager.getPersistantState().collectLatest { persistedState ->
                _pState.value = persistedState
            }
            Log.i(">>>>>", "loading Preferences: ${_pState.value}")
        }


        // Überwache den persistant state und speichere ihn bei Änderungen
        viewModelScope.launch {
            _pState.collectLatest {
                val pState = _pState.value
                datastoreManager.savePersitantState(pState)
            }
        }

    }


    // Actions
    // ------------------------------------------------------------------------------

    fun addToList(todo: ToDoItem) {
        // selected ToDo updaten, falls es geändert wurde
        val selectedToDo = _state.value.selectedToDo ?: ToDoItem()
        if (selectedToDo.id == todo.id)
            _state.value = _state.value.copy(selectedToDo = todo)

        // Liste updaten
        val currentList = _pState.value.toDoList
        // ist das todo schon in der Liste?
        val index = currentList.indexOfFirst { it.id == todo.id }
        // überschreibe bestehendes todo oder füge neues hinzu
        val updatedList = when {
            index >= 0 -> currentList.map { if(it.id == todo.id) todo else it }
            else -> currentList + todo
        }
        _pState.value = _pState.value.copy(toDoList = updatedList)
    }

    fun removeFromList(todo: ToDoItem) {
        // Auswahl zurücksetzen, wenn ausgewählter Termin gelöscht werden soll
        val selectedToDo = _state.value.selectedToDo
        if (selectedToDo == todo)
            _state.value = _state.value.copy(selectedToDo = null)

        // Liste updaten
        val currentList = _pState.value.toDoList
        // kopiere alle Elemente, die ungleich dem zu löschenden Element sind
        val updatedList = currentList.filterNot { it == todo }
        _pState.value = _pState.value.copy(toDoList = updatedList)
    }

    fun setCurrentToDo(todo: ToDoItem) {
        _state.value = _state.value.copy(currentToDo = todo)
    }

    fun setSelectedToDo(todo: ToDoItem) {
        _state.value = _state.value.copy(selectedToDo = todo)
    }



    // Ab hier Helper Funktionen
    // ------------------------------------------------------------------------------


    // Snackbar
    // ------------------------------------------------------------------------------

    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        Log.i(">>>>>", "showSnackbar: $message")
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )
        }
    }


    // Zugriff auf String Ressourcen
    // ------------------------------------------------------------------------------
    private fun getStringRessource(resId: Int): String {
        return getApplication<Application>().getString(resId)
    }
}
