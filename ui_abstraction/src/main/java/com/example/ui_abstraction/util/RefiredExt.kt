package com.example.ui_abstraction.util

import android.content.Context
import android.content.Intent

inline fun <reified T> mStartActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}