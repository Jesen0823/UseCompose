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
import com.jesen.driverexampaging.model.Question
import com.jesen.driverexampaging.viewmodel.ExamViewModel

@Composable
fun ExamListScreen(
    viewModel: ExamViewModel,
    context: Context
) {
    val questionList = viewModel.examList.collectAsLazyPagingItems()

    when (questionList.loadState.refresh) {
        is LoadState.NotLoading -> {
            ContentInfoList(
                questionList = questionList,
                context = context
            )
        }
        is LoadState.Error -> ErrorPage() { questionList.refresh() }
        is LoadState.Loading -> LoadingPage()
    }
}

@Composable
fun ContentInfoList(
    context: Context,
    questionList: LazyPagingItems<Question>
) {

    LazyColumn {
        itemsIndexed(questionList) { index, question ->
            QItemView(
                index = index,
                que = question,
                onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
            )
        }

        when (questionList.loadState.append) {
            is LoadState.NotLoading -> {
                itemsIndexed(questionList) { index, question ->
                    QItemView(
                        index = index,
                        que = question,
                        onClick = { Toast.makeText(context, "ccc", Toast.LENGTH_SHORT).show() },
                    )
                }
            }
            is LoadState.Error -> item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(onClick = {
                        questionList.retry()
                    }) {
                        Text(text = "重试")
                    }
                }
            }
            LoadState.Loading -> item {
                LoadingPage()
            }
        }
    }
}
