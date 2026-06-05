FROM amazoncorretto:17
WORKDIR /auth-service
COPY auth-service-impl/target/*.jar auth-service.jar
EXPOSE 8110
ENTRYPOINT ["java", "-jar", "auth-service.jar"]