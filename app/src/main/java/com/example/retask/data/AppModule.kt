package com.example.retask.data

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

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

    @Singleton
    @Binds
    abstract fun bindNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository

}