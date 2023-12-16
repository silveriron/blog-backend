package com.blog.blogbackend.domain.auth.entity

import com.blog.blogbackend.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomUserDetails(
    private val user: User,
    private val attributes: MutableMap<String, Any>? = mutableMapOf()
): UserDetails, OAuth2User {
    override fun getName(): String {
        return user.username
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes!!
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        authorities.add(GrantedAuthority { user.role.name })
        return authorities
    }

    fun getId(): Long {
        return user.id
    }

    fun getUser(): User {
        return user
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
         return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}