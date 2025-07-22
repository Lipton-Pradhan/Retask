package com.example.retask.ui.screen.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CreateScreen(modifier: Modifier = Modifier) {
    val viewModel: CreateViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearNote()
        }
    }

    Scaffold(modifier = modifier) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            TextField(
                value = uiState.newNote.title,
                onValueChange = { newTitle ->
                    viewModel.onNoteChange(
                        uiState.newNote.copy(
                            title = newTitle,
                            content = uiState.newNote.content
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = uiState.newNote.content,
                onValueChange = { newContent ->
                    viewModel.onNoteChange(
                        uiState.newNote.copy(
                            title = uiState.newNote.title,
                            content = newContent
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Content") }
            )
        }
    }
}