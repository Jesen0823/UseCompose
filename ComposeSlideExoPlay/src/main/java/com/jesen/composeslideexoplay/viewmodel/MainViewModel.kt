package com.jesen.composeslideexoplay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jesen.composeslideexoplay.net.VideoListDataSource
import com.jesen.composeslideexoplay.repository.Repository

class MainViewModel : ViewModel() {

    val videoItemList = Pager(
        config = PagingConfig(
            pageSize = 4,
            initialLoadSize = 8, // 第一次加载数量
            prefetchDistance = 2,
        )
    ) {
        VideoListDataSource(Repository)
    }.flow.cachedIn(viewModelScope)
}