package com.example.retask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retask.data.model.Note

@Database(entities = [Note::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}
