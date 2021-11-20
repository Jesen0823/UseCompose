package com.jesen.driverexampaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.net.ExamSource
import com.jesen.driverexampaging.repository.Repository
import kotlinx.coroutines.flow.Flow

class ExamViewModel : ViewModel() {

    val examList = Pager(
        config = PagingConfig(
            pageSize = 4,
            initialLoadSize = 8, // 第一次加载数量，如果不设置的话是 pageSize * 2
            prefetchDistance = 2,
        )
    ) {
        ExamSource(Repository)
    }.flow.cachedIn(viewModelScope)
}