package com.github.cenkakin.mavencacheserver.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("aws")
data class AwsProperties(
    var region: String = "eu-west-1",
    var accessKeyId: String = "",
    var secretAccessKey: String = "",
    var bucket: String = "",
)
