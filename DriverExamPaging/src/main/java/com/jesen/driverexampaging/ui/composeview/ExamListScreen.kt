package com.jesen.driverexampaging.ui.composeview

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.viewmodel.ExamViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun ExamListScreen(
    modifier: Modifier = Modifier,
    viewModel: ExamViewModel,
    context: Context
) {

    ContentInfoList(modifier = modifier, queList = viewModel.questions, context)
}

@Composable
fun ContentInfoList(modifier: Modifier, queList: Flow<PagingData<Question>>, context: Context) {

    val queItems: LazyPagingItems<Question> = queList.collectAsLazyPagingItems()
    LazyColumn {
        items(queItems.itemCount) { index ->
            QItemView(
                index = index,
                que = queItems.peek(index),
                onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
            )
        }
    }

    queItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
            }

            loadState.append is LoadState.Loading -> {
            }

            loadState.append is LoadState.Error -> {
            }
        }

    }
}
