FROM eclipse-temurin:21 as build

WORKDIR /app

COPY ./.mvn ./.mvn
COPY ./mvnw ./mvnw
COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN ./mvnw -Dmaven.test.skip package

# ----------------------------------------------------- #

FROM eclipse-temurin:21

ENV SPRING_PROFILE 'production'
ENV ALLOWED_ORIGINS '*'
ENV ENCODER_SECRET ''

ENV DATABASE_URL ''
ENV DATABASE_USERNAME ''
ENV DATABASE_PASSWORD ''

EXPOSE 8080
COPY --from=build /app/target/bookiero-0.1.26.jar app.jar
CMD [ "java", "-jar", "./app.jar" ]
