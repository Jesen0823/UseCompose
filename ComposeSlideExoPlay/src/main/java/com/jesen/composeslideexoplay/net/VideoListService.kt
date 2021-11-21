package com.jesen.composeslideexoplay.net

import com.jesen.composeslideexoplay.model.VideoStore
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoListService {

    @GET("api/v4/discovery/hot")
    suspend fun getVideoList(
        @Query("start") itemStart: Int = 1,
        @Query("num") pageSize: Int = 6
    ): VideoStore
}