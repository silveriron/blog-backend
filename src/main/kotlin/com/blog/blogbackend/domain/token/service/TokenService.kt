package com.blog.blogbackend.domain.token.service

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.token.dto.Token
import com.blog.blogbackend.domain.user.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.access-token-expiration-seconds}")
    private val accessExpiration: Long,
    @Value("\${jwt.refresh-token-expiration-seconds}")
    private val refreshExpiration: Long,
    private val userService: UserService
) {

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createToken(authentication: Authentication): Token {
        val accessToken = Jwts.builder()
            .subject((authentication.principal as CustomUserDetails).getId().toString())
            .signWith(key)
            .expiration(Date(System.currentTimeMillis() + accessExpiration * 1000))
            .compact()

        val refreshToken = Jwts.builder()
            .subject((authentication.principal as CustomUserDetails).getId().toString())
            .signWith(key)
            .expiration(Date(System.currentTimeMillis() + refreshExpiration * 1000))
            .compact()

        val user = userService.findByUserId((authentication.principal as CustomUserDetails).getId())

        user.ifPresent {
            it.refreshToken = refreshToken
            userService.save(it)
        }

        return Token(accessToken, refreshToken)
    }

    fun validateToken(token: String): String {
        val jwt = Jwts.parser().verifyWith(key).build().
            parseSignedClaims(token)

        return jwt.payload.subject
    }
}