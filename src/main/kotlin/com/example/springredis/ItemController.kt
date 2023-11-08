package com.example.springredis

import java.util.*
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController(
    private val cacheManager: CacheManager
) {
    @GetMapping("/users")
    @Cacheable(value = ["users"])
    fun findAll(param: String): String {
        return param + UUID.randomUUID().toString().subSequence(0..4)
    }

    @GetMapping("/evict")
    @CacheEvict(value = ["users"])
    fun evict(param: String): String {
        return "evict"
    }

    @GetMapping("/evictAll")
    @CacheEvict(value = ["users"], allEntries = true)
    fun evictAllEntries(): String {
        return "evict All entries"
    }

    @GetMapping("/one")
    fun users(): String {
        return "one"
    }

    @GetMapping("/put")
    @CachePut(value = ["users"], condition = "#param=='plz caching'")
    fun cachePut(param: String): String {
        return "cachePut" + param + UUID.randomUUID().toString().subSequence(0..4)
    }
}