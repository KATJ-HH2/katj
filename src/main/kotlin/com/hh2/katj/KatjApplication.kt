package com.hh2.katj

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class KatjApplication

fun main(args: Array<String>) {
	runApplication<KatjApplication>(*args)
}
