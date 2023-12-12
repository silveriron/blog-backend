package com.blog.blogbackend.common.config

import com.blog.blogbackend.common.filter.JwtAuthenticationFilter
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenService: TokenService,
    private val userService: UserService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it.requestMatchers("/api/auth/signup", "api/auth/login").permitAll()
                .anyRequest().authenticated() }
            .addFilterBefore(
                JwtAuthenticationFilter(tokenService, userService), UsernamePasswordAuthenticationFilter::class.java)
            .csrf { it.disable() }

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