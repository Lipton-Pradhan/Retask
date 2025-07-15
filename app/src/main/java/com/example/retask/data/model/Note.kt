package com.example.retask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var content: String = "",
)