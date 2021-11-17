package com.jesen.cleanarchitecture.feature_note.data.repository

import com.jesen.cleanarchitecture.feature_note.data.data_source.NoteDao
import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<NoteModel>> {
        return dao.getNotes()
    }

    override suspend fun insertNote(note: NoteModel) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: NoteModel) {
        dao.deleteNote(note)
    }
}