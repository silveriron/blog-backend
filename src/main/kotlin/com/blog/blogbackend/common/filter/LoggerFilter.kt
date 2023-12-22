package com.blog.blogbackend.common.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Integer.MIN_VALUE)
class LoggerFilter: Filter {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        val req: HttpServletRequest = request as HttpServletRequest
        val res: HttpServletResponse = response as HttpServletResponse


        chain.doFilter(request, response)


        val reqHeaders = req.headerNames.toList().stream().map {
            "[$it: ${req.getHeader(it)}]"
        }
            .toArray()

        val resHeaders = res.headerNames.toList().stream().map {
            "[$it: ${res.getHeader(it)}]"
        }
            .toArray()

        logger.info(" >>>>>>>>>>> request uri: ${req.requestURI}, method: ${req.method}, headers: ${reqHeaders.joinToString(", ")}, status: ${res.status}")
        logger.info(" <<<<<<<<<<< response uri: ${req.requestURI}, method: ${req.method}, headers: ${resHeaders.joinToString(", ")}, status: ${res.status}")
    }
}