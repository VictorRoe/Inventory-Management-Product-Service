FROM gradle:9.0.0-jdk21-alpine AS builder
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN sed -i 's/\r$//' ./gradlew
RUN chmod +x ./gradlew
# Ejecutamos el build de Gradle para generar el JAR ejecutable.
# El comando 'build ' compila y empaqueta la aplicación. '-x test' se salta los tests para acelerar el build.
RUN ./gradlew build -x test
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --from=builder /home/gradle/project/applications/app-service/build/libs/*.jar application.jar
RUN chown appuser:appgroup application.jar
USER appuser
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "application.jar" ]