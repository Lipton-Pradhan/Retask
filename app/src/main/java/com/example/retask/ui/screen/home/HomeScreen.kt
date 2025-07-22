package com.example.retask.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.retask.R
import com.example.retask.data.model.Note
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Note>()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (uiState.selectedNoteList.isNotEmpty()) {
                TopAppBar(
                    title = { Text(text = "${uiState.selectedNoteList.size} selected") },
                    navigationIcon = {
                        IconButton(
                            onClick = { viewModel.clearSelectedNoteList() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear_selection)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.deleteSelectedNotes(uiState.selectedNoteList) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_notes)
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        NavigableListDetailPaneScaffold(
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane {
                    NoteList(
                        noteList = uiState.noteList,
                        selectedNoteList = uiState.selectedNoteList,
                        isSelectModeActive = uiState.selectedNoteList.isNotEmpty(),
                        onNoteClick = { note ->
                            scope.launch {
                                scaffoldNavigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    contentKey = note
                                )
                            }
                        },
                        onNoteSelect = { note ->
                            viewModel.onNoteSelect(note)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    scaffoldNavigator.currentDestination?.contentKey?.let { note ->
                        NoteDetail(
                            note = note,
                            onNoteChange = viewModel::onNoteChange,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}