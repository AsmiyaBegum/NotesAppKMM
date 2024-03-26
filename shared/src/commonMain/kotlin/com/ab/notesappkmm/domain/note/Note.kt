package com.ab.notesappkmm.domain

import com.ab.notesappkmm.presentation.BabyBlueHex
import com.ab.notesappkmm.presentation.LightGreenHex
import com.ab.notesappkmm.presentation.RedOrangeHex
import com.ab.notesappkmm.presentation.RedPinkHex
import com.ab.notesappkmm.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id : Long?,
    val title : String,
    val content : String,
    val colorHex : Long,
    val created : LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex,RedPinkHex,BabyBlueHex,VioletHex,LightGreenHex )

        fun generateRandomColor() = colors.random()
    }
}

