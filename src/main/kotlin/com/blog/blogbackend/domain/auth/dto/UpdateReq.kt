package com.blog.blogbackend.domain.auth.dto

data class UpdateReq(
  val username: String? = null,
  val bio: String? = null,
  val image: String? = null,
)
