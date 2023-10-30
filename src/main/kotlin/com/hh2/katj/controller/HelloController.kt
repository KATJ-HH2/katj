package com.hh2.katj.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    var access = 0

    @GetMapping("/hello")
    fun hello(): String {
        return "slack notification hello world!!"
    }

    @GetMapping("/test")
    fun test(): String {
        access += 1
        return "app-${access}"
    }
}