package com.github.cenkakin.mavencacheserver.fileManager

import com.github.cenkakin.mavencacheserver.exception.MavenCacheFileFoundException
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class FileSystemMavenCacheFileManager(override val baseFolderToStore: String) : MavenCacheFileManager {

    companion object {
        private val logger = LoggerFactory.getLogger(FileSystemMavenCacheFileManager::class.java)
    }

    override fun store(key: String, content: InputStream, contentLength: Long) {
        val file = File("$baseFolderToStore/$key")
        if(!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        content.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    override fun get(key: String): InputStream {
        return try {
            File("$baseFolderToStore/$key").inputStream()
        } catch (ex: FileNotFoundException) {
            logger.info("Maven cache file not found, key: {}", key)
            throw MavenCacheFileFoundException(key)
        }
    }
}
