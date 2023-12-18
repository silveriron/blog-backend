package com.blog.blogbackend.common.handler

import com.blog.blogbackend.domain.token.service.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val tokenService: TokenService
):  SimpleUrlAuthenticationSuccessHandler(){

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val token = tokenService.createToken(authentication)

        response.sendRedirect("http://localhost:8080/api/users/code?accessToken=${token.accessToken}&refreshToken=${token.refreshToken}")

    }
}