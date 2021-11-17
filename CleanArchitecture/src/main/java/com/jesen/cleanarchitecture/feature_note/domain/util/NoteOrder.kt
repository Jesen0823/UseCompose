package com.jesen.cleanarchitecture.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {

    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Data(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Data -> Data(orderType)
            is Color -> Color(orderType)
        }
    }

}
