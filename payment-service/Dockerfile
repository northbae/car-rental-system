FROM amazoncorretto:17
WORKDIR /payment-service
COPY payment-service-impl/target/*.jar payment-service.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "payment-service.jar"]