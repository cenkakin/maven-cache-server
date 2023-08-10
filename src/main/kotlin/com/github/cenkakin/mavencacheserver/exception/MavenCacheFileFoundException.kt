package com.github.cenkakin.mavencacheserver.exception

class MavenCacheFileFoundException(key: String) : Exception("File with key $key not found.")
