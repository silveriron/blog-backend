package com.blog.blogbackend.domain.article.dto

data class CreateArticleDto(
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>
)
