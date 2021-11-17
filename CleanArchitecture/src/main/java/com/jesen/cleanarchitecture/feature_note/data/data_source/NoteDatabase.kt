package com.jesen.cleanarchitecture.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel

@Database(
    entities = [NoteModel::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes.db"
    }
}