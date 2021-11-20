package com.jesen.driverexampaging

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jesen.driverexampaging.ui.composeview.ExamListScreen
import com.jesen.driverexampaging.ui.theme.UseComposeTheme
import com.jesen.driverexampaging.viewmodel.ExamViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.*

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ExamViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            UseComposeTheme {
                // 加入ProvideWindowInsets
                ProvideWindowInsets {
                    // 状态栏改为透明
                    rememberSystemUiController().setStatusBarColor(
                        Color.Transparent, darkIcons = MaterialTheme.colors.isLight
                    )
                    // 底部导航栏
                    rememberSystemUiController().setNavigationBarColor(
                        Color.Transparent, darkIcons = MaterialTheme.colors.isLight
                    )

                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column {
                                Spacer(
                                    modifier = Modifier
                                        .statusBarsHeight()
                                        .fillMaxWidth()
                                )
                                ExamListScreen(
                                    viewModel = viewModel,
                                    context = this@MainActivity
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
