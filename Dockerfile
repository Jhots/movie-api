FROM openjdk:21

WORKDIR /movie-api

COPY build/libs/movie-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
