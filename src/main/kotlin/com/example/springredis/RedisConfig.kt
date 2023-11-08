package com.example.springredis

import java.time.Duration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext

@Configuration
@EnableCaching
class RedisConfig {
    @Bean
    @Primary
    fun defaultRedisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 63791))
    }

    @Bean(name = ["cacheConnectionFactory"])
    fun cacheConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 63792))
    }

    @Bean
    fun cacheManager(@Qualifier("cacheConnectionFactory") cacheConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        return RedisCacheManager.builder(cacheConnectionFactory())
            .cacheDefaults(getDefaultCacheConfig())
            .withInitialCacheConfigurations(
                mapOf(
                    "one_minute" to oneMinuteCacheConfiguration(),
                    "five_minute" to fiveMinuteCacheConfiguration()
                )
            )
            .build()
    }

    private fun getDefaultCacheConfig(): RedisCacheConfiguration {
        return defaultCacheConfig()
            .disableCachingNullValues()
            .serializeValuesWith(
                RedisSerializationContext
                    .SerializationPair
                    .fromSerializer(GenericJackson2JsonRedisSerializer())
            )
    }

    private fun oneMinuteCacheConfiguration(): RedisCacheConfiguration {
        return getDefaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1))
    }

    private fun fiveMinuteCacheConfiguration(): RedisCacheConfiguration {
        return getDefaultCacheConfig()
            .entryTtl(Duration.ofMinutes(5))
    }
}