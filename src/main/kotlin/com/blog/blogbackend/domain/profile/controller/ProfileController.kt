package com.blog.blogbackend.domain.profile.controller

import com.blog.blogbackend.domain.profile.dto.ProfileRes
import com.blog.blogbackend.domain.profile.service.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/{username}/follow")
    fun follow(
        @PathVariable
        username: String,
        authentication: Authentication
    ): ResponseEntity<ProfileRes> {

        val profileRes = profileService.follow(username, authentication)

        return ResponseEntity.ok(profileRes)
    }

    @DeleteMapping("/{username}/follow")
    fun unfollow(
        @PathVariable
        username: String,
        authentication: Authentication
    ): ResponseEntity<ProfileRes> {

        val profileRes = profileService.unfollow(username, authentication)

        return ResponseEntity.ok(profileRes)
    }
}