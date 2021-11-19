package com.jesen.driverexampaging.model

import com.google.gson.annotations.SerializedName

/**
 * 驾照考试题库
 *  接口数据来源：https://wx.jdcloud.com/market/api/11865
 *
 *  https://way.jd.com/jisuapi/driverexamQuery?type=c1&subject=1&pagesize=3&pagenum=1000&sort=normal&appkey=647fd7f08ee823199d919a2a09161345
 * */
data class ExamQuestions(
    val charge: Boolean,
    val code: String,      // 状态码 10000表示OK
    val msg: String,
    val requestId: String,
    val result: Result?
)

data class Result(
    val msg: String,
    @SerializedName("result")
    val resultData: ResultInfo?,
    val status: Int
)

data class ResultInfo(
    val chapter: Int,
    @SerializedName("list")
    val questionList: List<Question>?, // 题目列表
    val pagenum: Int,                 // 页码
    val pagesize: Int,                // 每页大小
    val sort: String,                 // 排序方式
    val subject: Int,                 // 科目类别
    val total: Int,                   // 总数
    val type: String                  // 题目类型
)

data class Question(
    val answer: String,
    val chapter: String?,
    val explain: String,    // 解析
    val option1: String?,    // 选项A
    val option2: String?,    // 选项B
    val option3: String?,    // 选项C
    val option4: String?,    // 选项D
    val question: String,   // 答案
    val type: String
)