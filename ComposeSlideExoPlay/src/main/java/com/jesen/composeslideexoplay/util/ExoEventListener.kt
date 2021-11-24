package com.jesen.composeslideexoplay.util

import com.google.android.exoplayer2.Player

interface ExoEventListener {

    // 全屏半屏
    fun changeFullScreen(player: Player)

    // 退出
    fun backExitScreen(player: Player)
}