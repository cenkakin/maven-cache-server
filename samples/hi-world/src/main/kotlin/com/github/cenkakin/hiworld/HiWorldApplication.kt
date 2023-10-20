package com.github.cenkakin.hiworld

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HiWorldApplication

fun main(args: Array<String>) {
    runApplication<HiWorldApplication>(*args)
}
