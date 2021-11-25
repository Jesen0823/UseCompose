package com.jesen.composeslideexoplay.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


val Context.activity: Activity?
    get() {
        return when (this) {
            is Activity -> {
                this
            }
            is ContextWrapper -> {
                this.baseContext.activity
            }
            else -> {
                null
            }
        }
    }

val Context.appCompActivity: AppCompatActivity?
    get() {
        return when (this) {
            is AppCompatActivity -> {
                this
            }
            is ContextThemeWrapper -> {
                this.baseContext.appCompActivity
            }
            else -> {
                null
            }
        }
    }

val Context.componentActivity: ComponentActivity?
    get() {
        return when (this) {
            is ComponentActivity -> {
                this
            }
            is ContextThemeWrapper -> {
                this.baseContext.componentActivity
            }
            else -> {
                null
            }
        }
    }

/**
 * 状态栏显示/隐藏
 * */
fun Activity.statusBarIsHide(view: View, enable: Boolean) {
    val controller = ViewCompat.getWindowInsetsController(view)
    if (enable) {
        controller?.hide(WindowInsetsCompat.Type.statusBars())
        controller?.hide(WindowInsetsCompat.Type.navigationBars())
        controller?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        //controller?.hide(WindowInsetsCompat.Type.systemBars())
    } else {
        controller?.show(WindowInsetsCompat.Type.statusBars())
        controller?.show(WindowInsetsCompat.Type.navigationBars())

        //controller?.show(WindowInsetsCompat.Type.systemBars())
    }
}

// 日志
fun logD(msg: String) {
    Log.d("--PlayerDemo", msg)
}

