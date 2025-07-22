package com.example.retask.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retask.data.model.Note

@Composable
fun NoteList(
    noteList: List<Note>,
    selectedNoteList: List<Note>,
    isSelectModeActive: Boolean,
    onNoteClick: (Note) -> Unit,
    onNoteSelect: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
//    LazyColumn(modifier = modifier) {
//        items(items = noteList, key = { it.id }) { note ->
//            NoteCard(
//                note = note,
//                onClick = { onNoteClick(note) },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(120.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(items = noteList, key = { it.id }) { note ->
                NoteCard(
                    note = note,
                    isSelectModeActive = isSelectModeActive,
                    isSelected = selectedNoteList.contains(note),
                    onClick = { onNoteClick(note) },
                    onLongClick = { onNoteSelect(note) },
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun NoteCard(
    note: Note,
    isSelectModeActive: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = if (isSelected) BorderStroke(4.dp, Color.Cyan) else null,
        modifier = modifier
            .combinedClickable(
                onClick = if (!isSelectModeActive) onClick else onLongClick,
                onLongClick = onLongClick
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = note.title, modifier = Modifier.fillMaxWidth())
            Text(text = note.content, modifier = Modifier.fillMaxWidth())
        }
    }
}