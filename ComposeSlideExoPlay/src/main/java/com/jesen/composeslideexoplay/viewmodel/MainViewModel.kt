package com.jesen.composeslideexoplay.viewmodel

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.android.exoplayer2.ui.MyPlayerView
import com.jesen.composeslideexoplay.net.VideoListDataSource
import com.jesen.composeslideexoplay.repository.Repository
import com.jesen.composeslideexoplay.util.logD

class MainViewModel() : ViewModel() {

    private var frameLayout: FrameLayout? = null

    private var contentRootView: ViewGroup? = null

    val videoItemList = Pager(
        config = PagingConfig(
            pageSize = 4,
            initialLoadSize = 8, // 第一次加载数量
            prefetchDistance = 2,
        )
    ) {
        VideoListDataSource(Repository)
    }.flow.cachedIn(viewModelScope)


    fun saveFrameLayout(frameLayout: FrameLayout) {
        this.frameLayout = frameLayout
    }

    fun addPlayerViewToLazyList(playerView: MyPlayerView?) {
        logD(" exitFullScreen,  add frameLayout:$frameLayout")
        frameLayout?.addView(
            playerView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    fun settRootViewGroup(rootView: ViewGroup) {
        contentRootView = rootView
    }

    fun getRootViewGroup(): ViewGroup? = contentRootView
}