FROM maven:3.6.3-jdk-11 AS builder
COPY ./src /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/energy-platform-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/energy-platform-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre
ENV TZ=UTC
ENV DB_IP=ec2-54-74-60-70.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=dvuisidxtsghbd
ENV DB_PASSWORD=d4718329d17128e9c32d1c082dedefb6617190c9a87b812a8728bd548f49d388
ENV DB_DBNAME=d11ljba3hvvpk6

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions"]