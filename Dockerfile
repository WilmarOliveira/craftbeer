# Maven

FROM maven:3.8.2-jdk-11 AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package spring-boot:repackage

# Java

FROM openjdk:11-jdk

COPY --from=build /home/app/target/craft-beer-1.0.jar /usr/local/lib/demo.jar

EXPOSE 9000

ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
