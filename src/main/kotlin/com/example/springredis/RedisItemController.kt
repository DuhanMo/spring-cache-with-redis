package com.example.springredis

import java.util.*
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis")
class RedisItemController(
    private val cacheManager: CacheManager,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    @GetMapping("/users")
    @Cacheable(value = ["one_minute"])
    fun findAll(param: String): Data {
        return Data(param + UUID.randomUUID().toString().subSequence(0..4))
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
    fun cachePut(param: String): Data {
        return Data("cachePut" + param + UUID.randomUUID().toString().subSequence(0..4))
    }

    @GetMapping("/foo")
    @Cacheable(value = ["foo"])
    fun fooCache(param: String): Data {
        return Data(param + UUID.randomUUID().toString().subSequence(0..4))
    }

    @PostMapping("/refresh")
    fun saveRefreshToken(user: String): RefreshToken {
        return refreshTokenRepository.save(RefreshToken(name = user))
    }
}


data class Data(
    val field1: String
)