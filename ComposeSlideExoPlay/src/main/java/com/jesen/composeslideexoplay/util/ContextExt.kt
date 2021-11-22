package com.jesen.composeslideexoplay.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper

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

