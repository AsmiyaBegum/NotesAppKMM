package com.ab.notesappkmm.data.di

import com.ab.notesappkmm.data.local.DatabaseDriverFactory
import com.ab.notesappkmm.data.note.SqlDelightNoteDataSource
import com.ab.notesappkmm.db.NotesDatabase
import com.ab.notesappkmm.domain.note.NoteDataSource

class DatabaseModule {
    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NotesDatabase(factory.createDriver()))
    }
}