package com.github.cenkakin.mavencacheserver.fileManager

import com.github.cenkakin.mavencacheserver.exception.MavenCacheFileFoundException
import org.slf4j.LoggerFactory
import software.amazon.awssdk.awscore.exception.AwsServiceException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.NoSuchKeyException
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream

class S3MavenCacheFileManager(private val s3Client: S3Client, override val baseFolderToStore: String) :
    MavenCacheFileManager {

    companion object {
        private val logger = LoggerFactory.getLogger(S3MavenCacheFileManager::class.java)
    }

    override fun store(key: String, content: InputStream, contentLength: Long) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(baseFolderToStore)
            .key(key)
            .build()

        try {
            s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(content, contentLength),
            )
        } catch (ex: AwsServiceException) {
            logger.error("Unable to upload maven cache file, key: {}", key, ex)
            throw ex
        }
    }

    override fun get(key: String): InputStream {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(baseFolderToStore)
            .key(key)
            .build()

        return try {
            s3Client.getObject(getObjectRequest)
        } catch (ex: NoSuchKeyException) {
            logger.info("Maven cache file not found, key: {}", key)
            throw MavenCacheFileFoundException(key)
        } catch (ex: AwsServiceException) {
            logger.error("Unable to upload maven cache file, key: {}", key, ex)
            throw ex
        }
    }
}
