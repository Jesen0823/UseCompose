package com.jesen.composeslideexoplay.exoplayer

import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.util.Pools
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ui.PlayerView
import com.jesen.composeslideexoplay.MainActivity
import com.jesen.composeslideexoplay.R
import com.jesen.composeslideexoplay.util.componentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 用来管理 PlayerView
 * */
object PlayerViewManager {

    var currentPlayerView: PlayerView? = null
    private var playerViewMode = PlayViewMode.HALF_SCREEN

    private val playerViewPool = Pools.SimplePool<PlayerView>(2)

    fun get(context: Context): PlayerView {
        return playerViewPool.acquire() ?: createPlayerView(context)
    }

    fun release(player: PlayerView) {
        playerViewPool.release(player)
    }

    // 创建PlayerView
    private fun createPlayerView(context: Context): PlayerView {
        val playView = (LayoutInflater.from(context)
            .inflate(R.layout.exoplayer_texture_view, null, false) as PlayerView)
        playView.setShowMultiWindowTimeBar(true)
        playView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
        playView.controllerAutoShow = true

        val fullScreenBtn = playView.findViewById<ImageView>(R.id.full_screen_btn)
        fullScreenBtn.setOnClickListener {
            switchPlayerViewMode(playView)
        }
        return playView
    }

    /**
     * 全屏切换
     * */
    private fun switchPlayerViewMode(playerView: PlayerView) {
        Log.d("xxx--", "switchPlayerViewMode")
        val activity = playerView.context.componentActivity
        val viewModel = (activity as MainActivity).viewModel
        val contentRootView = activity.findViewById<ViewGroup>(android.R.id.content)

        if (playerViewMode == PlayViewMode.HALF_SCREEN) {

            // 设置为全屏
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            viewModel.removePlayerViewFromLazyList()
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            viewModel.viewModelScope.launch {
                delay(100)
                contentRootView.addView(playerView, params)
            }
            playerViewMode = PlayViewMode.FULL_SCREEN

        } else { //退出全屏
            Log.d("xxx--", "switchPlayerViewMode  set to screen half")
            // 旋转屏幕
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            // 将playerView从Activity的R.id.content移除
            val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
            contentView.removeView(playerView)

            // 然后加入LazyColumn的ItemView下
            viewModel.viewModelScope.launch {
                delay(100)
                viewModel.addPlayerViewToLazyList()
            }

            playerViewMode = PlayViewMode.HALF_SCREEN
        }
    }
}


enum class PlayViewMode { HALF_SCREEN, FULL_SCREEN, TO_FULL, TO_HALF }