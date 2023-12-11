package com.blog.blogbackend.domain.user.service

import com.blog.blogbackend.domain.user.entity.AuthProvider
import com.blog.blogbackend.domain.user.entity.User
import com.blog.blogbackend.domain.user.entity.UserRole
import com.blog.blogbackend.domain.user.entity.UserStatus
import com.blog.blogbackend.domain.user.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean


@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    private val user = User(
        email = "test@test.com",
        name = "Test User",
        password = "test",
        image = "test",
        role = UserRole.ROLE_USER,
        status = UserStatus.UNVERIFIED,
        provider = AuthProvider.LOCAL
    )

    @Test
    fun `findByEmail returns user when email exists`() {
        `when`(userRepository.findByEmail("test@test.com")).thenReturn(user)

        val result = userService.findByEmail("test@test.com")

        assertEquals(user, result)

    }

    @Test
    fun `findByEmail returns null when email does not exist`() {
        `when`(userRepository.findByEmail("test@test.com")).thenReturn(null)

        val result = userService.findByEmail("test@test.com")

        assertNull(result)
    }

    @Test
    fun `save persists user and returns it`() {
        `when`(userRepository.save(user)).thenReturn(user)

        val result = userService.save(user)

        assertEquals(user, result)
    }

}