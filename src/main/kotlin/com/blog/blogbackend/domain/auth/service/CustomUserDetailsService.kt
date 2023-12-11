package com.blog.blogbackend.domain.auth.service

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.user.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userService: UserService
): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userService.findByEmail(email) ?: throw UsernameNotFoundException("해당 유저가 존재하지 않습니다.")

        return CustomUserDetails(user)
    }
}