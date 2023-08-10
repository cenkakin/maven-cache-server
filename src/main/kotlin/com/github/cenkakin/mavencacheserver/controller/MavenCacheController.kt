package com.github.cenkakin.mavencacheserver.controller

import com.github.cenkakin.mavencacheserver.exception.MavenCacheFileFoundException
import com.github.cenkakin.mavencacheserver.fileManager.MavenCacheFileManager
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class MavenCacheController(private val mavenCacheFileManager: MavenCacheFileManager) {

    companion object {
        private val logger = LoggerFactory.getLogger(MavenCacheController::class.java)
    }

    @GetMapping("/**")
    fun get(request: HttpServletRequest): ResponseEntity<InputStreamResource> {
        val requestPath = request.servletPath.removePrefix("/")
        return ResponseEntity.ok(InputStreamResource(mavenCacheFileManager.get(requestPath)))
    }

    @PutMapping("/**")
    fun store(request: HttpServletRequest): ResponseEntity<Unit> {
        val requestPath = request.servletPath.removePrefix("/")
        mavenCacheFileManager.store(requestPath, request.inputStream, request.contentLengthLong)
        logger.info("{} stored successfully", requestPath)
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Map<String, String?>> {
        logger.error("Exception happened!", ex)
        return ResponseEntity.internalServerError().body(mapOf("message" to ex.message))
    }

    @ExceptionHandler(MavenCacheFileFoundException::class)
    fun handleException(mavenCacheFileFoundException: MavenCacheFileFoundException): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }
}
