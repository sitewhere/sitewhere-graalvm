# SiteWhere GraalVM Integration
This repository is for testing related to integration of SiteWhere microservices with Quarkus/GraalVM.

## Build GraalVM Docker Container
In order to build a Linux-native executable, a Docker container is used to provide the necessary 
dependencies. Build the container by executing (from the `docker` folder):

```
docker build -t sitewhere/graalvm .
```

## Mount Project Folder and Build
In order to execute the build process, you local development directory needs to be mapped into
the Docker container as a volume. Execute the following (you will need to adjust the local path
in the volume mount to match your local folder structure):

```
docker run -it -v C:\YourGitHubPath\GitHub\sitewhere-graalvm:/home/sitewhere sitewhere/graalvm
```

## Build Enhanced JAR and Native Executable
After running the container, change to the `/home/sitewhere` folder to access the project 
contents. Build the enhanced JAR by running:

```
./gradlew clean build
```

Build the native executable by executing:

```
./gradlew buildNative
```
