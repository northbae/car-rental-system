FROM amazoncorretto:17
WORKDIR /cars-service
COPY cars-service-impl/target/*.jar cars-service.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "cars-service.jar"]