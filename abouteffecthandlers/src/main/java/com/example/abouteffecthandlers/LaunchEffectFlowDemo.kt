package com.example.abouteffecthandlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@InternalCoroutinesApi
@Composable
fun LaunchedEffectFlowDemo(
    viewModel: LaunchedEffectViewModel
) {
    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collect { event ->
            when (event) {
                is LaunchedEffectViewModel.ScreenEvents.ShowSnackbar -> {

                }
                is LaunchedEffectViewModel.ScreenEvents.Navigate -> {

                }
            }
        }
    }
}