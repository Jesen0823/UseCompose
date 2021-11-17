package com.jesen.cleanarchitecture.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jesen.cleanarchitecture.ui.theme.*

@Entity
data class NoteModel(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink, Purple200)
    }
}

// 异常处理
class InvalidNoteException(message: String) : Exception(message)