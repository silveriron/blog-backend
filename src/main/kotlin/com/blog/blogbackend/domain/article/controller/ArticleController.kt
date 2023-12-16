package com.blog.blogbackend.domain.article.controller

import com.blog.blogbackend.domain.article.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class ArticleController(
  val articleService: ArticleService
) {

  @GetMapping("/articles")
  fun getArticles(@RequestParam offset: Int, @RequestParam limit: Int) = articleService.getArticles(offset, limit)

}