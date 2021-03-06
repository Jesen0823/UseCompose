package com.jesen.composeslideexoplay

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jesen.composeslideexoplay.exoplayer.PlayerViewManager
import com.jesen.composeslideexoplay.ui.theme.UseComposeTheme
import com.jesen.composeslideexoplay.ui.viewpage.NormalVideoListScreen
import com.jesen.composeslideexoplay.ui.viewpage.RefreshVideoListScreen
import com.jesen.composeslideexoplay.util.logD
import com.jesen.composeslideexoplay.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    // 是否下拉刷新
    private val loadModeRefresh = false

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

                                if (loadModeRefresh) {
                                    // 下拉刷新加载
                                    RefreshVideoListScreen(
                                        viewModel = viewModel,
                                        context = this@MainActivity,
                                    )
                                } else {
                                    NormalVideoListScreen(
                                        viewModel = viewModel,
                                        context = this@MainActivity,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //PlayerViewManager.releaseVideoPlayer()
        logD("---onStop")
    }

    override fun onRestart() {
        super.onRestart()
        PlayerViewManager.playOrPause(false)
        logD("---onRestart")
    }

    override fun onResume() {
        super.onResume()
        logD("---onResume")
    }

    override fun onPause() {
        super.onPause()
        PlayerViewManager.playOrPause(true)
        logD("---onPause")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                logD("onConfigurationChanged LANDSCAPE from Configuration， 竖屏转横屏 手动")
                PlayerViewManager.enterFullScreen()
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                logD("onConfigurationChanged PORTRAIT from Configuration, 横屏转回竖屏")
                PlayerViewManager.exitFullScreen()
            }
            Configuration.ORIENTATION_SQUARE -> {
                logD("onConfigurationChanged SQUARE from Configuration")
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                logD("onConfigurationChanged UNDEFINED from Configuration")
            }
        }
    }

    override fun onBackPressed() {
        if (PlayerViewManager.onBackPressed()) return
        super.onBackPressed()
    }
}
