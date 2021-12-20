FROM ubuntu:18.04
WORKDIR /opt
COPY ./ /opt/product-ms
SHELL ["/bin/bash", "-c"]
RUN apt-get -y update  \
&& apt-get install wget -y \
&& wget https://javajdk7778.s3.sa-east-1.amazonaws.com/jdk-11.0.12_linux-x64_bin.tar.gz \
&& wget --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz   \
&& tar -xvf jdk-11.0.12_linux-x64_bin.tar.gz && rm -rf jdk-11.0.12_linux-x64_bin.tar.gz && mv jdk-11.0.12 jdk \
&& tar -xvf apache-maven-3.8.4-bin.tar.gz && rm -rf apache-maven-3.8.4-bin.tar.gz && mv apache-maven-3.8.4/ maven \
&& echo -e "export JAVA_HOME=/opt/jdk\nexport JRE_HOME=/opt/jdk/jre" >> /etc/bashrc \
&& echo -e 'export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin:/opt/maven/bin' >> /etc/bashrc \
&& source /etc/bashrc && cd /opt/product-ms && /opt/maven/bin/mvn clean install \
&& mv /opt/product-ms/target/*.jar /opt/product-ms/target/product-ms.jar
CMD ["/bin/sh", "-c", "/opt/jdk/bin/java -jar /opt/product-ms/target/product-ms.jar"]