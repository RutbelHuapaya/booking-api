
FROM openjdk:17-jdk-slim
ADD target/booking-api.jar booking-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/booking-api.jar"]
