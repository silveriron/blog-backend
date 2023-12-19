package com.blog.blogbackend.domain.follow.repository

import com.blog.blogbackend.domain.follow.entity.Follow
import com.blog.blogbackend.domain.follow.entity.FollowId
import org.springframework.data.jpa.repository.JpaRepository


interface FollowRepository: JpaRepository<Follow, FollowId> {
}