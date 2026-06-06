FROM amazoncorretto:17-alpine
WORKDIR /payment-service
COPY payment-service-impl/target/*.jar payment-service.jar
EXPOSE 8050
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "payment-service.jar"]