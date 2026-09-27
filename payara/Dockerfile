FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM payara/server-full:7.2026.8

USER root
ADD https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.4.0/mysql-connector-j-9.4.0.jar \
    /opt/payara/appserver/glassfish/domains/domain1/lib/mysql-connector-j.jar
COPY payara/post-boot-commands.asadmin /opt/payara/config/post-boot-commands.asadmin
COPY --from=build /src/deployments/demo.war /opt/payara/deployments/demo.war
RUN chown payara:payara \
    /opt/payara/appserver/glassfish/domains/domain1/lib/mysql-connector-j.jar \
    /opt/payara/config/post-boot-commands.asadmin \
    /opt/payara/deployments/demo.war
USER payara
