FROM ubuntu:18.04
WORKDIR /opt
COPY target/product-ms-1.0.0.jar /opt/product-ms.jar
RUN apt-get -y update  \
&& apt-get install wget -y \
&& wget https://javajdk7778.s3.sa-east-1.amazonaws.com/jdk-11.0.12_linux-x64_bin.tar.gz \
&& tar -xvf jdk-11.0.12_linux-x64_bin.tar.gz && rm -rf jdk-11.0.12_linux-x64_bin.tar.gz && mv jdk-11.0.12 jdk \
&& echo -e "export JAVA_HOME=/opt/jdk\nexport JRE_HOME=/opt/jdk/jre" >> /etc/bashrc \
&& echo -e 'export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin' >> /etc/bashrc
CMD ["/bin/sh", "-c", "/opt/jdk/bin/java -jar /opt/product-ms.jar"]