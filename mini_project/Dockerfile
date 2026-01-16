# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# 1. Copy the wrapper files first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 2. ADD THIS LINE: Grant execute permission to the wrapper
RUN chmod +x mvnw

# 3. (Optional but recommended) Fix Windows line endings
RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw

# 4. Copy source and build
COPY src src
RUN ./mvnw install -DskipTests

# Use a slim runtime stage
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]