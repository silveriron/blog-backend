package com.blog.blogbackend.domain.tag.entity

import com.blog.blogbackend.domain.articleTag.entity.ArticleTag
import jakarta.persistence.*

@Entity
data class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    @OneToMany(mappedBy = "tag")
    val articleTags: List<ArticleTag> = listOf(),
)
