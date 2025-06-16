package com.example.vitesse

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import coil3.video.VideoFrameDecoder
import com.example.vitesse.data.AppContainer
import com.example.vitesse.data.AppDatabaseContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VitesseApplication : Application(), SingletonImageLoader.Factory {
    lateinit var container: AppContainer
    // A long-lived CoroutineScope tied to the application's lifecycle
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        container = AppDatabaseContainer(this, applicationScope)
    }

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context = context)
//        .memoryCache{ MemoryCache.Builder().maxSizePercent(context = context, percent = 0.02).build() }
            .memoryCache{ MemoryCache.Builder().maxSizeBytes(1000000000).build() }
//        .diskCache{ DiskCache.Builder().maxSizeBytes(2*10f.pow(9).toLong()).build()}
            .diskCachePolicy(CachePolicy.DISABLED)
            .logger(DebugLogger())
            .components{add(VideoFrameDecoder.Factory())}
            .build()
    }
}