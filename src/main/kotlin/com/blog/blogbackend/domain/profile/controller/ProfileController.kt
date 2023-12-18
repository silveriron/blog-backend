package com.blog.blogbackend.domain.profile.controller

import com.blog.blogbackend.domain.profile.dto.ProfileRes
import com.blog.blogbackend.domain.profile.service.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val profileService: ProfileService
) {

    @GetMapping("/{username}")
    fun getProfile(
        @PathVariable
        username: String
    ): ResponseEntity<ProfileRes> {

        val user = profileService.getProfile(username)

        return ResponseEntity.ok(ProfileRes(user))
    }
}