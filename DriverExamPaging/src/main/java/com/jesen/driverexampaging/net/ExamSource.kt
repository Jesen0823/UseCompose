package com.jesen.driverexampaging.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jesen.driverexampaging.model.Question

class ExamSource : PagingSource<Int, Question>() {

    override fun getRefreshKey(state: PagingState<Int, Question>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Question> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize
        val response = RetrofitClient.createApi(ExamListService::class.java)
            .getExamList(currentPage, pageSize)

        val responseList = response.result?.resultData?.questionList ?: emptyList<Question>()

        // 加载分页

        val everyPageSize = 4
        val initPageSize = 8
        val preKey = if (currentPage == 1) null else currentPage.minus(1)
        val nextKey = if (currentPage == 1) {
            initPageSize / everyPageSize + 1
        } else {
            currentPage.plus(1)
        }

        return try {
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