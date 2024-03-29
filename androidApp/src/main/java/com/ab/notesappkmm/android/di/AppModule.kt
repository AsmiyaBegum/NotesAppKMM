package com.ab.notesappkmm.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.ab.notesappkmm.data.local.DatabaseDriverFactory
import com.ab.notesappkmm.domain.note.NoteDataSource
import com.ab.notesappkmm.data.note.SqlDelightNoteDataSource
import com.ab.notesappkmm.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(app : Application) : SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver) : NoteDataSource {
        return SqlDelightNoteDataSource(NotesDatabase(driver))
    }
}