package com.jesen.cleanarchitecture.feature_note.domain.use_case

import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.repository.NoteRepository

class GetSingleNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): NoteModel? {
        return repository.getNoteById(id)
    }
}