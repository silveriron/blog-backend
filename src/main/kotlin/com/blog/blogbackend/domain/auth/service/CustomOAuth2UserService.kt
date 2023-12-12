package com.blog.blogbackend.domain.auth.service

import com.blog.blogbackend.domain.auth.entity.CustomUserDetails
import com.blog.blogbackend.domain.user.entity.AuthProvider
import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.entity.UserRole
import com.blog.blogbackend.domain.user.entity.UserStatus
import com.blog.blogbackend.domain.user.service.UserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userService: UserService
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()

        val oAuth2User = delegate.loadUser(userRequest)

        val provider = if (userRequest.clientRegistration?.registrationId == "google") AuthProvider.GOOGLE else AuthProvider.LOCAL
        val providerId = oAuth2User.attributes["sub"].toString()

        val user = userService.findByProviderAndProviderId(provider, providerId)?: userService.save(
            User(
                username = oAuth2User.attributes["name"].toString(),
                email = oAuth2User.attributes["email"].toString(),
                provider = provider,
                providerId = providerId,
                password = "",
                role = UserRole.ROLE_USER,
                status = UserStatus.VERIFIED
            )
        )

        return CustomUserDetails(user, oAuth2User.attributes)
    }
}