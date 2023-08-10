package com.github.cenkakin.mavencacheserver.fileManager

import java.io.InputStream

interface MavenCacheFileManager {

    val baseFolderToStore: String

    fun store(key: String, content: InputStream, contentLength: Long)

    fun get(key: String): InputStream
}