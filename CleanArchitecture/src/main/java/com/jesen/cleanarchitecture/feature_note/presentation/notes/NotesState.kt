package com.jesen.cleanarchitecture.feature_note.presentation.notes

import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.util.NoteOrder
import com.jesen.cleanarchitecture.feature_note.domain.util.OrderType

data class NotesState(

    val notes: List<NoteModel> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Data(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)