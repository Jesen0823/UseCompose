package com.jesen.cleanarchitecture.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesen.cleanarchitecture.feature_note.domain.util.NoteOrder
import com.jesen.cleanarchitecture.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Data(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 按什么规则排序
        DefaultRadioButton(
            text = "Title",
            selected = noteOrder is NoteOrder.Title,
            onSelected = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.width(8.dp))

        DefaultRadioButton(
            text = "Data",
            selected = noteOrder is NoteOrder.Data,
            onSelected = { onOrderChange(NoteOrder.Data(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.width(8.dp))

        DefaultRadioButton(
            text = "Color",
            selected = noteOrder is NoteOrder.Color,
            onSelected = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        // 升降排序
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelected = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelected = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}