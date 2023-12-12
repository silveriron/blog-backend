package com.blog.blogbackend.common.filter

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.token.dto.Token
import com.blog.blogbackend.domain.token.service.TokenService
import com.blog.blogbackend.domain.user.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val tokenService: TokenService,
    private val userService: UserService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = getJwtFromRequest(request)

        if (token.accessToken.isNotEmpty()) {

            try {
                val user = userService.findByUserId(tokenService.validateToken(token.accessToken).toLong())

                if (user.isPresent) {
                    val userDetails = CustomUserDetails(user.get())

                    SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                }

            } catch (e: ExpiredJwtException) {

                if (token.refreshToken.isNotEmpty() && tokenService.validateToken(token.refreshToken).isNotEmpty()) {

                    userService.findByRefreshToken(token.refreshToken)?.let {
                        val accessToken = tokenService.createAccessToken(it.id)

                        val accessTokenCookie = Cookie("accessToken", accessToken)

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

        val accessToken = request.cookies?.find { it.name == "accessToken" }?.value ?: ""
        val refreshToken = request.cookies?.find { it.name == "refreshToken" }?.value ?: ""

        return Token(accessToken, refreshToken)

//        val bearerToken = request.getHeader("Authorization")
//        if (bearerToken != null && bearerToken.isNotEmpty() && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7)
//        }
//        return null
    }
}