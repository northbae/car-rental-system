FROM bellsoft/liberica-openjre-alpine:17
WORKDIR /gateway-service
COPY gateway-service/gateway-service-impl/target/*.jar gateway-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "gateway-service.jar"]