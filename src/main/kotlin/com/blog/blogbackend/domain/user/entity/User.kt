package com.blog.blogbackend.domain.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val email: String,
    @JsonIgnore
    var password: String,
    var username: String,
    var bio: String? = null,
    var image: String? = null,
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    var role: UserRole,
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    var status: UserStatus,
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    val provider: AuthProvider,
    @JsonIgnore
    val providerId: String? = null,
    @JsonIgnore
    var refreshToken: String? = null,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)
