package com.blog.blogbackend.domain.article.dto

import com.blog.blogbackend.domain.article.entity.Article

data class ArticlesRes(
    val articles: List<Article>,
    val articlesCount: Int
)
