package com.me.library.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> fromJson(json: String): T? {
    return try {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(json, type)
    } catch (e: Exception) {
        null
    }
}

fun Any.toJson():String{
    return Gson().toJson(this)
}
