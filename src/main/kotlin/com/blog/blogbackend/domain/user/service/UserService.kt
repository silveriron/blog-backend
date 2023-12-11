package com.blog.blogbackend.domain.user.service

import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findByEmail(email: String) = userRepository.findByEmail(email)

    fun save(user: User) = userRepository.save(user)
}