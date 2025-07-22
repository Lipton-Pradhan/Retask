package com.example.retask.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retask.ui.component.AppDestination
import com.example.retask.ui.screen.create.CreateScreen
import com.example.retask.ui.screen.home.HomeScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestination.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestination.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(destination.contentDescription)
                        )
                    },
                    label = { Text(stringResource(destination.label)) },
                    selected = destination == currentDestination,
                    onClick = {
                        if (destination != currentDestination) {
                            currentDestination = destination
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) {
        when (currentDestination) {
            AppDestination.HOME -> {
                HomeScreen(modifier = Modifier.fillMaxSize())
            }

            AppDestination.CREATE -> {
                CreateScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}