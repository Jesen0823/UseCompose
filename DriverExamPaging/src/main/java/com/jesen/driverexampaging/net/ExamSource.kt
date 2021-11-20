package com.jesen.driverexampaging.net

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.repository.Repository

class ExamSource(private val repository: Repository) : PagingSource<Int, Question>() {

    private val TAG = "--ExamSource"

    override fun getRefreshKey(state: PagingState<Int, Question>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Question> {

        return try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            Log.d(TAG, "currentPage: $currentPage")
            Log.d(TAG, "pageSize: $pageSize")

            val responseList = repository.getExamList(currentPage, pageSize = pageSize)
                .result?.resultData?.questionList ?: emptyList<Question>()

            // 加载分页

            val everyPageSize = 4
            val initPageSize = 8
            val preKey = if (currentPage == 1) null else currentPage.minus(1)
            var nextKey: Int? = if (currentPage == 1) {
                initPageSize / everyPageSize
            } else {
                currentPage.plus(1)
            }
            Log.d(TAG, "currentPage: $currentPage")
            Log.d(TAG, "preKey: $preKey")
            Log.d(TAG, "nextKey: $nextKey")
            if (responseList.isEmpty()) {
                nextKey = null
            }
            Log.d(TAG, "final nextKey: $nextKey")

            LoadResult.Page(
                data = responseList,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}