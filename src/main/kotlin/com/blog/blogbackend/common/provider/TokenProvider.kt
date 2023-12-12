package com.blog.blogbackend.common.provider

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.expiration-seconds}")
    private val expiration: Long
) {

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createToken(authentication: Authentication): String? {
        return Jwts.builder()
            .subject((authentication.principal as CustomUserDetails).getId().toString())
            .signWith(key)
            .expiration(Date(System.currentTimeMillis() + expiration * 1000))
            .compact()
    }

    fun validateToken(token: String): String {
        val jwt = Jwts.parser().verifyWith(key).build().
            parseSignedClaims(token)

        return jwt.payload.subject
    }
}