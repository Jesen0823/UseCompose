package com.jesen.driverexampaging.ui.composeview

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.insets.statusBarsHeight
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.viewmodel.ExamViewModel

/**
 * 首页列表加载 ---普通加载，没有下拉刷新，可加载下一页
 * */

@Composable
fun ExamListScreen(
    viewModel: ExamViewModel,
    context: Context
) {

    val collectAsLazyPagingIDataList = viewModel.examList.collectAsLazyPagingItems()

    // 首次加载业务逻辑
    when (collectAsLazyPagingIDataList.loadState.refresh) {
        is LoadState.NotLoading -> {
            ContentInfoList(
                collectAsLazyPagingIDataList = collectAsLazyPagingIDataList,
                context = context
            )
        }
        is LoadState.Error -> ErrorPage() { collectAsLazyPagingIDataList.refresh() }
        is LoadState.Loading -> LoadingPage()
    }
}

@Composable
fun ContentInfoList(
    context: Context,
    collectAsLazyPagingIDataList: LazyPagingItems<Question>
) {

    LazyColumn {
        itemsIndexed(collectAsLazyPagingIDataList) { index, question ->
            QItemView(
                index = index,
                que = question,
                onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
            )
        }

        // 加载下一页业务逻辑
        when (collectAsLazyPagingIDataList.loadState.append) {
            is LoadState.NotLoading -> {
                itemsIndexed(collectAsLazyPagingIDataList) { index, question ->
                    QItemView(
                        index = index,
                        que = question,
                        onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
                    )
                }
            }
            is LoadState.Error -> item {
                NextPageLoadError {
                    collectAsLazyPagingIDataList.retry()
                }
            }
            LoadState.Loading -> item {
                LoadingPage()
            }
        }
    }
}
