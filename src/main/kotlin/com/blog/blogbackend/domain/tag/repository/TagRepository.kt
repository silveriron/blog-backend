package com.blog.blogbackend.domain.tag.repository

import com.blog.blogbackend.domain.tag.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
}