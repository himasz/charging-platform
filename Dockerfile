FROM openjdk:17-jdk-slim

LABEL Name="E-mobility Charging Solutions Platform"
LABEL Description="com.dcs"
LABEL Maintainer="himaszidan@gmail.com"
LABEL Url=""
COPY ./target/charging-solutions-platform.jar dcs.jar

EXPOSE 9090

HEALTHCHECK --start-period=60s \
  CMD curl -f http://localhost:9090/actuator/health || exit 1

USER 1001

ENTRYPOINT ["java","-jar","/dcs.jar"]
