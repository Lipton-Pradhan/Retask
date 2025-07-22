package com.example.retask.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.retask.R
import kotlinx.serialization.Serializable


@Serializable
object User

@Serializable
object Main

enum class AppDestination(
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int
) {
    HOME(
        label = R.string.home_destination,
        icon = Icons.Default.Home,
        contentDescription = R.string.home_description
    ),

    CREATE(
        label = R.string.new_destination,
        icon = Icons.Default.Add,
        contentDescription = R.string.new_description
    )
}