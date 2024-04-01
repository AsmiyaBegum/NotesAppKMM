package com.ab.notesappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.notesappkmm.DateTimeUtil
import com.ab.notesappkmm.domain.Note
import com.ab.notesappkmm.domain.note.NoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor", Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { noteTitle, isNoteTitleFocused, noteContent,  isNoteContentFocused, noteColor ->

        NoteDetailState(
            noteTitle = noteTitle,
            isNoteTitleHintVisible = noteTitle.isEmpty() && !isNoteTitleFocused,
            noteContent =noteContent,
            isNoteContentHintVisible = noteContent.isEmpty() && !isNoteContentFocused,
            noteColor = noteColor
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())


    private val _hasNotBeenSaved = MutableStateFlow(false)
    val hasNotBeenSaved = _hasNotBeenSaved.asStateFlow()

    private var existingNoteId : Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let{ existingNoteId ->

            if(existingNoteId == -1L){
                return@let
            }

            this.existingNoteId = existingNoteId

            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] =  note.colorHex
                }
            }
        }
    }


    fun onNoteTitleChanged(title : String){
        savedStateHandle["noteTitle"] = title
    }


    fun onNoteContentChanged(content : String){
        savedStateHandle["noteContent"] = content
    }

    fun onNoteTitleFocusChanged(isFocused : Boolean){
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused : Boolean){
        savedStateHandle["isNoteContentFocused"] = isFocused
    }


    fun saveNote(){
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id =  existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )

            _hasNotBeenSaved.value = true
        }
    }
}