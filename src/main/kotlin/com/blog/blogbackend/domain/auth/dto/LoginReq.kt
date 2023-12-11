package com.blog.blogbackend.domain.auth.dto

data class LoginReq(
    val email: String,
    val password: String
)
