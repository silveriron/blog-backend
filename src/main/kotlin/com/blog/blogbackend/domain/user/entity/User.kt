package com.blog.blogbackend.domain.user.entity

import com.blog.blogbackend.domain.article.entity.Article
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
    var password: String,
    var username: String,
    var bio: String? = null,
    var image: String? = null,
    @Enumerated(EnumType.STRING)
    var role: UserRole,
    @Enumerated(EnumType.STRING)
    var status: UserStatus,
    @Enumerated(EnumType.STRING)
    val provider: AuthProvider,
    val providerId: String? = null,
    var refreshToken: String? = null,

    @OneToMany
    var articleList: List<Article>? = null,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)
