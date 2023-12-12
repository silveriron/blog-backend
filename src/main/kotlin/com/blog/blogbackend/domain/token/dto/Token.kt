package com.blog.blogbackend.domain.token.dto

data class Token(
    val accessToken: String,
    val refreshToken: String
)
