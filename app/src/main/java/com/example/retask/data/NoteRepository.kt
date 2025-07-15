package com.example.retask.data

import com.example.retask.data.model.Note
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllNotes(): Flow<List<Note>>

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

}

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun getAllNotes() = noteDao.getAllNotes()

    override suspend fun insert(note: Note) = noteDao.insert(note)

    override suspend fun update(note: Note) = noteDao.update(note)

    override suspend fun delete(note: Note) = noteDao.delete(note)

}