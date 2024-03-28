package com.ab.notesappkmm.domain.note

import com.ab.notesappkmm.DateTimeUtil
import com.ab.notesappkmm.domain.Note

class SearchNotes {
    fun execute(
        notes: List<Note>,
        query: String,
    ): List<Note> {
        if (query.isBlank()) {
            return notes
        }
        return notes.filter {
            it.title.lowercase().contains(query.lowercase()) ||
                it.content.lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}
