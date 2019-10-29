FROM ubuntu

RUN apt-get update && apt-get -y install sudo wget gcc zlib1g-dev

RUN cd /opt && \
    wget https://github.com/oracle/graal/releases/download/vm-19.2.1/graalvm-ce-linux-amd64-19.2.1.tar.gz && \
    tar -xvzf graalvm-ce-linux-amd64-19.2.1.tar.gz && rm -rf graalvm-ce-linux-amd64-19.2.1.tar.gz && \
    update-alternatives --install /usr/bin/java java /opt/graalvm-ce-19.2.1/bin/java 4 && \
    /opt/graalvm-ce-19.2.1/bin/gu install native-image

CMD /bin/bash
