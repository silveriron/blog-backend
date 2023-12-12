package com.blog.blogbackend.common.filter

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.token.service.TokenService
import com.blog.blogbackend.domain.user.service.UserService
import jakarta.servlet.FilterChain
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

        getJwtFromRequest(request)?.let {
            val user = userService.findByUserId(tokenService.validateToken(it).toLong())

            if (user.isPresent) {
               val userDetails = CustomUserDetails(user.get())

                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            }
        }

        return filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.isNotEmpty() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}