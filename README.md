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



