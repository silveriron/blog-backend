package com.blog.blogbackend.domain.follow.entity

import java.io.Serializable

data class FollowId(
    var followerId: Long,
    var followingId: Long
): Serializable {
    constructor(): this(0, 0)
}
