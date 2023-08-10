package com.github.cenkakin.mavencacheserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MavenCacheApplication

fun main(args: Array<String>) {
    runApplication<MavenCacheApplication>(*args)
}
