FROM openjdk:11
EXPOSE 8080

RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.13.0-amd64.deb && \
    dpkg -i filebeat-7.13.0-amd64.deb

COPY filebeat.yml /etc/filebeat/filebeat.yml
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ADD target/projeto-integrador.jar projeto-integrador.jar
ENTRYPOINT ["java", "-jar", "projeto-integrador.jar"]