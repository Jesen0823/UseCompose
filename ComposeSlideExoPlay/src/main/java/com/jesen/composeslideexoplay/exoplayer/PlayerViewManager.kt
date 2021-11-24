package com.jesen.composeslideexoplay.exoplayer

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.util.Pools
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.MyPlayerView
import com.jesen.composeslideexoplay.MainActivity
import com.jesen.composeslideexoplay.R
import com.jesen.composeslideexoplay.util.ExoEventListener
import com.jesen.composeslideexoplay.util.componentActivity
import com.jesen.composeslideexoplay.util.logD
import com.jesen.composeslideexoplay.util.statusBarIsHide
import com.jesen.composeslideexoplay.viewmodel.MainViewModel


/**
 * 用来管理 PlayerView
 * */
object PlayerViewManager : ExoEventListener {

    var currentPlayerView: MyPlayerView? = null

    var playerViewMode = PlayViewMode.HALF_SCREEN
    var activity: MainActivity? = null
    var viewModel: MainViewModel? = null

    private val playerViewPool = Pools.SimplePool<MyPlayerView>(2)

    fun get(context: Context): MyPlayerView {
        return playerViewPool.acquire() ?: createPlayerView(context)
    }

    fun release(player: MyPlayerView) {
        playerViewPool.release(player)
    }

    /**
     * 创建PlayerView
     * */
    private fun createPlayerView(context: Context): MyPlayerView {
        val playView = (LayoutInflater.from(context)
            .inflate(R.layout.exoplayer_texture_view, null, false) as MyPlayerView)
        playView.setShowMultiWindowTimeBar(true)
        playView.setShowBuffering(MyPlayerView.SHOW_BUFFERING_ALWAYS)
        playView.controllerAutoShow = true
        playView.playerController.setExoEventListener(this)

        initOther(playView)
        return playView
    }


    /****************************************其他业务*******************************************/


    private fun initOther(playView: MyPlayerView) {
        activity = playView.context.componentActivity as MainActivity
        viewModel = (activity as MainActivity).viewModel
        val root = activity?.findViewById<ViewGroup>(android.R.id.content)
        if (root != null) {
            viewModel?.settRootViewGroup(root)
        }

        // 返回按钮
        val backExitBtn = playView.findViewById<ImageView>(R.id.back_play)
        backExitBtn.setOnClickListener {
            if (isFullScreen()) {
                switchPlayerViewMode()
            } else {
                activity?.finish()
            }
        }
    }

    private fun switchPlayerViewMode() {
        logD("switchPlayerViewMode")
        activity = currentPlayerView?.context?.componentActivity as MainActivity
        if (activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //切换竖屏
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            //切换横屏
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    fun enterFullScreen() {
        val contentRootView = activity?.findViewById<ViewGroup>(android.R.id.content)
        // 隐藏状态栏导航栏
        activity?.statusBarIsHide(contentRootView as View, true)

        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val parent = currentPlayerView?.parent as ViewGroup
        parent.let {
            it.removeView(currentPlayerView)
        }
        contentRootView?.addView(currentPlayerView, params)
        logD(" enterFullScreen,  remove parent:$parent, add contentView:$contentRootView")

        playerViewMode = PlayViewMode.FULL_SCREEN
    }

    fun exitFullScreen(): Boolean {
        if (isFullScreen()) {
            logD("switchPlayerViewMode  set to screen half")
            viewModel?.getRootViewGroup()?.let {
                // 恢复状态栏显示
                activity?.statusBarIsHide(it as View, false)
                // 从根View移除PlayerView
                it.removeView(currentPlayerView)
            }

            // 然后加入LazyColumn的ItemView下
            viewModel?.addPlayerViewToLazyList(currentPlayerView)

            playerViewMode = PlayViewMode.HALF_SCREEN

            return true
        }
        return false
    }

    /**
     * 全屏处理
     * */
    override fun changeFullScreen(player: Player) {
        switchPlayerViewMode()
    }

    override fun backExitScreen(player: Player) {
        if (isFullScreen()) {
            exitFullScreen()
        } else {
            activity?.finish()
        }
    }

    /**
     * 暂停续播
     * */
    fun playOrPause(isPause: Boolean) {
        val playerController = currentPlayerView?.playerController
        playerController?.let {
            if (isPause) it.doPause() else it.doPlay()
        }
    }

    private fun isFullScreen(): Boolean = playerViewMode == PlayViewMode.FULL_SCREEN

    fun onBackPressed(): Boolean {
        return exitFullScreen()
    }
}


enum class PlayViewMode { HALF_SCREEN, FULL_SCREEN }
