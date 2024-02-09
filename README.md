# maven-cache-server

This is a simple Spring application that can function as a remote Maven cache server. 

This project enables using a Maven cache to cache files locally or remotely in AWS S3.

## Quickstart

Before you begin, make sure you have [Docker](https://www.docker.com/get-started) installed on your system.

You can run the project using the following command:

```bash
docker run -e authentication.enabled="true" \
-e authentication.user.name="admin" \
-e authentication.user.password="admin" \
-e storeType="FILE_SYSTEM" \
-p 8080:8080 \
ghcr.io/cenkakin/maven-cache-server:latest
```

You can also check docker-compose examples [here](samples/remote-cache-starters).

You've successfully set up and run the maven cache server using Docker. If you need to customize the configuration or
explore advanced options, refer to the **Configuration** section below.

## Configuration

Please see the configuration details below:

- **authentication.enabled:** This is a flag that enables basic authentication, which is set to false by default.

- **authentication.user.name:** This specifies the user name that can access the server, which is set to "admin" by default.

- **authentication.user.password:** This specifies the password that can access the server, which is also set to "admin" by default.

- **maven-cache-server.storeType:** This specifies the desired strategy to cache files. There are two options available: FILE_SYSTEM (store files on the running machine's filesystem) or S3 (store files on AWS S3). The default option is FILE_SYSTEM.

- **maven-cache-server.baseFolderToStore:** This specifies the base folder path to store the files. When the storeType is S3, it represents the bucket name.

- **aws.accessKeyId:** This specifies the access key for the AWS account. It is required when the storeType is S3.

- **aws.secretAccessKey:** This specifies the secret key for the AWS account. It is required when the storeType is S3.

- **aws.region:** This specifies the region of the AWS account.

## How to run the server locally

- Use spring boot maven plugin:

```
./mvnw spring-boot:run
```

- Or open your IDE, and run/debug `com.github.cenkakin.mavencacheserver.MavenCacheApplication`

- The server starts on port 8080

Those commands use the default configuration. You can modify them
in [application.yml](src/main/resources/application.yml) file.

## How to test a remote cache server with the sample project locally

- Run the remote cache server using either:

    - Maven run command or IDE as instructed in the previous section

    - Run the sample docker-compose:
      ```bash
      docker-compose -f samples/remote-cache-starters/maven-cache-server-docker-compose.yml up
      ```

    - Run maven build command with the remote cache enabled:
      ```bash
      cd samples/hi-world && ./mvnw clean install -s settings.xml -Dmaven.build.cache.remote.enabled=true -Dmaven.build.cache.remote.save.enabled=true && cd ../..
      ```
