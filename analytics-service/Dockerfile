FROM amazoncorretto:17-alpine
WORKDIR /analytics-service
COPY analytics-service-impl/target/*.jar analytics-service.jar
EXPOSE 8120
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "analytics-service.jar"]