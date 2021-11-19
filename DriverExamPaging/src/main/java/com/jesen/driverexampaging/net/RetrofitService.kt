package com.jesen.driverexampaging.net

import com.jesen.driverexampaging.model.ExamQuestions
import com.jesen.driverexampaging.net.RetrofitClient.APP_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ExamListService {

    @GET("jisuapi/driverexamQuery")
    suspend fun getExamList(
        @Query("pagenum") pagenum: Int,
        @Query("pagesize") pagesize: Int = 4,
        @Query("type") type: String = "C1",
        @Query("subject") subject: Int = 1,
        @Query("sort") sort: String = "normal",
        @Query("appkey") appkey: String = APP_KEY,
    ): ExamQuestions
}