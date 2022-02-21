package com.example.task.utility.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun<T> T.serializeToGson(gson: Gson): String = gson.toJson(this)

inline fun <reified T> Gson.deserializeFromGson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
