# UseCompose
first  study in Jetpack Compose

### 案例展示

| app                                                          | AnimationCodelab                                             | ThemingCodelab                                               |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![LazyColumn列表](./capture/11-16_133035.jpg) | ![可收缩列表](./capture/11-16_142603.jpg) | ![主题定制](./capture/11-16_142211.jpg) |
| 简单LazyColumn + animateColorAsState                         | LazyColumn+AnimatedVisibility+HomeTab                        | MaterialTheme+TopAppBar+Card+LazyColumn                      |


| ui_abstraction                                           | ui_abstraction                                               |      |
| -------------------------------------------------------- | ------------------------------------------------------------ | ---- |
| ![](./capture/11-16_142348.jpg) | ![gradLoyout](./capture/11-16_150254.jpg) |      |
| 基本的LazyColumn  / ConstraintLayout / StaggeredGrid /   | 自定义Layout                                                 |      |



| DriverExamPaging                                             | CleanArchitecture                                            |      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---- |
| <img src="./capture/11-20_145409-1.gif" alt="paging分页" style="zoom:80%;" /> | <img src="./capture/11-20_152428-1.gif" alt="CA" style="zoom:80%;" /> |      |
| Paging3+ MVVM + compose+retrofit+LazyColumn                  | dagger:hilt+navigation+room+compose+mvvm+                    |      |

### 沉浸式状态栏

  ```groovy
  // 状态栏相关
  implementation "com.google.accompanist:accompanist-insets:0.21.2-beta"
  implementation "com.google.accompanist:accompanist-insets-ui:0.21.2-beta"
  implementation "com.google.accompanist:accompanist-systemuicontroller:0.21.2-beta"
  ```

  * `WindowCompat.setDecorFitsSystemWindows(window, true)`

    ```java
    /**
    * 设置decorView是否适配windowsetscompat的WindowInsetsCompat根视图。
    * 若置为false，将不适配内容视图的insets，只会适配内容视图。
    * 请注意:在应用程序中使用View.setSystemUiVisibility(int) API可能会与此方法冲突。应停止使用View.setSystemUiVisibility(int)。
    */
    public static void setDecorFitsSystemWindows(@NonNull Window window,
                final boolean decorFitsSystemWindows) {
            if (Build.VERSION.SDK_INT >= 30) {
                Impl30.setDecorFitsSystemWindows(window, decorFitsSystemWindows);
            } else if (Build.VERSION.SDK_INT >= 16) {
                Impl16.setDecorFitsSystemWindows(window, decorFitsSystemWindows);
            }
        }
    ```

    * 状态栏透明
     拿到SystemUiController的setStatusBarColor()方法来改变状态栏，也可以修改底部导航栏

    ```kotlin
     setContent {
          UseComposeTheme {
               // 状态栏改为透明，参数：color(状态栏颜色)，darkIcons（是否为深色图标）
                rememberSystemUiController().setStatusBarColor(
                      Color.Transparent, darkIcons = MaterialTheme.colors.isLight
                )

                // 底部导航栏颜色
                rememberSystemUiController().setNavigationBarColor(
                     Color.Transparent, darkIcons = MaterialTheme.colors.isLight
                )

                // ...
          }
     }
    ```
    * 调整以适配状态栏高度

    需要用到ProvideWindowInsets，在显示内容的外围包一层ProvideWindowInsets，在Theme以下包裹ProvideWindowInsets以便取得状态栏的高度。

    ```kotlin
     setContent {
          UseComposeTheme {
                     // 加入ProvideWindowInsets
                     ProvideWindowInsets {
                         // 状态栏改为透明
                         rememberSystemUiController().setStatusBarColor(
                             Color.Transparent, darkIcons = MaterialTheme.colors.isLight
                         )

                         Surface(color = MaterialTheme.colors.background) {
                             Scaffold(
                                 modifier = Modifier.fillMaxSize()
                             ) {
                                 Column {
                                       // 填充留白状态栏高度
                                      Spacer(modifier = Modifier
                                          .statusBarsHeight()
                                          .fillMaxWidth()
                                      )

                                      // 你的业务 Composable
                                 )
                             }
                         }
                     }
                 }
          }
    ```

    * 效果

    | 修改前                                                       | FitsSystemWindows :false                                     | 修改颜色                                                     | 适配高度                                                     |
    | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
    | <img src="./capture/11-20_171011.jpg" alt="未修改" style="zoom:50%;" /> | <img src="./capture/11-20_171902.jpg" alt="FitsSystemWindows" style="zoom:50%;" /> | <img src="./capture/11-20_173513.jpg" alt="改变颜色" style="zoom:50%;" /> | <img src="./capture/11-20_184244.jpg" alt="适配高度" style="zoom:50%;" /> |


### 下拉刷新

 构建错误：是com.google.accompanist:accompanist:xxx 相关库的版本不兼容，需要依赖相同的版本

```shell
   21:35:51.503 7789-7789/com.jesen.driverexampaging E/AndroidRuntime: FATAL EXCEPTION: main
       Process: com.jesen.driverexampaging, PID: 7789
       java.lang.NoSuchMethodError: No interface method startReplaceableGroup(ILjava/lang/String;)V in class Landroidx/compose/runtime/Composer; or its super classes (declaration of 'androidx.compose.runtime.Composer' appears in /data/app/~~4FT0iYbXWuoHva-X3Y0lBg==/com.jesen.driverexampaging-4uB2hZ7cDclbzM5qgmkttA==/base.apk)
           at com.google.accompanist.swiperefresh.SwipeRefreshKt.rememberSwipeRefreshState(Unknown Source:5)
           at com.jesen.driverexampaging.common.SwipeRefreshListKt.SwipeRefreshList(SwipeRefreshList.kt:31)
           at com.jesen.driverexampaging.ui.composeview.RefreshExamListScreenKt.RefreshExamListScreen(RefreshExamListScreen.kt:33)
           at com.jesen.driverexampaging.Main2Activity$onCreate$1$1$1$1$1.invoke(Main2Activity.kt:54)
           at com.jesen.driverexampaging.Main2Activity$onCreate$1$1$1$1$1.invoke(Main2Activity.kt:47)
           at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:116)
           at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:34)
           at androidx.compose.material.ScaffoldKt$ScaffoldLayout$1$1$1$bodyContentPlaceables$1.invoke(Scaffold.kt:316)
           at androidx.compose.material.ScaffoldKt$ScaffoldLayout$1$1$1$bodyContentPlaceables$1.invoke(Scaffold.kt:314)
```
   1. 效果 ：
   <img src="./capture/11-20_225027.gif" alt="下拉刷新效果" style="zoom:50%;" />






