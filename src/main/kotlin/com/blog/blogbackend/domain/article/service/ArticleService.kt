package com.blog.blogbackend.domain.article.service

import com.blog.blogbackend.domain.article.dto.CreateArticleDto
import com.blog.blogbackend.domain.article.dto.UpdateArticleDto
import com.blog.blogbackend.domain.article.entity.Article
import com.blog.blogbackend.domain.article.repository.ArticleRepository
import com.blog.blogbackend.domain.tag.service.TagService
import com.blog.blogbackend.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val tagService: TagService
) {

    fun getArticles(offset: Int = 0,
                    limit: Int = 20,
                    tag: String?,
                    author: String?,
                    favorited: String?
    ): List<Article> {

        return articleRepository.findAll().subList(offset, offset + limit)
    }

    fun getArticle(slug: String): Article? {
        return articleRepository.findBySlug(slug)
    }

    fun createArticle(createArticleDto: CreateArticleDto, user: User): Article {
        val tagList = createArticleDto.tagList.map { tagService.createTag(it) }
        val article = Article(
            slug = createArticleDto.title,
            title = createArticleDto.title,
            description = createArticleDto.description,
            body = createArticleDto.body,
            tagList = tagList,
            author = user
        )

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

    fun getArticlesCount(
        tag: String?,
        author: String?,
        favorited: String?
    ): Int {
        return articleRepository.findAll().size
    }
}