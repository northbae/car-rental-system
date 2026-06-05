FROM amazoncorretto:17
WORKDIR /analytics-service
COPY analytics-service-impl/target/*.jar analytics-service.jar
EXPOSE 8120
ENTRYPOINT ["java", "-jar", "analytics-service.jar"]