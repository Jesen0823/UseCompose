package com.jesen.composeslideexoplay.ui.viewpage

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.jesen.composeslideexoplay.exoplayer.ExoPlayerHolder
import com.jesen.composeslideexoplay.exoplayer.PlayerViewManager
import com.jesen.composeslideexoplay.exoplayer.VideoDataSourceHolder
import com.jesen.composeslideexoplay.model.VideoInfo
import com.jesen.composeslideexoplay.model.VideoItem
import com.jesen.composeslideexoplay.ui.theme.gray300
import com.jesen.composeslideexoplay.ui.theme.gray600

@ExperimentalCoilApi
@Composable
fun VideoCardItem(videoItem: VideoItem, isFocused: Boolean, onClick: () -> Unit, index: Int) {
    Surface {
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
                val videoInfo = videoItem.videoInfo

                Text(
                    text = "$index: ${videoItem.videoInfo?.description}",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = videoItem.videoInfo?.title ?: "",
                    style = MaterialTheme.typography.body1,
                    color = gray600
                )
                if (isFocused) {
                    ExoPlayerView(isFocused, videoInfo)
                } else {
                    // 截断以下图片Url
                    val coverUrl = videoInfo?.cover?.feed?.substringBefore('?')
                    CoilImage(
                        url = coverUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ExoPlayerView(isFocused: Boolean, videoInfo: VideoInfo?) {

    val context = LocalContext.current
    // 获取播放器实例
    val exoPlayer = remember { ExoPlayerHolder.get(context = context) }
    var playerView: PlayerView? = null

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

        val width = videoInfo?.playInfo?.get(0)?.width ?: 1280
        val height = videoInfo?.playInfo?.get(0)?.height ?: 720
        AndroidView(

            modifier = Modifier.aspectRatio(width.toFloat() / height),
            factory = { context ->
                val frameLayout = FrameLayout(context)
                frameLayout.setBackgroundColor(context.getColor(android.R.color.holo_blue_bright))
                frameLayout
            },
            update = { frameLayout ->
                frameLayout.removeAllViews()
                if (isFocused) {
                    playerView = PlayerViewManager.get(frameLayout.context)
                    // 切换播放器布局
                    PlayerView.switchTargetView(
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
                } else if (playerView != null) {
                    playerView?.apply {
                        (parent as? ViewGroup)?.removeView(this)
                        PlayerViewManager.release(this)
                    }
                    playerView = null
                }
            }
        )

        DisposableEffect(key1 = videoInfo?.playUrl) {
            onDispose {
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