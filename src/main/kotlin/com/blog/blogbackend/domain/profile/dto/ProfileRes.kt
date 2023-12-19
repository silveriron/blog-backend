package com.blog.blogbackend.domain.profile.dto

import com.blog.blogbackend.domain.user.entity.User

data class ProfileRes(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
) {
    constructor(user: User, following: Boolean): this(
        username = user.username,
        bio = user.bio,
        image = user.image,
        following = following
    )

    constructor(user: User): this(
        username = user.username,
        bio = user.bio,
        image = user.image,
        following = false
    )
}
