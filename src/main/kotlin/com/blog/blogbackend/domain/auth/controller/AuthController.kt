package com.blog.blogbackend.domain.auth.controller

import com.blog.blogbackend.common.utils.CookieFactory
import com.blog.blogbackend.common.utils.CookieName
import com.blog.blogbackend.domain.auth.dto.LoginReq
import com.blog.blogbackend.domain.auth.dto.SignupReq
import com.blog.blogbackend.domain.auth.dto.UpdateReq
import com.blog.blogbackend.domain.auth.dto.UserRes
import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.auth.service.AuthService
import com.blog.blogbackend.domain.token.service.TokenService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/users")
@RestController
class AuthController(
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/")
    fun signup(
        @Valid
        @RequestBody
        signupReq: SignupReq
    ): ResponseEntity<UserRes> {

        val user = authService.signup(signupReq)

        return ResponseEntity.ok(UserRes(user))
    }

    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        loginReq: LoginReq,
        response: HttpServletResponse
    ): ResponseEntity<UserRes> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginReq.email, loginReq.password)
        )

        val token = tokenService.createToken(authentication)

        val accessTokenCookie = CookieFactory.generateCookie(CookieName.ACCESS_TOKEN, token.accessToken)
        val refreshTokenCookie = CookieFactory.generateCookie(CookieName.REFRESH_TOKEN, token.refreshToken)

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)

        val user = authentication.principal as CustomUserDetails

        return ResponseEntity.ok(UserRes(user.getUser()))
    }

    @GetMapping("/")
    fun me(
        authentication: Authentication
    ): ResponseEntity<UserRes> {

        val user = authentication.principal as CustomUserDetails

        return ResponseEntity.ok(UserRes(user.getUser()))
    }

    @PutMapping("/")
    fun update(
        authentication: Authentication,
        @Valid
        @RequestBody
        updateReq: UpdateReq
    ): ResponseEntity<UserRes> {

          val user = authService.update(authentication, updateReq)

          logger.info("user: $user")

        return ResponseEntity.ok(UserRes(user))
    }

    @GetMapping("/code")
    fun code(
        @RequestParam
        accessToken: String,
        @RequestParam
        refreshToken: String,
        response: HttpServletResponse
    ) {

        val accessTokenCookie = CookieFactory.generateCookie(CookieName.ACCESS_TOKEN, accessToken)
        val refreshTokenCookie = CookieFactory.generateCookie(CookieName.REFRESH_TOKEN, refreshToken)

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)

        response.sendRedirect("http://localhost:8080/api/users")
    }
}