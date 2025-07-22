package com.example.retask.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retask.data.model.Note

@Composable
fun NoteDetail(
    note: Note,
    onNoteChange: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by rememberSaveable(note.id) { mutableStateOf(note.title) }
    var content by rememberSaveable(note.id) { mutableStateOf(note.content) }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { newTitle ->
                title = newTitle
                onNoteChange(note.copy(title = newTitle, content = content))
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = content,
            onValueChange = { newContent ->
                content = newContent
                onNoteChange(note.copy(title = title, content = newContent))
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}