package com.github.cenkakin.mavencacheserver.configuration

import com.github.cenkakin.mavencacheserver.configuration.properties.AwsProperties
import com.github.cenkakin.mavencacheserver.fileManager.FileSystemMavenCacheFileManager
import com.github.cenkakin.mavencacheserver.fileManager.MavenCacheFileManager
import com.github.cenkakin.mavencacheserver.fileManager.S3MavenCacheFileManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@EnableConfigurationProperties(AwsProperties::class)
@Configuration
class MavenCacheConfiguration(@Value("\${maven-cache-server.baseFolderToStore}") private val baseFolderToUpload: String) {

    @Bean
    @ConditionalOnProperty(prefix = "maven-cache-server", name = ["storeType"], havingValue = "S3")
    fun s3MavenCacheFileManager(awsProperties: AwsProperties): MavenCacheFileManager {
        val awsBasicCredentials = AwsBasicCredentials.create(awsProperties.accessKeyId, awsProperties.secretAccessKey)
        val s3Client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
            .region(Region.of(awsProperties.region))
            .build()
        return S3MavenCacheFileManager(s3Client, baseFolderToUpload)
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "maven-cache-server",
        name = ["storeType"],
        havingValue = "FILE_SYSTEM",
        matchIfMissing = true,
    )
    fun fileSystemMavenCacheFileManager(): MavenCacheFileManager {
        return FileSystemMavenCacheFileManager(baseFolderToUpload)
    }
}
