package com.blog.blogbackend.common.filter

import com.blog.blogbackend.common.utils.CookieFactory
import com.blog.blogbackend.common.utils.CookieName
import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.token.dto.Token
import com.blog.blogbackend.domain.token.service.TokenService
import com.blog.blogbackend.domain.user.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val tokenService: TokenService,
    private val userService: UserService,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {

        val token = getJwtFromRequest(request)

        if (token.accessToken.isNotEmpty()) {

            try {
                val user = userService.findByUserId(tokenService.validateToken(token.accessToken).toLong())

                if (user.isPresent) {

                    val userDetails = CustomUserDetails(user.get())

                    val authentication: Authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)


                    SecurityContextHolder.getContext().authentication = authentication
                }

            } catch (e: ExpiredJwtException) {

                if (token.refreshToken.isNotEmpty() && tokenService.validateToken(token.refreshToken).isNotEmpty()) {

                    userService.findByRefreshToken(token.refreshToken)?.let {
                        val accessToken = tokenService.createAccessToken(it.id)

                        val accessTokenCookie = CookieFactory.generateCookie(CookieName.ACCESS_TOKEN, accessToken)

                        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                            CustomUserDetails(it),
                            null,
                            CustomUserDetails(it).authorities
                        )

                        response.addCookie(accessTokenCookie)
                    }

                }
            }
        }

        return filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): Token {

        val accessToken = request.cookies?.find { it.name == CookieName.ACCESS_TOKEN.name }?.value ?: ""
        val refreshToken = request.cookies?.find { it.name == CookieName.REFRESH_TOKEN.name }?.value ?: ""

        return Token(accessToken, refreshToken)
    }
}