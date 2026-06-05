FROM amazoncorretto:17
WORKDIR /logging-service
COPY logging-service-impl/target/*.jar logging-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "logging-service.jar"]