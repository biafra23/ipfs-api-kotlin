package io.ipfs.kotlin.defaults

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient

internal fun createOKHTTP() = OkHttpClient.Builder().build()

internal fun createMoshi() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Add support for Kotlin data classes
    .build()