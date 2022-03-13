package com.example.servicetestcompose.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {
    private val TAG = "MyService---"
    lateinit var job: Job

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        job = MainScope().launch {
            while (true) {
                delay(800)
                Log.d(TAG, "service is running...")
            }
        }
        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}