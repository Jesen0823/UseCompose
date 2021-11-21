package com.jesen.composeslideexoplay.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okio.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageUtil {

    @Throws(IOException::class)
    suspend fun drawableFromUrl(url: String?): BitmapDrawable = coroutineScope {
        return@coroutineScope withContext(Dispatchers.IO) {
            val bp: Bitmap
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.connect()
            val input: InputStream = connection.inputStream
            bp = BitmapFactory.decodeStream(input)
            BitmapDrawable(Resources.getSystem(), bp)
        }
    }


}
