package com.jesen.cleanarchitecture.feature_note.domain.use_case

import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.repository.NoteRepository
import com.jesen.cleanarchitecture.feature_note.domain.util.NoteOrder
import com.jesen.cleanarchitecture.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Data(OrderType.Descending)
    ): Flow<List<NoteModel>> {
        return repository.getNotes().map { notes ->
            // 各种排序：按标题长度、按时间、按颜色
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.length }
                        is NoteOrder.Data -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.length }
                        is NoteOrder.Data -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}