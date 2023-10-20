package com.github.cenkakin.hiworld.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class HiController {

    @GetMapping("/hi")
    fun hi() = mapOf("message" to "Hi?")
}
