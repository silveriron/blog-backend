package com.blog.blogbackend.domain.article.service

import com.blog.blogbackend.domain.article.dto.UpdateArticleDto
import com.blog.blogbackend.domain.article.entity.Article
import com.blog.blogbackend.domain.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {

    fun getArticles(offset: Int = 0, limit: Int = 20): List<Article> {
        return articleRepository.findAll().subList(offset, offset + limit)
    }

    fun getArticle(slug: String): Article? {
        return articleRepository.findBySlug(slug)
    }

    fun createArticle(article: Article): Article {
        return articleRepository.save(article)
    }

    fun updateArticle(slug: String, updateDto: UpdateArticleDto): Article {
        val article = articleRepository.findBySlug(slug) ?: throw Exception("존재하지 않는 게시글입니다.")

        updateDto.title?.let { article.title = it }
        updateDto.description?.let { article.description = it }
        updateDto.body?.let { article.body = it }

        return articleRepository.save(article)
    }

    fun deleteArticle(slug: String): Boolean {
        return articleRepository.deleteBySlug(slug)
    }
}