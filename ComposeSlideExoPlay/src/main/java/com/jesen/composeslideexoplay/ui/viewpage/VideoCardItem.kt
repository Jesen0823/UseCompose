package com.jesen.composeslideexoplay.ui.viewpage

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.MyPlayerView
import com.google.android.exoplayer2.util.Util
import com.jesen.composeslideexoplay.exoplayer.ExoPlayerHolder
import com.jesen.composeslideexoplay.exoplayer.PlayViewMode
import com.jesen.composeslideexoplay.exoplayer.PlayerViewManager
import com.jesen.composeslideexoplay.exoplayer.VideoDataSourceHolder
import com.jesen.composeslideexoplay.model.VideoInfo
import com.jesen.composeslideexoplay.model.VideoItem
import com.jesen.composeslideexoplay.ui.theme.gray300
import com.jesen.composeslideexoplay.ui.theme.gray600
import com.jesen.composeslideexoplay.util.logD
import com.jesen.composeslideexoplay.viewmodel.MainViewModel

@ExperimentalCoilApi
@Composable
fun VideoCardItem(
    videoItem: VideoItem,
    isFocused: Boolean,
    onClick: () -> Unit,
    index: Int,
    viewModel: MainViewModel?
) {
    val videoInfo = videoItem.videoInfo
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        backgroundColor = if (isFocused) gray300 else MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "$index: ${videoInfo?.description}",
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = videoInfo?.title ?: "",
                style = MaterialTheme.typography.body1,
                color = gray600
            )
            var width = 1280
            var height = 720
            videoInfo?.playInfo?.let {
                if (it.isNotEmpty()) {
                    width = it[0].width
                    height = it[0].height
                }
            }
            if (isFocused) {
                ExoPlayerView(isFocused, videoInfo, viewModel)
            } else {
                // 截断以下图片Url
                val coverUrl = videoInfo?.cover?.feed?.substringBefore('?')
                CoilImage(
                    url = coverUrl,
                    modifier = Modifier
                        .aspectRatio(width.toFloat() / height)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ExoPlayerView(isFocused: Boolean, videoInfo: VideoInfo?, viewModel: MainViewModel?) {

    val context = LocalContext.current
    // 获取播放器实例
    val exoPlayer = remember { ExoPlayerHolder.get(context = context) }
    var playerView: MyPlayerView? = null

    var width = 1280
    var height = 720
    videoInfo?.playInfo?.let {
        if (it.isNotEmpty()) {
            width = it[0].width
            height = it[0].height
        }
    }

    if (isFocused) {
        videoInfo?.let {
            LaunchedEffect(key1 = videoInfo.playUrl, key2 = it) {
                val playUri = Uri.parse(it.playUrl)
                val dataSourceFactory = VideoDataSourceHolder.getCacheFactory(context)
                val mediaSource = when (Util.inferContentType(playUri)) {
                    C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(playUri))
                    C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(playUri))
                    else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(playUri))
                }

                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
            }
        }

        AndroidView(

            modifier = Modifier.aspectRatio(width.toFloat() / height),
            factory = { context ->
                val frameLayout = FrameLayout(context)
                frameLayout.setBackgroundColor(context.getColor(android.R.color.holo_purple))
                frameLayout
            },
            update = { frameLayout ->
                logD("update removeAllViews, playerViewMode: ${PlayerViewManager.playerViewMode}, isFocused:$isFocused")
                if (PlayerViewManager.playerViewMode == PlayViewMode.HALF_SCREEN) {
                    frameLayout.removeAllViews()
                    if (isFocused) {
                        playerView = PlayerViewManager.get(frameLayout.context)

                        // 切换播放器
                        MyPlayerView.switchTargetView(
                            exoPlayer,
                            PlayerViewManager.currentPlayerView,
                            playerView
                        )
                        PlayerViewManager.currentPlayerView = playerView

                        playerView?.apply {
                            player?.playWhenReady = true
                        }

                        playerView?.apply {
                            (parent as? ViewGroup)?.removeView(this)
                        }
                        frameLayout.addView(
                            playerView,
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                        viewModel?.saveFrameLayout(frameLayout)
                        logD("update, frameLayout:$frameLayout")
                    } else if (playerView != null) {
                        playerView?.apply {
                            (parent as? ViewGroup)?.removeView(this)
                            PlayerViewManager.release(this)
                        }
                        playerView = null
                    }
                }
            }
        )

        DisposableEffect(key1 = videoInfo?.playUrl) {
            onDispose {
                logD("--onDispose, isFocused: $isFocused")
                if (isFocused) {
                    playerView?.apply {
                        (parent as? ViewGroup)?.removeView(this)
                    }
                    exoPlayer.stop()
                    playerView?.let {
                        PlayerViewManager.release(it)
                    }
                    playerView = null
                }
            }
        }
    } else {
        // 在AndroidView中插入Compose UI
        /*AndroidView(
           modifier = Modifier.aspectRatio(width.toFloat() / height),
           factory = { context ->
               val coverLayout = FrameLayout(context)
               coverLayout.setBackgroundColor(context.getColor(android.R.color.darker_gray))
               coverLayout
           },
           update = { coverLayout ->
               val coverUrl = videoInfo?.cover?.feed?.substringBefore('?')
               coverLayout.addView(ComposeView(context).apply {
                   //id = R.id.compose_view_y
                   setContent {
                       MaterialTheme {
                           CoilImage(
                               url = coverUrl,
                               modifier = Modifier.fillMaxWidth()
                           )
                       }
                   }
               })
           }
        )*/
    }
}


@ExperimentalCoilApi
@Composable
fun CoilImage(url: String?, modifier: Modifier) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = modifier,
    )
}