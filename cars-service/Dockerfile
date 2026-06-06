FROM bellsoft/liberica-openjre-alpine:17
WORKDIR /cars-service
COPY cars-service-impl/target/*.jar cars-service.jar
EXPOSE 8070
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "cars-service.jar"]