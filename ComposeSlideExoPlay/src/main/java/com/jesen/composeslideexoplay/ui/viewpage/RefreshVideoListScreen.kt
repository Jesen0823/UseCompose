package com.jesen.composeslideexoplay.ui.viewpage

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.jesen.composeslideexoplay.init.SwipeRefreshLayout
import com.jesen.composeslideexoplay.viewmodel.MainViewModel

/**
 * 首页列表加载 ---下拉刷新，加载更多动效
 * */
@Composable
fun RefreshVideoListScreen(
    viewModel: MainViewModel,
    context: Context,
) {

    val collectAsLazyPagingIDataList = viewModel.videoItemList.collectAsLazyPagingItems()

    val lazyListState = rememberLazyListState()
    val focusIndex by derivedStateOf { lazyListState.firstVisibleItemIndex }


    SwipeRefreshLayout(
        columnState = lazyListState,
        collectAsLazyPagingItems = collectAsLazyPagingIDataList
    ) {

        itemsIndexed(collectAsLazyPagingIDataList) { index, data ->
            VideoCardItem(
                videoItem = data!!,
                isFocused = index == focusIndex,
                onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
                index = index,
                viewModel = viewModel
            )
        }
    }
}
