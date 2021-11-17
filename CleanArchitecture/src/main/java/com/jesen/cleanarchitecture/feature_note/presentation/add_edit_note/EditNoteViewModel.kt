package com.jesen.cleanarchitecture.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesen.cleanarchitecture.feature_note.domain.model.InvalidNoteException
import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.domain.repository.NoteRepository
import com.jesen.cleanarchitecture.feature_note.domain.use_case.NoteUseCase
import com.jesen.cleanarchitecture.feature_note.presentation.notes.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currNoteId: Int? = null

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "标题..."
        )
    )
    private val noteTitleState: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "记录内容..."
        )
    )
    val noteContentState: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(NoteModel.noteColors.random().toArgb())
    private val noteColorState = _noteColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCase.getNote(noteId)?.also { note ->
                        currNoteId = note.id
                        _noteTitle.value = noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }

                }
            }

        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.PutTitle -> {
                _noteTitle.value = noteTitleState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.PutContent -> {
                _noteContent.value = noteContentState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteTitleState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContentState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteContentState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = noteColorState.value
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.addNote(
                            NoteModel(
                                title = noteTitleState.value.text,
                                content = noteContentState.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColorState.value,
                                id = currNoteId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                message = e.message.toString()
                            )
                        )
                    }

                }
            }
        }
    }

    sealed class UIEvent {

        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveNote : UIEvent()

    }
}