package com.github.cenkakin.mavencacheserver.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("aws")
data class AwsProperties(
    var region: String = "",
    var accessKeyId: String = "",
    var secretAccessKey: String = "",
)
