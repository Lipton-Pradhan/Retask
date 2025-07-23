package com.example.retask.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retask.data.NoteRepository
import com.example.retask.data.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var noteList: StateFlow<List<Note>> = noteRepository.getAllNotes().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private var selectedNoteList: MutableList<Note> = mutableListOf()
    private var addNewNoteJob: Job? = null
    private var updateNoteJob: Job? = null
    private var deleteNoteJob: Job? = null
    private var newNoteId: Long = -1L
    private var oldNoteId: Long = 0L

    init {
        viewModelScope.launch {
            noteList.collectLatest { noteList ->
                _uiState.update { mainUiState ->
                    mainUiState.copy(
                        noteList = noteList
                    )
                }
            }
        }
    }

    fun onNoteChange(updatedNote: Note) {
        // This if checks if the updated note has a different ID than the last processed note,
        // which can happen when a new note is clicked from the list. If that happens, then this
        // if-block resets the oldNoteId and newNoteId to ensure that the correct note is updated
        // or deleted.
        if (updatedNote.id != oldNoteId) {
            oldNoteId = updatedNote.id
            newNoteId = -1L
        }

        // First if-block:
        // This if-block checks if the updated note is empty (both title and content are blank) when
        // an existing note from the list is being edited which means newNoteId is less than 0. When
        // this happens, the note is deleted after a delay of 500 milliseconds. A delay is used to
        // prevent numerous deletions when the user is continuously pressing the space bar.
        //
        // First else-if block:
        // This else-if block checks if the updated note is empty (both title and content are blank)
        // when an newly created note is being edited which means newNoteId is greater than 0. When
        // this happens, the note is deleted after a delay of 500 milliseconds. A delay is used to
        // prevent numerous deletions when the user is continuously pressing the space bar.
        //
        // Second else-if block:
        // This else-if block checks if the newNoteId is 0, which means a new note is being created.
        // When this happens, the note is added after a delay of 500 milliseconds. A delay is added
        // to prevent numerous additions when the user is continuously typing new characters other
        // than blank spaces.
        //
        // Third else-if block:
        // This else-if block checks if the newNoteId is greater than 0, which means a newly
        // created note is being edited. When this happens, the note is updated after a delay
        // of 500 milliseconds. A delay is added to prevent numerous updates when the user is
        // continuously typing new characters other than blank spaces.
        //
        // Last else block:
        // This else block is used to update an existing note from the list. When this happens,
        // the note is updated after a delay of 500 milliseconds. A delay is added to prevent
        // numerous updates when the user is continuously typing new characters other than blank
        // spaces.
        if (updatedNote.title.isBlank() && updatedNote.content.isBlank() && newNoteId < 0L) {
            newNoteId = 0L
            deleteNoteJob?.cancel()
            deleteNoteJob = viewModelScope.launch {
                delay(500L)
                deleteNote(updatedNote)
            }
        } else if (updatedNote.title.isBlank() && updatedNote.content.isBlank() && newNoteId > 0L) {
            deleteNoteJob?.cancel()
            deleteNoteJob = viewModelScope.launch {
                delay(500L)
                deleteNote(updatedNote.copy(id = newNoteId))
            }
        }
        else if (newNoteId == 0L) {
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
        } else {
            oldNoteId = updatedNote.id
            updateNoteJob?.cancel()
            updateNoteJob = viewModelScope.launch {
                delay(500L)
                updateNote(updatedNote)
            }
        }
    }

    fun onNoteSelect(note: Note) {
        if (selectedNoteList.contains(note)) {
            selectedNoteList.remove(note)
        } else {
            selectedNoteList.add(note)
        }
        _uiState.update { mainUiState ->
            mainUiState.copy(
                selectedNoteList = selectedNoteList.toList()
            )
        }
    }

    fun clearSelectedNoteList() {
        selectedNoteList.clear()
        _uiState.update { mainUiState ->
            mainUiState.copy(
                selectedNoteList = selectedNoteList.toList()
            )
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

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note)
        }
    }

    fun deleteSelectedNotes(selectedNoteList: List<Note>) {
        viewModelScope.launch {
            noteRepository.delete(selectedNoteList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        addNewNoteJob?.cancel()
        updateNoteJob?.cancel()
    }
}

data class HomeUiState(
    val noteList: List<Note> = listOf(),
    val selectedNoteList: List<Note> = listOf()
)