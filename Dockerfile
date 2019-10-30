FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/sitewhere-graalvm
WORKDIR /home/gradle/sitewhere-graalvm
RUN ./gradlew build

FROM oracle/graalvm-ce:19.2.1 as graalvm
COPY --from=builder /home/gradle/sitewhere-graalvm /home/gradle/sitewhere-graalvm
WORKDIR /home/gradle/sitewhere-graalvm
RUN /opt/graalvm-ce-19.2.1/bin/gu install native-image
RUN ./gradlew buildNative

FROM frolvlad/alpine-glibc
COPY --from=graalvm /home/gradle/sitewhere-graalvm/build/sitewhere-2.2.0-runner .
ENTRYPOINT ["./sitewhere-2.2.0-runner"]
