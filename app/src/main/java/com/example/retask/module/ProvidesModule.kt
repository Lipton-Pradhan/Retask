package com.example.retask.module

import android.content.Context
import androidx.room.Room
import com.example.retask.data.LocalDatabase
import com.example.retask.data.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Singleton
    @Provides
    fun provideLocalDatabase(
        @ApplicationContext context: Context
    ): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "retask_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(
        localDatabase: LocalDatabase
    ): NoteDao {
        return localDatabase.noteDao()
    }
}