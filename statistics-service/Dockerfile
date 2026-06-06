FROM amazoncorretto:17-alpine
WORKDIR /statistics-service
COPY statistics-service-impl/target/*.jar statistics-service.jar
EXPOSE 8100
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "statistics-service.jar"]