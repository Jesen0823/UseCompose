package com.jesen.driverexampaging.ui.composeview

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.jesen.driverexampaging.common.SwipeRefreshList
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.viewmodel.ExamViewModel

/**
 * 首页列表加载 ---下拉刷新，加载更多动效
 * */
@Composable
fun RefreshExamListScreen(
    viewModel: ExamViewModel,
    context: Context,
) {

    val collectAsLazyPagingIDataList = viewModel.examList.collectAsLazyPagingItems()

    SwipeRefreshList(collectAsLazyPagingIDataList) {

        itemsIndexed(collectAsLazyPagingIDataList) { index, data ->
            QItemView(
                index = index,
                que = data,
                onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
            )
        }
    }
}
