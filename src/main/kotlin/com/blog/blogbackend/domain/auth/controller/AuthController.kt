package com.blog.blogbackend.domain.auth.controller

import com.blog.blogbackend.domain.auth.dto.LoginReq
import com.blog.blogbackend.domain.auth.dto.SignupReq
import com.blog.blogbackend.domain.auth.service.AuthService
import com.blog.blogbackend.domain.user.entity.User
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager

) {

    @PostMapping("/signup")
    fun signup(
        @Valid
        @RequestBody
        signupReq: SignupReq
    ): ResponseEntity<User> {

        val user = authService.signup(signupReq)

        return ResponseEntity.ok(user)
    }

    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        loginReq: LoginReq
    ): ResponseEntity<String> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginReq.email, loginReq.password)
        )

        return ResponseEntity.ok("로그인 성공")
    }

}