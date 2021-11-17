package com.jesen.cleanarchitecture.feature_note.domain.repository

import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<NoteModel>>

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)
}