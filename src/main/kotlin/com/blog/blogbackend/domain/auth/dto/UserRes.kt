package com.blog.blogbackend.domain.auth.dto

import com.blog.blogbackend.domain.user.entity.User

data class UserRes(
    val email: String,
    val username: String,
    val bio: String? = null,
    val image: String? = null,
) {
    constructor(user: User) : this(
        email = user.email,
        username = user.username,
        bio = user.bio,
        image = user.image
    )
}
