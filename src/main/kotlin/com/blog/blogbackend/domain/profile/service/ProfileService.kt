package com.blog.blogbackend.domain.profile.service

import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.service.UserService
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val userService: UserService
) {

    fun getProfile(username: String): User {

        return userService.findByUsername(username) ?: throw Exception("User not found")
    }
}
