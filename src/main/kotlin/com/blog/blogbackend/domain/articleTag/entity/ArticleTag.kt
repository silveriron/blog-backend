package com.blog.blogbackend.domain.articleTag.entity

import com.blog.blogbackend.domain.article.entity.Article
import com.blog.blogbackend.domain.tag.entity.Tag
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
data class ArticleTag(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long,
  @ManyToOne
  val article: Article,
  @ManyToOne
  val tag: Tag,
  @CreatedDate
  val createdAt: LocalDateTime? = LocalDateTime.now(),
  @LastModifiedDate
  val updatedAt: LocalDateTime? = LocalDateTime.now(),

)