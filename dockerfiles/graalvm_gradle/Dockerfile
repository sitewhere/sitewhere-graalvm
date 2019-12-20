FROM oracle/graalvm-ce:19.2.1

RUN yum install -y wget unzip &&\
    wget https://services.gradle.org/distributions/gradle-5.6.3-bin.zip -P /opt &&\
    cd /opt && unzip gradle-5.6.3-bin.zip &&\
    ln -s gradle-5.6.3 gradle

CMD ["/bin/bash"]
