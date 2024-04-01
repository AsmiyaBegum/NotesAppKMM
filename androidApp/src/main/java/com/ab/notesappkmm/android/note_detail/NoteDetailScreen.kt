package com.ab.notesappkmm.android.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NoteDetailScreen(
    noteId : Long,
    navController: NavController,
    viewModel: NoteDetailViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNotBeenSaved.collectAsState()
    
    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if(hasNoteBeenSaved){
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveNote()
                          },
                containerColor = Color.Black
            ) {
                
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = Color.White)
            }
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                onValueChanged = viewModel::onNoteTitleChanged,
                isHintVisible = state.isNoteTitleHintVisible,
                singleLine = true ,
                textStyle = TextStyle(fontSize = 20.sp),
                onFocusChanged = { state ->
                    viewModel.onNoteTitleFocusChanged(state.isFocused)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter a note content",
                onValueChanged = viewModel::onNoteContentChanged,
                isHintVisible = state.isNoteContentHintVisible,
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                onFocusChanged = { state ->
                    viewModel.onNoteContentFocusChanged(state.isFocused)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }


}