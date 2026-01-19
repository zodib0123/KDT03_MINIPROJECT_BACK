# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# 1. Copy the wrapper files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 2. Fix Windows line endings
RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw

# 3. Copy source
COPY src src

# 4. BUILD COMMAND
# Use 'sh' to run the file so we don't need executable permissions
RUN sh mvnw install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR from the build stage to the current WORKDIR (/app)
# We use a simpler pattern to ensure we grab the primary fat jar
COPY --from=build /workspace/app/target/*.jar app.jar

# Verify the file is there (This will show up in your Render logs)
RUN ls -l /app/app.jar

EXPOSE 8080

# Run using the relative path to the WORKDIR
ENTRYPOINT ["java", "-jar", "app.jar"]