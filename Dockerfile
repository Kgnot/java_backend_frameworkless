# Etapa 1: Compila el JAR
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Usa solo el JAR construido
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/Java_frameworkless-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
