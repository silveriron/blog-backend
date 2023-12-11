package com.blog.blogbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BlogBackendApplication

fun main(args: Array<String>) {
	runApplication<BlogBackendApplication>(*args)
}
