package com.jesen.driverexampaging

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.jesen.driverexampaging.ui.composeview.ExamListScreen
import com.jesen.driverexampaging.ui.theme.UseComposeTheme
import com.jesen.driverexampaging.viewmodel.ExamViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ExamViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        /*lifecycleScope.launchWhenCreated {
            viewModel.data = viewModel.loadExam()
        }
*/
        setContent {
            UseComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        ExamListScreen(
                            viewModel = viewModel,
                            context = this
                        )
                    }
                }
            }
        }
    }
}
