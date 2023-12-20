package com.blog.blogbackend.domain.article.repository

import com.blog.blogbackend.domain.article.entity.Article
import com.blog.blogbackend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {
    fun findBySlug(slug: String): Article?

    fun findByAuthor(author: User): List<Article>

    fun deleteBySlug(slug: String): Boolean
}