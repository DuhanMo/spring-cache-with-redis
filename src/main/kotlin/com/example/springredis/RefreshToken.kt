package com.example.springredis

import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("refresh_token")
class RefreshToken(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String
)