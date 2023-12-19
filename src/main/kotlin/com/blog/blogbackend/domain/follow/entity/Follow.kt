package com.blog.blogbackend.domain.follow.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@IdClass(FollowId::class)
@Entity
data class Follow(
    @Id
    val followerId: Long,
    @Id
    val followingId: Long,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
