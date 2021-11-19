package com.jesen.driverexampaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.net.ExamSource
import kotlinx.coroutines.flow.Flow

class ExamViewModel : ViewModel() {

    val questions: Flow<PagingData<Question>> = Pager(
        PagingConfig(
            initialLoadSize = 8, // 初始条目
            prefetchDistance = 1,
            pageSize = 4,
        )
    ) {
        ExamSource()
    }.flow.cachedIn(viewModelScope)
}