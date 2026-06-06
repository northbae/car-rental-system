FROM bellsoft/liberica-openjre-alpine:17
WORKDIR /logging-service
COPY logging-service-impl/target/*.jar logging-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "logging-service.jar"]