package com.jesen.cleanarchitecture.navigation

sealed class Screen(
    val route: String
) {
    object NoteListScreen : Screen("list_page_route")
    object EditNoteDetailScreen : Screen("detail_page_route")
}
