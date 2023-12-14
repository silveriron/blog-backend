package com.blog.blogbackend.domain.article.dto

data class UpdateArticleDto(
    val title: String? = null,
    val description: String? = null,
    val body: String? = null
)
