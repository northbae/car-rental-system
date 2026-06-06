FROM bellsoft/liberica-openjre-alpine:17
WORKDIR /rental-service
COPY rental-service-impl/target/*.jar rental-service.jar
EXPOSE 8060
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "rental-service.jar"]