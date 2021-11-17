package com.jesen.cleanarchitecture.feature_note.presentation.notes

import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.util.NoteOrder
import com.jesen.cleanarchitecture.feature_note.domain.util.OrderType

sealed class NotesEvent {

    data class Order(val noteOrder: NoteOrder) : NotesEvent()

    data class DeleteNote(val note: NoteModel) : NotesEvent()

    object RestoreNote : NotesEvent()

    object ToggleOrderSection : NotesEvent()
}