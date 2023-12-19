package com.blog.blogbackend.domain.follow.service

import com.blog.blogbackend.domain.follow.entity.Follow
import com.blog.blogbackend.domain.follow.entity.FollowId
import com.blog.blogbackend.domain.follow.repository.FollowRepository
import com.blog.blogbackend.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository
) {
    fun follow(me: User, user: User): Boolean {
        followRepository.save(Follow(me.id, user.id))

        return true
    }

    fun unfollow(me: User, user: User): Boolean {
        followRepository.deleteById(FollowId(me.id, user.id))

        return false
    }
}