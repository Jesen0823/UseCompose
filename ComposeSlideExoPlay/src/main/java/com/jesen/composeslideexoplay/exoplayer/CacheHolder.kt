package com.jesen.composeslideexoplay.exoplayer

import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache

object CacheHolder {
    private var cache: SimpleCache? = null
    private val lock = Object()

    fun get(context: Context): SimpleCache {
        synchronized(lock) {
            if (cache == null) {
                val cacheSize = 20L * 1024 * 1024
                val exoDatabaseProvider = ExoDatabaseProvider(context)

                cache = SimpleCache(
                    context.cacheDir,
                    LeastRecentlyUsedCacheEvictor(cacheSize),
                    exoDatabaseProvider
                )
            }
        }
        return cache!!
    }
}