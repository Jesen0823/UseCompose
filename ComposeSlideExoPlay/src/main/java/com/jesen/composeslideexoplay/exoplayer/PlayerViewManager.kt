package com.jesen.composeslideexoplay.exoplayer

import android.content.Context
import android.view.LayoutInflater
import androidx.core.util.Pools
import com.google.android.exoplayer2.ui.PlayerView
import com.jesen.composeslideexoplay.R

/**
 * 用来管理 PlayerView
 * */
object PlayerViewManager {
    var currentPlayerView: PlayerView? = null

    private val playerViewPool = Pools.SimplePool<PlayerView>(2)

    fun get(context: Context): PlayerView {
        return playerViewPool.acquire() ?: createPlayerView(context)
    }

    fun release(player: PlayerView) {
        playerViewPool.release(player)
    }

    private fun createPlayerView(context: Context): PlayerView {
        val playView = (LayoutInflater.from(context)
            .inflate(R.layout.exoplayer_texture_view, null, false) as PlayerView)
        playView.setShowMultiWindowTimeBar(true)
        playView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
        playView.controllerAutoShow = true
        return playView
    }
}
