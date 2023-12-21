package com.blog.blogbackend.domain.article.entity

import com.blog.blogbackend.domain.user.entity.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var slug: String,
    var title: String,
    var description: String,
    var body: String,

    @ManyToOne
    var author: User,

    @CreatedDate
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    )
