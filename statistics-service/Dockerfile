FROM amazoncorretto:17
WORKDIR /statistics-service
COPY statistics-service-impl/target/*.jar statistics-service.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "statistics-service.jar"]