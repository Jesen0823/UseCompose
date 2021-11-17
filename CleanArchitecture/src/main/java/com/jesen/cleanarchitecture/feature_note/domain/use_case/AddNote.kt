package com.jesen.cleanarchitecture.feature_note.domain.use_case

import com.jesen.cleanarchitecture.feature_note.domain.model.InvalidNoteException
import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NoteModel) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("title of note can‘t be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("content of note can‘t be empty")
        }
        repository.insertNote(note)
    }
}