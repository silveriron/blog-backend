package com.blog.blogbackend.domain.user.service

import com.blog.blogbackend.domain.user.entity.AuthProvider
import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findByEmail(email: String) = userRepository.findByEmail(email)

    fun save(user: User) = userRepository.save(user)
    fun findByUserId(userId: Long) = userRepository.findById(userId)

    fun findByUsername(username: String) = userRepository.findByUsername(username)
    fun findByProviderAndProviderId(provider: AuthProvider, providerId: String) = userRepository.findByProviderAndProviderId(provider, providerId)
    fun findByRefreshToken(refreshToken: String) = userRepository.findByRefreshToken(refreshToken)
}