FROM amazoncorretto:17
WORKDIR /rental-service
COPY rental-service-impl/target/*.jar rental-service.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "rental-service.jar"]