/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.statecodelab.start.state.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.statecodelab.finish.state.todo.TodoItem
import com.example.statecodelab.start.state.ui.StateCodelabTheme

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    TodoActivityScreen(todoViewModel = todoViewModel)
                }
            }
        }
    }
}

/*
该Composable将是ViewModel中存储的State和项目中已经定义的TodoScreen之间的桥梁。
虽然可以将TodoScreen更改为直接获取ViewModel，但是会降低TodoScreen的可重用性。
通过选择更简单的参数，如List<TodoItem>，TodoScreen不会耦合到State被提升的特定位置。
* */
@Composable
fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    // observeAsState 观察 LiveData 返回 State 对象，数据下流
    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(initial = listOf())
    TodoScreen(
        items = items,
        // 从ViewModel传递addItem和removeItem
        // 如此，当调用TodoScreen的onAddItem或onRemoveItem,事件就上流到了ViewModel
        onAddItem = { todoViewModel.addItem(it) },
        onRemoveItem = { todoViewModel.removeItem(it) }
    )
}











