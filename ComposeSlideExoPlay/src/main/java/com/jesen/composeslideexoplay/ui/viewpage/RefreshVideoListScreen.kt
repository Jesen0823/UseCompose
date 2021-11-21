package com.jesen.composeslideexoplay.ui.viewpage

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.jesen.composeslideexoplay.init.SwipeRefreshLayout
import com.jesen.composeslideexoplay.viewmodel.MainViewModel
import java.lang.reflect.Modifier

/**
 * 首页列表加载 ---下拉刷新，加载更多动效
 * */
@Composable
fun RefreshVideoListScreen(
    viewModel: MainViewModel,
    context: Context,
) {

    val collectAsLazyPagingIDataList = viewModel.videoItemList.collectAsLazyPagingItems()

    SwipeRefreshLayout(
        collectAsLazyPagingItems = collectAsLazyPagingIDataList
    ) {

        itemsIndexed(collectAsLazyPagingIDataList) { index, data ->
            VideoCardItem(
                index = index,
                videoItem = data!!,
                onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
            )
        }
    }
}
