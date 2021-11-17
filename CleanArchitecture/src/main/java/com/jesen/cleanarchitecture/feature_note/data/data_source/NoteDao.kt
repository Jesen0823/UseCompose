package com.jesen.cleanarchitecture.feature_note.data.data_source

import androidx.room.*
import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM noteModel")
    fun getNotes(): Flow<List<NoteModel>>

    @Query("SELECT * FROM noteModel WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteModel: NoteModel)

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)
}