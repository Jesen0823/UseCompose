package com.jesen.cleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jesen.cleanarchitecture.feature_note.presentation.add_edit_note.components.EditNoteDetailScreen
import com.jesen.cleanarchitecture.feature_note.presentation.notes.components.NoteListScreen

import com.jesen.cleanarchitecture.navigation.Screen
import com.jesen.cleanarchitecture.ui.theme.UseComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UseComposeTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteListScreen.route,
                    ) {

                        composable(route = Screen.NoteListScreen.route) {
                            NoteListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.EditNoteDetailScreen.route + "?noteId={noteId}&noteColor={noteColor}",

                            // 传参
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: 0xfffffff
                            EditNoteDetailScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
