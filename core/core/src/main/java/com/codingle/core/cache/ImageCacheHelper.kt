package com.codingle.core.cache

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy.ENABLED
import coil.request.ImageRequest
import javax.inject.Inject

class ImageCacheHelper @Inject constructor(private val context: Context) {

    fun downloadAndCacheFile(url: String) = ImageLoader(context)
        .newBuilder()
        .build()
        .enqueue(
            ImageRequest
                .Builder(context)
                .diskCachePolicy(ENABLED)
                .memoryCachePolicy(ENABLED)
                .diskCacheKey(url)
                .memoryCacheKey(url)
                .build()
        )
}