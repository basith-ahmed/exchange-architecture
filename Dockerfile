FROM openjdk:11-jre-slim
COPY target/trading-platform.jar /app/trading-platform.jar
ENTRYPOINT ["java", "-jar", "/app/trading-platform.jar"]
