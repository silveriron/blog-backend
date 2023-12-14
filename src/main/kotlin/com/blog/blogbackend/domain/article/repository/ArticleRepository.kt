package com.blog.blogbackend.domain.article.repository

import com.blog.blogbackend.domain.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {
    fun findBySlug(slug: String): Article?

    fun deleteBySlug(slug: String): Boolean
}