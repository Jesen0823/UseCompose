package com.example.servicetestcompose.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.servicetestcompose.R

class ForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(this)
        } else ""

        val channelBuilder = NotificationCompat.Builder(this, channelId)

        val notification = channelBuilder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentTitle("前台服务标题")
            .setContentText("这是前台服务。。。。")
            .build()

        startForeground(110, notification)

        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }


}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(context: Context): String {
    val channelId = "service_fore"
    val channelName = "serviceFore"
    val channel = NotificationChannel(
        channelId,
        channelName,
        NotificationManager.IMPORTANCE_HIGH
    )
    channel.apply {
        lightColor = Color.BLUE
        importance = NotificationManager.IMPORTANCE_HIGH
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    }

    val service = ContextCompat.getSystemService(context, ForegroundService::class.java)
        ?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    service.createNotificationChannel(channel)
    return channelId
}