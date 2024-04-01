package com.ab.notesappkmm.android.note_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    text : String,
    hint : String,
    onValueChanged : (String) -> Unit,
    isHintVisible : Boolean,
    singleLine : Boolean,
    textStyle : TextStyle,
    onFocusChanged : (FocusState) -> Unit,
    modifier: Modifier = Modifier
){

    Box(
      modifier = modifier
    ){
        BasicTextField(
            value = text,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    onFocusChanged
                }
        )

        if(isHintVisible){
            Text(
                text = hint,
                color = Color.DarkGray
            )
        }
    }
}