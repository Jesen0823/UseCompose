package com.jesen.driverexampaging.repository

import com.jesen.driverexampaging.net.ExamListService
import com.jesen.driverexampaging.net.RetrofitClient

object Repository {

    suspend fun getExamList(page: Int, pageSize: Int) =
        RetrofitClient.createApi(ExamListService::class.java)
            .getExamList(page, pageSize)
}