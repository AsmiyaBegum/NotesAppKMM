package com.ab.notesappkmm.android.note_list

import com.ab.notesappkmm.domain.Note

data class NoteListState (
    val notes : List<Note>  = emptyList(),
    val searchText : String = "",
    val isSearchActive : Boolean = false
)
