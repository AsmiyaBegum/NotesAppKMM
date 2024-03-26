package com.ab.notesappkmm.data.note1

import com.ab.notesappkmm.DateTimeUtil
import com.ab.notesappkmm.db.NotesDatabase
import com.ab.notesappkmm.domain.Note
import com.ab.notesappkmm.domain.note.NoteDataSource

class SqlDelightNoteDataSource(db : NotesDatabase) : NoteDataSource {

    private val queries = db.notesQueries
    override suspend fun insertNote(note: Note) {
       queries.insertNote(
           note.id,
           note.title,
           note.content,
           note.colorHex,
           DateTimeUtil.toEpochMillis(note.created)
       )
    }

    override suspend fun getNoteById(id: Long): Note? {
       return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map {
                it.toNote()
            }
    }

    override suspend fun deleteNoteById(id: Long) {
       return queries.deleteNoteById(id)
    }
}