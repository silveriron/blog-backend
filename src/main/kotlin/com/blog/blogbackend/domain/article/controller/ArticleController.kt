package com.blog.blogbackend.domain.article.controller

import com.blog.blogbackend.domain.article.dto.ArticlesRes
import com.blog.blogbackend.domain.article.dto.CreateArticleDto
import com.blog.blogbackend.domain.article.entity.Article
import com.blog.blogbackend.domain.article.service.ArticleService
import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class ArticleController(
  val articleService: ArticleService
) {

  @GetMapping("/articles")
  fun getArticles(@RequestParam offset: Int,
                  @RequestParam limit: Int,
                  @RequestParam tag: String?,
                  @RequestParam author: String?,
                  @RequestParam favorited: String?
                  ): ResponseEntity<ArticlesRes> {

    val articles = articleService.getArticles(offset, limit, tag, author, favorited)
    val count = articleService.getArticlesCount(tag, author, favorited)

    return ResponseEntity.ok(ArticlesRes(articles, count))
  }

  @PostMapping("/articles")
    fun createArticle(
        authentication: Authentication,
        @RequestBody
        createArticleDto: CreateArticleDto
    ): ResponseEntity<Article> {

        println("createArticleDto")


      val user = (authentication.principal as CustomUserDetails).getUser()

        val createdArticle = articleService.createArticle(createArticleDto, user)
        return ResponseEntity.ok(createdArticle)
    }
}