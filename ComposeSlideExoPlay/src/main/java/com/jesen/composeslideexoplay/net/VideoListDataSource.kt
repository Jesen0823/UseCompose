package com.jesen.composeslideexoplay.net

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jesen.composeslideexoplay.model.VideoItem
import com.jesen.composeslideexoplay.repository.Repository

import kotlinx.coroutines.delay

class VideoListDataSource(private val repository: Repository) : PagingSource<Int, VideoItem>() {

    private val TAG = "--ExamSource"

    override fun getRefreshKey(state: PagingState<Int, VideoItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoItem> {

        return try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize

            // 加载分页
            val everyPageSize = 4
            val initPageSize = 8
            val curStartItem =
                if (currentPage == 1) 1 else (currentPage - 2) * everyPageSize + 1 + initPageSize

            val responseList = repository.getVideoList(curStartItem, pageSize = pageSize)
                .videoList ?: emptyList<VideoItem>()

            val preKey = if (currentPage == 1) null else currentPage.minus(1)
            var nextKey: Int? = currentPage.plus(1)
            Log.d(TAG, "currentPage: $currentPage")
            Log.d(TAG, "preKey: $preKey")
            Log.d(TAG, "nextKey: $nextKey")
            if (responseList.isEmpty()) {
                nextKey = null
            }

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