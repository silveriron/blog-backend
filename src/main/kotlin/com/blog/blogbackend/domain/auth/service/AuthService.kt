package com.blog.blogbackend.domain.auth.service

import com.blog.blogbackend.domain.auth.dto.SignupReq
import com.blog.blogbackend.domain.user.entity.AuthProvider
import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.entity.UserRole
import com.blog.blogbackend.domain.user.entity.UserStatus
import com.blog.blogbackend.domain.user.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun signup(signupReq: SignupReq): User {

        userService.findByEmail(signupReq.email)?.let {
            throw Exception("이미 존재하는 유저입니다.")
        }

        val user = User(
            email = signupReq.email,
            password = passwordEncoder.encode(signupReq.password),
            username = signupReq.username,
            image = signupReq.image,
            role = UserRole.ROLE_USER,
            status = UserStatus.UNVERIFIED,
            provider = AuthProvider.LOCAL
        )

        return userService.save(user)
    }
}