package com.blog.blogbackend.common.utils

import jakarta.servlet.http.Cookie

class CookieFactory {

    companion object{

    fun generateCookie(name: CookieName, value: String): Cookie {

        return when(name) {
            CookieName.ACCESS_TOKEN -> generateCookie(name, value, 60 * 60)
            CookieName.REFRESH_TOKEN -> generateCookie(name, value, 60 * 60 * 24 * 7)
            else -> throw IllegalArgumentException("Invalid Cookie Name")
        }
    }

        private fun generateCookie(name: CookieName, value: String, maxAge: Int): Cookie {
            val cookie = Cookie(name.toString(), value)
            cookie.isHttpOnly = true
            cookie.maxAge = maxAge
            cookie.path = "/"
            return cookie
        }
    }

}