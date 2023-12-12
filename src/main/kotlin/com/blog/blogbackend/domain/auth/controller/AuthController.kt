package com.blog.blogbackend.domain.auth.controller

import com.blog.blogbackend.domain.auth.dto.LoginReq
import com.blog.blogbackend.domain.auth.dto.SignupReq
import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.auth.service.AuthService
import com.blog.blogbackend.domain.token.service.TokenService
import com.blog.blogbackend.domain.user.entity.User
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

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
        loginReq: LoginReq,
        response: HttpServletResponse
    ): ResponseEntity<String> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginReq.email, loginReq.password)
        )

        val token = tokenService.createToken(authentication)

        val accessTokenCookie = Cookie("accessToken", token.accessToken)
        val refreshTokenCookie = Cookie("refreshToken", token.refreshToken)

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)

        return ResponseEntity.ok("로그인 성공")
    }

    @GetMapping("/me")
    fun me(
        authentication: Authentication
    ): ResponseEntity<String> {

        return ResponseEntity.ok((authentication.principal as CustomUserDetails).username)
    }
}