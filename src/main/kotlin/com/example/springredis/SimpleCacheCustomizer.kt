package com.example.springredis

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.stereotype.Component

//@Component
//class SimpleCacheCustomizer : CacheManagerCustomizer<ConcurrentMapCacheManager> {
//    override fun customize(cacheManager: ConcurrentMapCacheManager) {
//        cacheManager.setCacheNames(listOf("users", "transactions"))
//    }
//}