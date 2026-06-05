FROM amazoncorretto:17
WORKDIR /auth-service
COPY auth-service-impl/target/*.jar auth-service.jar
EXPOSE 8110
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "auth-service.jar"]