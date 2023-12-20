package com.blog.blogbackend.common.config

import com.blog.blogbackend.common.filter.JwtAuthenticationFilter
import com.blog.blogbackend.common.handler.CustomAuthenticationEntryPoint
import com.blog.blogbackend.common.handler.OAuth2SuccessHandler
import com.blog.blogbackend.domain.auth.service.CustomUserDetailsService
import com.blog.blogbackend.domain.token.service.TokenService
import com.blog.blogbackend.domain.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity(debug = true)
class SecurityConfig(
    private val tokenService: TokenService,
    private val userService: UserService,

) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it.requestMatchers("api/users/login", "api/users/code", "/api/profiles/{username}").permitAll()
                .requestMatchers("api/profiles/{username}/follow", "api/profiles/{username}/unfollow").authenticated()
                .anyRequest().authenticated() }
            .addFilterBefore(
                JwtAuthenticationFilter(tokenService, userService), UsernamePasswordAuthenticationFilter::class.java)
            .csrf { it -> it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling {it.authenticationEntryPoint(CustomAuthenticationEntryPoint())}
            .oauth2Login { it.successHandler(OAuth2SuccessHandler(tokenService)) }

        return http.build()
    }

    @Bean
    fun authenticationManager(
        customUserDetailsService: CustomUserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val daoAuthenticationProvider = DaoAuthenticationProvider()

        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder)

        return ProviderManager(daoAuthenticationProvider)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}