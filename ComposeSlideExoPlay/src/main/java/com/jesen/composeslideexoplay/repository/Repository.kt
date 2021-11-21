package com.jesen.composeslideexoplay.repository

import com.jesen.composeslideexoplay.net.RetrofitClient
import com.jesen.composeslideexoplay.net.VideoListService

object Repository {

    suspend fun getVideoList(itemStart: Int, pageSize: Int) =
        RetrofitClient.createApi(VideoListService::class.java)
            .getVideoList(itemStart, pageSize)
}