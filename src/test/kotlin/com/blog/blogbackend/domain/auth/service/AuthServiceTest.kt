package com.blog.blogbackend.domain.auth.service

import com.blog.blogbackend.domain.auth.dto.SignupReq
import com.blog.blogbackend.domain.user.entity.AuthProvider
import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.entity.UserRole
import com.blog.blogbackend.domain.user.entity.UserStatus
import com.blog.blogbackend.domain.user.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private lateinit var authService: AuthService

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `signup creates new user when email is not taken`() {

        val signupReq = SignupReq(
            email = "test@test.com", username="Test User", password =  "password", image = null)

        `when`(passwordEncoder.encode(signupReq.password)).thenReturn("password")

        val user = User(
            email = signupReq.email,
            password = passwordEncoder.encode(signupReq.password),
            username = signupReq.username,
            image = signupReq.image,
            role = UserRole.ROLE_USER,
            status = UserStatus.UNVERIFIED,
            provider = AuthProvider.LOCAL
        )

        `when`(userService.findByEmail(signupReq.email)).thenReturn(null)
        `when`(userService.save(user)).thenReturn(user)

        val result = authService.signup(signupReq)

        assertEquals(user, result)
    }

    @Test
    fun `signup throws exception when email is already taken`() {

        val signupReq = SignupReq(email = "test@test.com", password =  "Test User",  username = "password", image = null)

        `when`(passwordEncoder.encode(signupReq.password)).thenReturn("password")

        val user = User(
            email = signupReq.email,
            password = passwordEncoder.encode(signupReq.password),
            username = signupReq.username,
            role = UserRole.ROLE_USER,
            status = UserStatus.UNVERIFIED,
            provider = AuthProvider.LOCAL
        )
        `when`(userService.findByEmail(signupReq.email)).thenReturn(user)

        assertThrows<Exception> {
            authService.signup(signupReq)
        }
    }
}