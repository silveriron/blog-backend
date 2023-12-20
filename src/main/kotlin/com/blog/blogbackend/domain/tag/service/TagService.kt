package com.blog.blogbackend.domain.tag.service

import com.blog.blogbackend.domain.tag.entity.Tag
import com.blog.blogbackend.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {

    fun getTag(
        tag: String
    ): Tag? {
        return tagRepository.findByName(tag)
    }

    fun createTag(
        tag: String
    ): Tag {
        return tagRepository.findByName(tag) ?: tagRepository.save(Tag(name = tag))
    }

}