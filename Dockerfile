FROM eclipse-temurin:25-jdk-jammy

WORKDIR /app

# For installing curl command, if you will not install it then your task will reman unhealthy
RUN apt-get update && apt-get install -y curl

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]