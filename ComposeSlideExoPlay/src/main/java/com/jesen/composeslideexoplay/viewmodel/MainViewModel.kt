package com.jesen.composeslideexoplay.viewmodel

import android.view.ViewParent
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jesen.composeslideexoplay.exoplayer.PlayerViewManager
import com.jesen.composeslideexoplay.net.VideoListDataSource
import com.jesen.composeslideexoplay.repository.Repository

class MainViewModel() : ViewModel() {

    var frameLayout: FrameLayout? = null

    // 默认非全屏
    //var playerViewMode = PlayViewMode.HALF_SCREEN

    var currentCardItem: ViewParent? = null

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

    fun saveCurrentCard(curItem: ViewParent) {
        currentCardItem = curItem
    }

    fun removePlayerViewFromLazyList() {
        frameLayout?.removeView(PlayerViewManager.currentPlayerView)

    }

    fun addPlayerViewToLazyList() {
        frameLayout?.addView(
            PlayerViewManager.currentPlayerView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }
}