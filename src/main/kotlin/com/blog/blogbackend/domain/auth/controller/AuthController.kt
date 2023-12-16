package com.blog.blogbackend.domain.auth.controller

import com.blog.blogbackend.common.utils.CookieFactory
import com.blog.blogbackend.common.utils.CookieName
import com.blog.blogbackend.domain.auth.dto.LoginReq
import com.blog.blogbackend.domain.auth.dto.SignupReq
import com.blog.blogbackend.domain.auth.dto.UpdateReq
import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.auth.service.AuthService
import com.blog.blogbackend.domain.token.service.TokenService
import com.blog.blogbackend.domain.user.entity.User
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

        val accessTokenCookie = CookieFactory.generateCookie(CookieName.ACCESS_TOKEN, token.accessToken)
        val refreshTokenCookie = CookieFactory.generateCookie(CookieName.REFRESH_TOKEN, token.refreshToken)

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)

        return ResponseEntity.ok("로그인 성공")
    }

    @GetMapping("/")
    fun me(
        authentication: Authentication
    ): ResponseEntity<User> {

        return ResponseEntity.ok((authentication.principal as CustomUserDetails).getUser())
    }

    @PutMapping("/")
    fun update(
        authentication: Authentication,
        @Valid
        @RequestBody
        updateReq: UpdateReq
    ): ResponseEntity<User> {

          val user = authService.update(authentication, updateReq)

          logger.info("user: $user")

        return ResponseEntity.ok(user)
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

        response.sendRedirect("http://localhost:8080/api/auth/me")
    }
}