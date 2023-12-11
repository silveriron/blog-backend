package com.blog.blogbackend.domain.auth.dto

data class SignupReq(
    val email: String,
    val password: String,
    val name: String,
    val image: String?
)
