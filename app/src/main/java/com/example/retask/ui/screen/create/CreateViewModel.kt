package com.example.retask.ui.screen.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retask.data.NoteRepository
import com.example.retask.data.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<CreateUiState> = MutableStateFlow(CreateUiState())
    val uiState: StateFlow<CreateUiState> = _uiState.asStateFlow()

    private var newNoteId: Long = 0L
    private var addNewNoteJob: Job? = null
    private var updateNoteJob: Job? = null
    private var deleteNoteJob: Job? = null

    fun onNoteChange(updatedNote: Note) {
        _uiState.update { createUiState ->
            createUiState.copy(
                newNote = updatedNote
            )
        }
        if (updatedNote.title.isBlank() && updatedNote.content.isBlank()) {
            newNoteId = 0L
            deleteNoteJob?.cancel()
            deleteNoteJob = viewModelScope.launch {
                delay(500L)
                deleteNote(updatedNote)
            }
        } else if (newNoteId == 0L) {
            addNewNoteJob?.cancel()
            addNewNoteJob = viewModelScope.launch {
                delay(500L)
                addNewNote(updatedNote)
            }
        } else if (newNoteId > 0L) {
            updateNoteJob?.cancel()
            updateNoteJob = viewModelScope.launch {
                delay(500L)
                updateNote(updatedNote.copy(id = newNoteId))
            }
        }
    }

    private fun addNewNote(note: Note) {
        viewModelScope.launch {
            newNoteId = noteRepository.insert(note)
        }
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note)
        }
    }

    fun clearNote() {
        _uiState.update { createUiState ->
            createUiState.copy(
                newNote = Note()
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        addNewNoteJob?.cancel()
        updateNoteJob?.cancel()
        deleteNoteJob?.cancel()
    }
}

data class CreateUiState(
    val newNote: Note = Note(),
)