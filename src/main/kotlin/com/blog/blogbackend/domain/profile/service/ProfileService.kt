package com.blog.blogbackend.domain.profile.service

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.follow.service.FollowService
import com.blog.blogbackend.domain.profile.dto.ProfileRes
import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val userService: UserService,
    private val followService: FollowService
) {

    fun getProfile(username: String): User {

        return userService.findByUsername(username) ?: throw Exception("User not found")
    }

    fun follow(username: String, authentication: Authentication): ProfileRes {
        val user = userService.findByUsername(username) ?: throw Exception("User not found")

        val userDetails = authentication.principal as CustomUserDetails


        val isFollow = followService.follow(userDetails.getUser(), user)

        return ProfileRes(user, isFollow)
    }

    fun unfollow(username: String, authentication: Authentication): ProfileRes {
        val user = userService.findByUsername(username) ?: throw Exception("User not found")

        val userDetails = authentication.principal as CustomUserDetails


        val isFollow = followService.unfollow(userDetails.getUser(), user)

        return ProfileRes(user, isFollow)

    }
}
